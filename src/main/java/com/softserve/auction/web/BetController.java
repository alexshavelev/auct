package com.softserve.auction.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softserve.auction.domain.Bet;
import com.softserve.auction.domain.Lot;
import com.softserve.auction.domain.LotStates;
import com.softserve.auction.domain.User;
import com.softserve.auction.domain.UserRole;
import com.softserve.auction.service.BetService;
import com.softserve.auction.service.LotService;
import com.softserve.auction.service.UserService;

@Controller
public class BetController {
	@Resource
	private BetService betService;
	@Resource
	private LotService lotService;
	@Resource
	private UserService userService;
	
	

	@RequestMapping(value = "/saveBet/{userId}/{lotId}", method = RequestMethod.POST)
	public String saveBet(@PathVariable Long userId, @PathVariable Long lotId,
			Model model, HttpServletRequest request) {

		Lot lot = lotService.findById(lotId);
		User user = userService.findById(userId);

		String query = "SELECT * FROM bet WHERE betstate=0 AND user_id="
				+ userId;
		List<Bet> bets = betService.findBets(query);
		Set<Long> uniqueLots = new HashSet<Long>();
		for (Bet bet : bets) {
			uniqueLots.add(bet.getLot().getLotId());
		}
		if (uniqueLots.size() >= 10) {
			model.addAttribute("errormsg",
					"You can't have more than 10 bets on different lots!");
			return "addBet";
		}

		if (user.getUserRole() == UserRole.USER
				&& lot.getStartPrice().compareTo(new BigDecimal(10000)) >= 0) {
			model.addAttribute("errormsg", "You can't add bet for this lot!");
			model.addAttribute("lot", lot);
			return "addBet";
		}

		if (user.getEmail().equals(lot.getUser().getEmail())) {
			model.addAttribute("errormsg", "It's your Lot!");
			model.addAttribute("lot", lot);
			return "addBet";
		}

		if (lot.getLotState() != LotStates.NONACTIVE
				&& lot.getLotState() != LotStates.PUBLISHED
				|| lot.getEndDate().getTime().compareTo(new Date()) < 0) {
			model.addAttribute("errormsg", "This Lot not for sale!");
			model.addAttribute("lot", lot);
			return "addBet";
		}

		Bet bet = lot.getCurrentBet();
		if (lot.getStartPrice().compareTo(
				new BigDecimal(request.getParameter("betPrice"))) >= 0) {
			model.addAttribute("errormsg",
					"Your Bet must be greater than Start price!");
			model.addAttribute("lot", lot);
			return "addBet";
		}

		if (bet != null) {
			if (request.getParameter("betPrice").isEmpty()
					|| (bet.getBetPrice().compareTo(
							new BigDecimal(request.getParameter("betPrice"))) >= 0)) {
				model.addAttribute("errormsg",
						"Your Bet must be greater than current Bet!");
				model.addAttribute("lot", lot);
				return "addBet";
			}
		}

		BigDecimal betPrice = new BigDecimal(request.getParameter("betPrice"));

		if (user.getBalance().compareTo(betPrice) < 0) {
			model.addAttribute("errormsg", "Insufficient funds!");
			model.addAttribute("lot", lot);
			return "addBet";
		}

		betService.addBet(betPrice, userId, lotId);
		return "redirect:/lots";
	}
	

	@RequestMapping(value = "/addBet/{lotId}")
	public String addBet(@PathVariable Long lotId, Model model) {
		model.addAttribute("lot", lotService.findById(lotId));
		return "addBet";
	}
	

	@RequestMapping(value = "/removeBet/{userId}/{lotId}/{betId}")
	public String cancelBet(@PathVariable Long userId,
			@PathVariable Long lotId, @PathVariable Long betId,
			RedirectAttributes redirectAttributes) {
		if (!betService.cancelBet(userId, lotId, betId)) {
			redirectAttributes.addFlashAttribute("errormsg",
					"Your can't cancel this Bet!");
		}
		return "redirect:/userbets/" + userId;
	}
}
