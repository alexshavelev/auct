package com.softserve.auction.web.rest;

import java.util.Calendar;
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

import com.softserve.auction.domain.Lot;
import com.softserve.auction.domain.LotStates;
import com.softserve.auction.service.LotService;

@Controller
public class LotRestController {
	@Resource
	private LotService lotService;

	@RequestMapping(value = "/rest/lots", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void addLot(HttpServletRequest request, Model model,
			@RequestBody Lot lot) {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		if (lot.getEndDate().before(now)) {
			throw new LotEndDateException();
		}
		lot.setLotState(LotStates.PUBLISHED);
		lotService.addLot(lot);
	}

	@RequestMapping(value = "/rest/lots", method = RequestMethod.GET)
	@ResponseBody
	public List<Lot> getAllLot() {
		String query = "SELECT * FROM lot WHERE lotstate<2";
		return lotService.findLots(query);
	}

	@RequestMapping(value = "/rest/lots/{lotId}", method = RequestMethod.GET)
	@ResponseBody
	public Lot getLot(@PathVariable Long lotId) {
		Lot lot = lotService.findById(lotId);
		if (lot == null) {
			throw new IndexOutOfBoundsException();
		}
		return lot;
	}

	@ExceptionHandler(IndexOutOfBoundsException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Lot not found")
	public void notFound() {
	}

	@RequestMapping(value = "/rest/lots/update", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public void updateLot(@RequestBody Lot lot) {
		Lot lotToUpdate = lotService.findById(lot.getLotId());
		if (lot.getLotState() != LotStates.NONACTIVE) {
			throw new LotStateException();
		}
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		if (lot.getEndDate().before(now)) {
			throw new LotEndDateException();
		}
		lotToUpdate.setName(lot.getName());
		lotToUpdate.setEndDate(lot.getEndDate());
		lotToUpdate.setDescription(lot.getDescription());
		lotToUpdate.setStartPrice(lot.getStartPrice());
		lotService.updateLot(lotToUpdate.getLotId());
	}

	@RequestMapping(value = "/rest/lots/{lotId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLot(@PathVariable Long lotId) {
		lotService.deleteLot(lotId);
	}

	@ExceptionHandler(LotStateException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Date < then Now")
	public void invalidEndDate() {
	}

	@ExceptionHandler(LotEndDateException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This Lot unable to update")
	public void noUpdateActiveLots() {
	}

}