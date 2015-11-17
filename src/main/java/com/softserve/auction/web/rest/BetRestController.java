package com.softserve.auction.web.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.softserve.auction.domain.Bet;
import com.softserve.auction.domain.Lot;
import com.softserve.auction.domain.LotStates;
import com.softserve.auction.domain.User;
import com.softserve.auction.domain.UserRole;
import com.softserve.auction.service.BetService;
import com.softserve.auction.service.LotService;
import com.softserve.auction.service.UserService;

@Controller
public class BetRestController {
	@Resource
	private BetService betService;
	@Resource
	private LotService lotService;
	@Resource
	private UserService userService;

	@RequestMapping(value = "/rest/bets", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public void addBet(HttpServletRequest request, Model model,
			@RequestBody Bet bet) {
		Lot lot = lotService.findById(bet.getLot().getLotId());
		User user = userService.findById(bet.getUser().getUserId());
		System.out.println("user:" + user + " lot:" + lot);
		BigDecimal betPrice = bet.getBetPrice();
		if (user.getBalance().compareTo(betPrice) < 0) {
			throw new InsufficientFundsException();
		}
		if (user.getUserRole() == UserRole.USER
				&& lot.getStartPrice().compareTo(new BigDecimal(10000)) >= 0) {
			throw new UserStatusException();
		}
		if (user.getEmail().equals(lot.getUser().getEmail())) {
			throw new NullPointerException();
		}
		if (lot.getLotState() != LotStates.NONACTIVE
				&& lot.getLotState() != LotStates.PUBLISHED
				|| lot.getEndDate().getTime().compareTo(new Date()) < 0) {
			throw new LotNotForSaleException();
		}
		if (lot.getCurrentBet() != null
				&& lot.getCurrentBet().getBetPrice().compareTo(betPrice) < 0) {
			betService.addBet(bet.getBetPrice(), bet.getUser().getUserId(), bet
					.getLot().getLotId());
		} else if (lot.getCurrentBet() == null
				&& lot.getStartPrice().compareTo(betPrice) < 0) {
			betService.addBet(bet.getBetPrice(), bet.getUser().getUserId(), bet
					.getLot().getLotId());
		} else {
			throw new BetPriceException();
		}
	}

	@RequestMapping(value = "/rest/bets", method = RequestMethod.GET)
	@ResponseBody
	public List<Bet> getAllBets() {
		return betService.findAllBets();
	}

	@RequestMapping(value = "/rest/bets/{betId}", method = RequestMethod.GET)
	@ResponseBody
	public Bet getBet(@PathVariable Long betId) {
		return betService.findById(betId);
	}

	@ExceptionHandler(IndexOutOfBoundsException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Bet not found")
	public void notFound() {
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You sent wrong parameters")
	public void nullParameter() {
	}

	@ExceptionHandler(InsufficientFundsException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Insufficient funds")
	public void insufficientFunds() {
	}

	@ExceptionHandler(UserStatusException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Impossible for your status")
	public void wrongStaus() {
	}

	@ExceptionHandler(LotNotForSaleException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Lot not for sale")
	public void wrongLotStaus() {
	}

	@ExceptionHandler(BetPriceException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Low bet")
	public void wrongBetPrice() {
	}

	@RequestMapping(value = "/rest/bets/update", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateBet(@RequestBody Bet bet) {
		Bet betToUpdate = betService.findById(bet.getBetId());
		BigDecimal betPrice = bet.getBetPrice();
		Lot lot = lotService.findById(bet.getLot().getLotId());
		if (lot.getCurrentBet().getBetPrice().compareTo(betPrice) < 0
				&& lot.getStartPrice().compareTo(betPrice) < 0) {
			betToUpdate.setBetPrice(betPrice);
			betService.updateBet(betToUpdate.getBetId());
		}
	}

	@RequestMapping(value = "/rest/bets/{betId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteBet(@PathVariable Long betId) {
		betService.deleteBet(betId);
	}
}
