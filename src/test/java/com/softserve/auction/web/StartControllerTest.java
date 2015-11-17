package com.softserve.auction.web;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.softserve.auction.domain.Bet;
import com.softserve.auction.domain.Lot;
import com.softserve.auction.service.BetService;
import com.softserve.auction.service.LotService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		" file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class StartControllerTest {
	@Resource
	private WebApplicationContext wac;
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();
	private StartController startController;
	private MockMvc mockMvc;
	private LotService lotService;
	private BetService betService;
	private final String queryLot = "SELECT * FROM lot WHERE lotstate<2 ORDER BY lotid DESC LIMIT 3";
	private final String queryBet = "SELECT * FROM bet WHERE betstate<2 ORDER BY betid DESC LIMIT 3";

	@Mock
	Model model;

	@Before
	public void setup() {
		context.setImposteriser(ClassImposteriser.INSTANCE);
		lotService = context.mock(LotService.class);
		betService = context.mock(BetService.class);
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
				.dispatchOptions(true).build();

	}

	@Test
	public void indexPageTest() throws Exception {

		startController = new StartController();
		ReflectionTestUtils.setField(startController, "lotService", lotService);
		ReflectionTestUtils.setField(startController, "betService", betService);

		final List<Lot> lots = new ArrayList<Lot>();
		lots.add(new Lot());
		lots.add(new Lot());
		lots.add(new Lot());

		final List<Bet> bets = new ArrayList<Bet>();
		bets.add(new Bet());
		bets.add(new Bet());
		bets.add(new Bet());

		context.checking(new Expectations() {
			{
				allowing(lotService).findLots(queryLot);
				will(returnValue(lots));
				allowing(betService).findBets(queryBet);
				will(returnValue(bets));
				allowing(model).addAttribute("lots", lots);
				allowing(model).addAttribute("bets", bets);
			}
		});

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/"));

		result.andExpect(MockMvcResultMatchers.view().name("index"))
				.andExpect(
						MockMvcResultMatchers.model().attributeExists("lots"))
				.andExpect(
						MockMvcResultMatchers.model().attributeExists("bets"));

		String viewName = startController.home(model);
		assertEquals("index", viewName);
	}
}
