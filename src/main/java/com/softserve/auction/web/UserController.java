package com.softserve.auction.web;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softserve.auction.domain.User;
import com.softserve.auction.service.UserService;

@Controller
@SessionAttributes({ "user" })
public class UserController {
	@Resource
	private UserService userService;
	@Resource
	private JavaMailSender mailSender;

	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String addUser(HttpServletRequest request, Model model) {
		Map param = (Map) request.getParameterMap();
		model.addAttribute("dname", request.getParameter("name"));
		model.addAttribute("demail", request.getParameter("email"));
		model.addAttribute("date", request.getParameter("birthday"));
		
		if (!request.getParameter("password").equals(
				request.getParameter("confirmpass"))) {
			model.addAttribute("errormsg",
					"Passwords did not match. Please enter the same password in both fields.");
			return "registration";
		}

		String emailAddress = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		String saveResult = userService.saveUser(param);
		if (saveResult.equals("Saved")) {
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(emailAddress);
			email.setSubject("Auction Elite");
			email.setText("Hello! Dear, "
					+ name
					+ "! \nÑongratulate with register on our Elite Auction!\n Your credits for login is:\n"
					+ emailAddress + "\n" + password);
			mailSender.send(email);
			return "redirect:/login";
		}
		model.addAttribute("errormsg", saveResult);
		return "registration";
	}

	
	@RequestMapping(value = "/registration")
	public String newUser() {
		return "registration";
	}

	
	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	
	@RequestMapping(value = "/edituser")
	public String editUser() {
		return "editUser";
	}

	
	@RequestMapping(value = "/editUser/{userId}", method = RequestMethod.POST)
	public String saveEditUser(@PathVariable Long userId,
			RedirectAttributes redirectAttributes, HttpServletRequest request,
			Model model) {
		if (!request.getParameter("password").equals(
				request.getParameter("confirmpass"))) {
			redirectAttributes.addFlashAttribute("dname",
					request.getParameter("name"));
			redirectAttributes.addFlashAttribute("demail",
					request.getParameter("email"));
			redirectAttributes
					.addFlashAttribute("errormsg",
							"Passwords did not match. Please enter the same password in both fields.");
			return "redirect:/edituser";
		}
		String query = "SELECT * FROM user WHERE userId =" + userId;
		User user = userService.findUsers(query).get(0);

		if (!request.getParameter("email").equals(user.getEmail())) {
			String query2 = "SELECT * FROM user WHERE email ='"
					+ request.getParameter("email") + "'";
			if (!userService.findUsers(query2).isEmpty()) {
				redirectAttributes.addFlashAttribute("errormsg",
						"This email already used!");
				return "redirect:/edituser";
			}
		}
		if (!request.getParameter("oldpassword").equals(user.getPassword())) {
			redirectAttributes.addFlashAttribute("errormsg",
					"Wrong old password");
			return "redirect:/edituser";
		}
		if (!request.getParameter("name").isEmpty()) {
			user.setName(request.getParameter("name"));
		}
		if (!request.getParameter("email").isEmpty()) {
			user.setEmail(request.getParameter("email"));
		}
		if (!request.getParameter("password").isEmpty()) {
			user.setPassword(request.getParameter("password"));
		}

		String birthdayStr = request.getParameter("birthday");
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date;
		try {
			date = formatter.parse(birthdayStr);
		} catch (ParseException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("dname",
					request.getParameter("name"));
			redirectAttributes.addFlashAttribute("demail",
					request.getParameter("email"));
			redirectAttributes.addFlashAttribute("errormsg",
					"Invalid date format");
			return "redirect:/edituser";
		}
		Calendar birthday = Calendar.getInstance();
		birthday.setTime(date);
		Calendar youngDate = Calendar.getInstance();
		youngDate.add(Calendar.YEAR, -18);
		if (birthday.compareTo(youngDate) > 0) {
			redirectAttributes.addFlashAttribute("dname",
					request.getParameter("name"));
			redirectAttributes.addFlashAttribute("demail",
					request.getParameter("email"));
			redirectAttributes.addFlashAttribute("errormsg",
					"Sorry. You are under 18.");
			return "redirect:/edituser";
		}
		user.setBirthday(birthday);
		userService.updateUser(userId);

		if (!request.getParameter("password").isEmpty()
				&& !request.getParameter("email").isEmpty()) {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					request.getParameter("email"),
					request.getParameter("password"));
			SecurityContextHolder.getContext().setAuthentication(
					authenticationToken);
		}
		return "redirect:/userDetail";
	}

	
	@RequestMapping(value = "/userDetail")
	public String userDetail(Principal principal, Model model) {
		if (principal != null) {
			String query = "SELECT * FROM user WHERE email ='"
					+ principal.getName() + "'";
			List<User> users = userService.findUsers(query);
			if (!users.isEmpty()) {
				model.addAttribute("user", userService.findUsers(query).get(0));
				return "userDetail";
			}
		}
		return "login";
	}

	
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, Model model) {
		model.asMap().remove("user");
		request.getSession().invalidate();
		return "redirect:/index";
	}

	
	@RequestMapping(value = "/checklogin")
	public String checkLogin(HttpServletRequest request, Model model,
			Principal principal) {

		String query = "SELECT * FROM user WHERE email ='"
				+ principal.getName() + "'";
		List<User> users = userService.findUsers(query);
		if (!users.isEmpty()) {
			model.addAttribute("user", userService.findUsers(query).get(0));
			return "redirect:/index";
		}
		return "login";
	}

	
	@RequestMapping(value = "/userbets/{userId}")
	public String userBets(@PathVariable Long userId, Model model) {
		model.addAttribute("bets", userService.getUserBets(userId));
		return "userbets";
	}
}
