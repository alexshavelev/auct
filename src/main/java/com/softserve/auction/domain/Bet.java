package com.softserve.auction.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
public class Bet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long betId;
	private BigDecimal betPrice = new BigDecimal(0);

	@ManyToOne
	@JoinColumn(name = "lot_id")
	@JsonBackReference("bets in lot")
	private Lot lot;
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;
	private Date betDate = new Date();
	@Enumerated
	private BetState betState = BetState.MADE;

	public Bet() {

	}

	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
	}

	public BigDecimal getBetPrice() {
		return betPrice;
	}

	public void setBetPrice(BigDecimal betPrice) {
		this.betPrice = betPrice;
	}

	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getBetDate() {
		return betDate;
	}

	public void setBetDate(Date betDate) {
		this.betDate = betDate;
	}

	public BetState getBetState() {
		return betState;
	}

	public void setBetState(BetState betState) {
		this.betState = betState;
	}

}
