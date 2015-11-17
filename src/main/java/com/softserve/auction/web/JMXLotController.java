package com.softserve.auction.web;

import javax.annotation.Resource;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.auction.domain.Lot;
import com.softserve.auction.domain.LotStates;
import com.softserve.auction.service.LotService;

@Controller
@ManagedResource(objectName = "Auction:name=JMXLotController")
public class JMXLotController {
	@Resource
	private LotService lotService;

	
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	@ManagedOperation
	public void changeLotState(Long lotId, Boolean publish, Boolean block) {
		Lot lot = lotService.findById(lotId);
		if (lot != null) {
			if (block) {
				lot.setLotState(LotStates.BLOCKED);
			} else if (lot.getLotState() == LotStates.BLOCKED){
				lot.setLotState(LotStates.NONACTIVE);
			}
			if (publish && lot.getLotState() == LotStates.NONACTIVE) {
				lot.setLotState(LotStates.PUBLISHED);
			}
			lotService.updateLot(lotId);
		}
	}
}
