package com.softserve.auction.web;

import static org.junit.Assert.assertEquals;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;

import com.softserve.auction.domain.Bet;
import com.softserve.auction.domain.User;
import com.softserve.auction.service.UserService;

public class UserControllerTest {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();
	private UserController userController;
	private UserService userService;
	private String query = "SELECT * FROM user WHERE email ='e@mail.ua'";
	@Mock
	Model model;
	@Mock
	Principal principal;
	private final Long userId = 2L;
	private HttpServletRequest request;

	@Before
	public void setup() {
		context.setImposteriser(ClassImposteriser.INSTANCE);
		userService = context.mock(UserService.class);
		request = context.mock(HttpServletRequest.class);
		userController = new UserController();
		ReflectionTestUtils
				.setField(userController, "userService", userService);
	}

	@Test
	public void userDetailTest() {
		final List<User> users = new ArrayList<User>();
		final User user = new User();
		users.add(user);

		context.checking(new Expectations() {
			{
				allowing(userService).findUsers(query);
				will(returnValue(users));
				oneOf(model).addAttribute("user", user);
				oneOf(principal).getName();
				will(returnValue("e@mail.ua"));
			}
		});

		String viewName = userController.userDetail(principal, model);
		assertEquals("userDetail", viewName);
	}

	@Test
	public void userBetsTest() {
		final List<Bet> bets = new ArrayList<Bet>();
		bets.add(new Bet());
		bets.add(new Bet());
		bets.add(new Bet());

		context.checking(new Expectations() {
			{
				oneOf(userService).getUserBets(userId);
				will(returnValue(bets));
				oneOf(model).addAttribute("bets", bets);
			}
		});

		String viewName = userController.userBets(userId, model);
		assertEquals("userbets", viewName);
	}

	@Test
	public void logoutTest() {
		context.checking(new Expectations() {
			{
				oneOf(model).asMap().remove("user");
				oneOf(request).getSession().invalidate();
			}
		});

		String viewName = userController.logout(request, model);
		assertEquals("redirect:/index", viewName);
	}
}
