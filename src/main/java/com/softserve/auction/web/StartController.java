package com.softserve.auction.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.softserve.auction.service.BetService;
import com.softserve.auction.service.LotService;

@Controller
public class StartController {
	@Resource
	private LotService lotService;
	@Resource
	private BetService betService;

	
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String home(Model model) {
		String query = "SELECT * FROM lot WHERE lotstate<2 ORDER BY lotid DESC LIMIT 3";
		model.addAttribute("lots", lotService.findLots(query));
		
		String betQuery = "SELECT * FROM bet WHERE betstate<2 ORDER BY betid DESC LIMIT 3";
		model.addAttribute("bets", betService.findBets(betQuery));
		
		return "index";
	}

	
	@RequestMapping(value = { "/error403" })
	public String error() {
		return "error403";
	}
}
