package com.softserve.auction.web;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.auction.domain.User;
import com.softserve.auction.service.UserService;

@Controller
@ManagedResource(objectName = "Auction:name=JMXUserController")
public class JMXUserController {
	@Resource
	private UserService userService;

	
	
	@Transactional
	@ManagedOperation
	public void recharge(String email, double money) {
		String query = "SELECT * FROM user WHERE email ='" + email + "'";
		List<User> users = userService.findUsers(query);
		if (users.size() > 0) {
			User user = users.get(0);
			BigDecimal newBalance = user.getBalance()
					.add(new BigDecimal(money));
			user.setBalance(newBalance);
			userService.updateUser(user.getUserId());
		}
	}

	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	@ManagedOperation
	public void changeRole(String email, Boolean block) {
		String query = "SELECT * FROM user WHERE email ='" + email + "'";
		List<User> users = userService.findUsers(query);
		if (users.size() > 0) {
			User user = users.get(0);
			if (block) {
				user.setRole("ROLE_BLOCKED");
			} else {
				user.setRole("ROLE_USER");
			}
			userService.updateUser(user.getUserId());
		}
	}
}
