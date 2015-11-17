package com.softserve.auction.web.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.softserve.auction.domain.User;
import com.softserve.auction.service.UserService;

@Controller
public class UserRestController {
	@Resource
	private UserService userService;

	
	
	@RequestMapping(value = "/rest/users", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getUsers() {
		return userService.findAllUsers();
	}

	
	@RequestMapping(value = "/rest/users/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public User getUser(@PathVariable Long userId) {
		return userService.findById(userId);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/rest/users", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void addUser(HttpServletRequest request, Model model,
			@RequestBody User user) {
		Map param = new HashMap();
		param.put("email", new String[]{user.getEmail()});
		param.put("name", new String[]{user.getName()});
		param.put("password", new String[]{user.getPassword()});
		Date birthday = user.getBirthday().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		param.put("birthday", new String[]{formatter.format(birthday)});
		userService.saveUser(param);
	}
	
	
	@RequestMapping(value = "/rest/users/update", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public void updateUser(@RequestBody User user) {
		User userToUpdate = userService.findById(user.getUserId());
		userToUpdate.setName(user.getName());
		userToUpdate.setBirthday(user.getBirthday());
		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setPassword(user.getPassword());
		userService.updateUser(userToUpdate.getUserId());
	}
	
	
}
