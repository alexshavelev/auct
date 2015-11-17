package com.softserve.auction.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.auction.dao.Dao;
import com.softserve.auction.domain.Bet;
import com.softserve.auction.domain.Lot;
import com.softserve.auction.domain.LotStates;
import com.softserve.auction.domain.User;

@Service
@Transactional
public class UserService {
	@Resource
	private Dao<User> userDaoImpl;
	
	

	@SuppressWarnings("rawtypes")
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public String saveUser(Map param) {
		String email = ((String[]) param.get("email"))[0];
		String query = "SELECT * FROM user WHERE email ='" + email + "'";

		if (findUsers(query).isEmpty()) {
			String name = ((String[]) param.get("name"))[0];
			String password = ((String[]) param.get("password"))[0];
			String birthdayStr = ((String[]) param.get("birthday"))[0];

			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			Date date;
			try {
				date = formatter.parse(birthdayStr);
			} catch (ParseException e) {
				e.printStackTrace();
				return "Invalid date format";
			}
			Calendar birthday = Calendar.getInstance();
			birthday.setTime(date);
			Calendar youngDate = Calendar.getInstance();
			youngDate.add(Calendar.YEAR, -18);
			if (birthday.compareTo(youngDate) > 0) {
				return "Sorry. You are under 18.";
			}

			User user = new User();
			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);
			user.setBirthday(birthday);
			userDaoImpl.save(user);

			return "Saved";
		}
		return "This email already used!";
	}

	public boolean updateUser(Long id) {
		userDaoImpl.update(id);
		return true;
	}

	public List<User> findUsers(String query) {
		return userDaoImpl.findByQuery(query);
	}

	public List<Bet> getUserBets(Long userId) {
		String query = "SELECT * FROM user WHERE userid=" + userId;
		User user = findUsers(query).get(0);
		return user.getBets();
	}

	public boolean checkUserLogin(String email, String password) {
		String query = "SELECT * FROM user WHERE password='" + password
				+ "' AND email ='" + email + "'";
		if (userDaoImpl.findByQuery(query).isEmpty()) {
			return false;
		}
		return true;
	}
	

	@Scheduled(fixedRate = 60000)
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void checkUserStatus() {
		String query2 = "UPDATE user SET userrole=2 WHERE userrole=1 AND experience>=120";
		userDaoImpl.updateByQuery(query2);

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -7);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String query3 = "UPDATE user SET userrole=0 WHERE userrole=1 AND"
				+ " lastbetdate<'" + formatter.format(c.getTime()) + "'";
		userDaoImpl.updateByQuery(query3);

		String query = "UPDATE user SET userrole=1 WHERE userrole=0 AND points>=1000 AND"
				+ " lastbetdate>='" + formatter.format(c.getTime()) + "'";
		userDaoImpl.updateByQuery(query);
	}
	

	@Scheduled (fixedRate = 60000)//(cron = "0 0 0 15 * ?")
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void increaseUserExperience() {
		String query = "SELECT*FROM user WHERE userrole>0";
		List<User> users = userDaoImpl.findByQuery(query);

		Calendar monthAgo = Calendar.getInstance();
		monthAgo.setTime(new Date());
		monthAgo.add(Calendar.MONTH, -1);

		for (User user : users) {
			List<Lot> lots = user.getLots();
			BigDecimal soldSum = new BigDecimal(0);
			for (Lot lot : lots) {
				if ((lot.getLotState() == LotStates.SOLD || lot.getLotState() == LotStates.CLOSED)
						&& (lot.getEndDate().after(monthAgo))) {
					soldSum = soldSum.add(lot.getCurrentBet().getBetPrice());
				}
			}
			if (soldSum.compareTo(new BigDecimal(10000)) >= 0) {
				user.setExperience((user.getExperience() + 1));
				if ((user.getExperience() % 12 == 0)
						&& (user.getDiscount().compareTo(new BigDecimal(0.02)) < 0)) {
					BigDecimal newDiscount = user.getDiscount().add(
							new BigDecimal(0.01));
					user.setDiscount(newDiscount);
				}
				userDaoImpl.update(user.getUserId());
			}
		}

	}
	

	public List<User> findAllUsers() {
		return userDaoImpl.findAll();
	}
	

	public User findById(Long userId) {
		return userDaoImpl.findById(userId);
	}
}
