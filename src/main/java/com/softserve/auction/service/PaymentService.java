package com.softserve.auction.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.auction.dao.Dao;
import com.softserve.auction.domain.Bet;
import com.softserve.auction.domain.BetState;
import com.softserve.auction.domain.Earnings;
import com.softserve.auction.domain.Lot;
import com.softserve.auction.domain.LotStates;
import com.softserve.auction.domain.User;

@Service
@Transactional
@ManagedResource(objectName = "Auction:name=Income")
public class PaymentService {
	private final BigDecimal commission = new BigDecimal(0.11);
	private final BigDecimal pointCoef = new BigDecimal(0.01);
	private final BigDecimal penaltyAuctionCoef = new BigDecimal(0.005);
	private final BigDecimal penaltyOwnerCoef = new BigDecimal(0.01);

	@Resource
    private JavaMailSender mailSender;
	@Resource
	private LotService lotService;
	@Resource
	private UserService userService;
	@Resource
	private Dao<Earnings> earningsDaoImpl;

	
	
	@Transactional
	public boolean selling() {
		String query = "SELECT * FROM lot WHERE lotState=2";
		List<Lot> lots = lotService.findLots(query);
		System.out.println("Lots to sold: " + lots.size());
		for (Lot lot : lots) {
			System.out.println("Lots to sold: " + lot.getLotState());
			User owner = lot.getUser();
			BigDecimal soldPrice = lot.getCurrentBet().getBetPrice();
			BigDecimal payment = lot.getStartPrice().multiply(commission);
			BigDecimal newOwnerBalance = owner.getBalance().add(
					soldPrice.subtract(payment));
			BigDecimal points = soldPrice.multiply(pointCoef);

			User buyer = lot.getCurrentBet().getUser();
			BigDecimal newBuyerBalance = buyer.getBalance()
					.subtract(
							soldPrice.subtract(soldPrice.multiply(buyer
									.getDiscount())));
			if (newBuyerBalance.doubleValue()<0){
				lot.setLotState(LotStates.NONACTIVE);
				lotService.updateLot(lot.getLotId());
				return false;
			}
			owner.setPoints(owner.getPoints().add(points));
			owner.setBalance(newOwnerBalance);
			buyer.setBalance(newBuyerBalance);
			userService.updateUser(owner.getUserId());
			userService.updateUser(buyer.getUserId());

			Earnings earnings = new Earnings();
			earnings.setUser(buyer);
			earnings.setPaymentDate(lot.getCurrentBet().getBetDate());
			earnings.setPayment(payment);
			earningsDaoImpl.save(earnings);
			lot.setLotState(LotStates.CLOSED);
			lot.getCurrentBet().setBetState(BetState.WON);
			lotService.updateLot(lot.getLotId());
			
			// takes input from e-mail form
            String emailAddress = owner.getEmail();
            String name = owner.getName();
            String soldLot = lot.getName();
            String currentPrice = lot.getCurrentBet().getBetPrice().toString();
            String nameBuyer = buyer.getName();
            String emailBuyer = buyer.getEmail();

            // prints debug info
            // System.out.println("To: " + emailAddress);
            System.out.println("Bet: " + currentPrice);

            // creates a simple e-mail object
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(emailAddress);
            email.setSubject("Auction Elite");
            email.setText("Hello, dear " + name
                            + "!\nCongratulation! Your lot:" 
                            + soldLot + " - has been sold for " + currentPrice + " $!!!\n" 
                            + "You can contact with buyer for detail of shipping."
                            + "Here is his name, and contact email:\n"
                            + nameBuyer + ", " + emailBuyer);

            // sends the e-mail
            mailSender.send(email);
		}
		return true;
	}

	
	public void paymentOfPenalties(Long lotId, Long userId, Bet bet) {
		String query = "SELECT * FROM lot WHERE lotid=" + lotId;
		Lot lot = lotService.findLots(query).get(0);
		String queryUser = "SELECT * FROM user WHERE userid=" + userId;
		User buyer = userService.findUsers(queryUser).get(0);
		User owner = lot.getUser();

		BigDecimal payment = lot.getStartPrice().multiply(penaltyAuctionCoef);
		Earnings earnings = new Earnings();
		earnings.setUser(buyer);
		earnings.setPaymentDate(new Date());
		earnings.setPayment(payment);
		earningsDaoImpl.save(earnings);

		BigDecimal max = new BigDecimal(10000);
		BigDecimal penaltyToOwner = lot.getStartPrice().multiply(
				penaltyOwnerCoef);
		if (penaltyToOwner.compareTo(max) > 0) {
			penaltyToOwner = max;
		}
		BigDecimal newOwnerBalance = owner.getBalance().add(penaltyToOwner);
		BigDecimal newBuyerBalance = buyer.getBalance().subtract(
				payment.add(penaltyToOwner));
		System.out.println("bet state: " + bet.getBetState() + "betPrice="
				+ bet.getBetPrice());
		if (bet.getBetState() == BetState.WON) {
			newOwnerBalance = newOwnerBalance.subtract(bet.getBetPrice()
					.subtract(lot.getStartPrice().multiply(commission)));
			newBuyerBalance = newBuyerBalance.add(bet.getBetPrice());
			owner.setPoints(owner.getPoints().subtract(
					bet.getBetPrice().multiply(pointCoef)));
		}
		owner.setBalance(newOwnerBalance);
		userService.updateUser(owner.getUserId());
		buyer.setBalance(newBuyerBalance);
		userService.updateUser(buyer.getUserId());
	}

	
	@ManagedOperation
	public double getTotalIncome() {
		String query = "SELECT * FROM earnings";
		return getIncome(query);
	}

	
	@ManagedOperation
	public double getYearIncome(int year) {
		String query = "SELECT * FROM earnings WHERE paymentdate LIKE '" + year
				+ "%'";
		return getIncome(query);
	}

	
	@ManagedOperation
	public double getMonthIncome(int year, int month) {
		String mm = month + "";
		if (month < 10 && month > 0) {
			mm = "0" + month;
		}
		String query = "SELECT * FROM earnings WHERE paymentdate LIKE '" + year
				+ "-" + mm + "%'";
		return getIncome(query);
	}

	
	public double getIncome(String query) {
		List<Earnings> earnings = earningsDaoImpl.findByQuery(query);
		BigDecimal income = new BigDecimal(0);
		for (Earnings earn : earnings) {
			income = income.add(earn.getPayment());
		}
		return income.doubleValue();
	}
}
