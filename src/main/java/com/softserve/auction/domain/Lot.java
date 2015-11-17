package com.softserve.auction.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Lot implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lotId;

	private String name = "uncknown";

	@Temporal(value = TemporalType.TIMESTAMP)
	private Calendar endDate = Calendar.getInstance();
	private BigDecimal startPrice = new BigDecimal(0);

	@Enumerated
	private LotStates lotState = LotStates.NONACTIVE;

	private String description = "";
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CURRENTBET_ID")
	private Bet currentBet;

	@JsonManagedReference("bets in lot")
	@OneToMany(mappedBy = "lot")
	private List<Bet> bets = new ArrayList<Bet>();
	@JsonBackReference("user in lot")
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private String imgPath;

	public Lot() {
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public List<Bet> getBets() {
		return bets;
	}

	public void setBets(List<Bet> bets) {
		this.bets = bets;
	}

	public User getUser() {
		return user;
	}

	public Bet getCurrentBet() {
		return currentBet;
	}

	public void setCurrentBet(Bet currentBet) {
		this.currentBet = currentBet;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}

	public LotStates getLotState() {
		return lotState;
	}

	public void setLotState(LotStates lotState) {
		this.lotState = lotState;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
