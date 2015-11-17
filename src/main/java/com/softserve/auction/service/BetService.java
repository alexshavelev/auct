package com.softserve.auction.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.auction.dao.Dao;
import com.softserve.auction.domain.Bet;
import com.softserve.auction.domain.BetState;
import com.softserve.auction.domain.Lot;
import com.softserve.auction.domain.User;

@Service
@Transactional
public class BetService {
	@Resource
	private Dao<Bet> betDaoImpl;
	@Resource
	private UserService userService;
	@Resource
	private LotService lotService;
	@Resource
	private PaymentService paymentService;

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void addBet(BigDecimal betPrice, Long userId, Long lotId) {
		Lot lot = lotService.findById(lotId);
		User user = userService.findById(userId);
		Bet bet = new Bet();
		bet.setLot(lot);
		bet.setBetPrice(betPrice);
		bet.setUser(user);
		lot.getBets().add(bet);
		lot.setCurrentBet(bet);
		user.getBets().add(bet);
		user.setLastBetDate(new Date());
		userService.updateUser(userId);
		lotService.updateLot(lotId);
		betDaoImpl.save(bet);
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public boolean cancelBet(Long userId, Long lotId, Long betId) {
		Bet bet = findById(betId);
		if (bet.getBetState() == BetState.CANCELED
				|| bet.getBetState() == BetState.WON) {
			return false;
		}
		Lot lot = lotService.findById(lotId);
		if (bet.getBetDate().compareTo(lot.getEndDate().getTime()) > 0) {
			System.out.println("No cancel");
			return false;
		}
		paymentService.paymentOfPenalties(lotId, userId, bet);

		String query = "UPDATE bet SET betstate=2 WHERE betid=" + betId;
		betDaoImpl.updateByQuery(query);

		String queryBets = "SELECT * FROM bet WHERE lot_id=" + lotId
				+ "AND betprice=(SELECT MAX(betprice) from bet WHERE lot_id="
				+ lotId + " AND betstate<>2)";
		List<Bet> bets = betDaoImpl.findByQuery(queryBets);
		Bet currentBet = null;
		if (bets.size() > 0) {
			currentBet = bets.get(0);
		}
		lot.setCurrentBet(currentBet);
		lotService.updateLot(lotId);
		return true;
	}

	public List<Bet> findBets(String query) {
		return betDaoImpl.findByQuery(query);
	}

	public List<Bet> findAllBets() {
		return betDaoImpl.findAll();
	}
	
	public Bet findById(Long id) {
		return betDaoImpl.findById(id);
	}

	public void deleteBet(Long betId) {
		betDaoImpl.delete(betId);
	}

	public void updateBet(Long betId) {
		betDaoImpl.update(betId);
	}
}
