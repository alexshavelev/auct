package com.softserve.auction.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String name;
	private String password;
	private String email;
	private BigDecimal balance = new BigDecimal(0);
	private BigDecimal points = new BigDecimal(0);

	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastBetDate;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Calendar birthday;
	@JsonManagedReference("user in lot")
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Lot> lots = new ArrayList<Lot>();
	@JsonManagedReference
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Bet> bets = new ArrayList<Bet>();
	@Enumerated
	private UserRole userRole = UserRole.USER;
	private int experience;
	private BigDecimal discount = new BigDecimal(0);

	private String role = "ROLE_USER";
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public BigDecimal getPoints() {
		return points;
	}

	public void setPoints(BigDecimal points) {
		this.points = points;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getLastBetDate() {
		return lastBetDate;
	}

	public void setLastBetDate(Date date) {
		this.lastBetDate = date;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public List<Lot> getLots() {
		return lots;
	}

	public void setLots(List<Lot> lots) {
		this.lots = lots;
	}

	public List<Bet> getBets() {
		return bets;
	}

	public void setBets(List<Bet> bets) {
		this.bets = bets;
	}

}
