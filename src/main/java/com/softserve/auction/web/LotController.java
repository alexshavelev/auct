package com.softserve.auction.web;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softserve.auction.domain.Lot;
import com.softserve.auction.domain.LotStates;
import com.softserve.auction.domain.UploadFile;
import com.softserve.auction.domain.User;
import com.softserve.auction.service.FileService;
import com.softserve.auction.service.LotService;
import com.softserve.auction.service.PageService;

@Controller
public class LotController {
	@Resource
	private LotService lotService;
	@Resource
	private PageService pageService;
	@Resource
	private ServletContext app;
	@Resource
	private Validator imageValidator;
	@Resource
	private FileService fileService;

	@InitBinder("uploadFile")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(imageValidator);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lotController", method = RequestMethod.POST)
	public String addLot(@Valid @ModelAttribute("uploadFile") UploadFile img,
			BindingResult result, HttpServletRequest request, Model model) {

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String startprice = request.getParameter("startprice");
		model.addAttribute("name", name);
		model.addAttribute("description", description);
		model.addAttribute("startprice", startprice);
		if (result.hasErrors()) {
			return "newlot";
		}
		Map<String, String[]> param = (Map<String, String[]>) request
				.getParameterMap();
		User user = (User) request.getSession().getAttribute("user");
		String saveResult = lotService.saveLot(param, user, img,
				app.getRealPath("resources/userimg/"));
		if (saveResult.equals("Saved")) {
			return "redirect:/lots";
		}
		model.addAttribute("errormsg", saveResult);
		return "newlot";
	}

	@RequestMapping(value = "/editLot/{lotId}", method = RequestMethod.POST)
	public String saveEditLot(@PathVariable Long lotId,
			HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("uploadFile") UploadFile img,
			BindingResult result) {

		if (result.hasErrors()) {
			return editLot(lotId, model);
		}
		User user = (User) request.getSession().getAttribute("user");
		Lot lot = lotService.findById(lotId);

		if (lot.getLotState() != LotStates.NONACTIVE) {
			redirectAttributes.addFlashAttribute("errormsg",
					"You can edit only NONACTIVE Lots!");
			return "redirect:/lots";
		}

		if (!user.getEmail().equals(lot.getUser().getEmail())) {
			redirectAttributes.addFlashAttribute("errormsg",
					"It is not your Lot!");
			return "redirect:/lots";
		}

		if (!request.getParameter("name").isEmpty()) {
			lot.setName(request.getParameter("name"));
		}
		if (!request.getParameter("description").isEmpty()) {
			lot.setDescription(request.getParameter("description"));
		}
		if (!request.getParameter("startprice").isEmpty()) {
			lot.setStartPrice(new BigDecimal(request.getParameter("startprice")));
		}
		String enddateStr = request.getParameter("enddate");
		String hours = request.getParameter("hour");
		String min = request.getParameter("min");
		Calendar endDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date;
		try {
			date = formatter.parse(enddateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			model.addAttribute("errormsg",
					"Invalid date format");
			return editLot(lotId, model);
		}
		endDate.setTime(date);
		endDate.set(Calendar.HOUR, Integer.parseInt(hours));
		endDate.set(Calendar.MINUTE, Integer.parseInt(min));
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		if (endDate.before(now)) {
			model.addAttribute("errormsg",
					"End date should be later than the current!");
			return editLot(lotId, model);
		}
		lot.setEndDate(endDate);

		if (!img.getFile().isEmpty()) {
			File fileImg = new File(app.getRealPath(lot.getImgPath()));
			fileImg.delete();
			lot.setImgPath("/resources/userimg/"
					+ img.getFile().getOriginalFilename());
			fileService.saveFile(app.getRealPath("resources/userimg/"), img);
		}
		lotService.updateLot(lotId);
		return "redirect:/lots";
	}

	@RequestMapping(value = "/editlot/{lotId}")
	public String editLot(@PathVariable Long lotId, Model model) {
		Lot lot = lotService.findById(lotId);
		if (lot != null) {
			model.addAttribute("lot", lot);
		}
		return "editLot";
	}

	@RequestMapping(value = "/searchLots", method = RequestMethod.GET)
	public String searchLots(Model model, HttpServletRequest request) {
		filterLots(model, request);
		return "lots";
	}

	@RequestMapping(value = "/filterLots")
	public String filterLots(Model model, HttpServletRequest request) {
		int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		double maxPrice = 1000000000000d;

		String orderby = " lotid DESC";
		String orderParam = (String) request.getSession().getAttribute(
				"orderName");
		String orderDirection = (String) request.getSession().getAttribute(
				"order");
		if (orderParam != null) {
			orderby = " " + orderParam + " ASC";
			if (orderDirection != null) {
				if (orderParam.equals("startprice")) {
					orderParam = "(CASE WHEN currentbet_id is null THEN startprice else betprice end)";
				}
				orderby = " " + orderParam + " " + orderDirection;
			}
		}
		if (request.getParameterMap().containsKey("orderParam")
				&& request.getParameterMap().containsKey("orderDir")) {
			request.getSession().setAttribute("orderName",
					request.getParameter("orderParam"));
			request.getSession().setAttribute("order",
					request.getParameter("orderDir"));
			orderParam = request.getParameter("orderParam");
			orderDirection = request.getParameter("orderDir");
			if (orderParam.equals("startprice")) {
				orderParam = "(CASE WHEN currentbet_id is null THEN startprice else betprice end)";
			}
			orderby = " " + orderParam + " " + orderDirection;
		}

		String enddate = "";
		String minenddate = "";
		String name = "";
		double minPrice = ServletRequestUtils.getDoubleParameter(request,
				"minprice", 0);
		maxPrice = ServletRequestUtils.getDoubleParameter(request, "maxprice",
				maxPrice);
		enddate = ServletRequestUtils.getStringParameter(request, "maxenddate",
				"");
		minenddate = ServletRequestUtils.getStringParameter(request,
				"minenddate", "");
		name = ServletRequestUtils.getStringParameter(request, "lotname", "");

		try {
			if (enddate != "") {
				enddate = dateFormat.format(formatter.parse(enddate));
				enddate = "AND enddate<='" + enddate + "'";
			}
			if (minenddate != "") {
				minenddate = dateFormat.format(formatter.parse(minenddate));
				minenddate = " AND enddate>='" + minenddate + "' ";
			}
		} catch (ParseException e) {
			model.addAttribute("errormsg",
					"Wrong date format! Enter please like (yyyy-mm-dd)");
			return "lots";
		}

		String query = "SELECT * FROM lot LEFT JOIN bet ON currentbet_id = betid "
				+ "WHERE UPPER(name) LIKE UPPER('%"
				+ name
				+ "%')"
				+ enddate
				+ minenddate
				+ " AND (currentbet_id IN (SELECT betid FROM bet WHERE betprice BETWEEN "
				+ minPrice
				+ " AND "
				+ maxPrice
				+ ") "
				+ "OR lotid IN (SELECT lotid FROM lot WHERE (startprice BETWEEN "
				+ minPrice
				+ " AND "
				+ maxPrice
				+ ")"
				+ "AND currentbet_id is null)) AND lotstate<2 ORDER BY"
				+ orderby;
		List<Lot> lots = lotService.findByPage(pg, query);

		model.addAttribute("lots", lots);
		model.addAttribute("pageNav", pageService.buildPageNav("#", lotService
				.findLots(query).size(), pg, 6, 2));
		return "filteredLots";
	}

	@RequestMapping(value = "/lots")
	public String showLotsOnPage(Model model, HttpServletRequest request) {
		int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
		String orderby = " lotid DESC";
		String query = "SELECT * FROM lot WHERE lotstate<2 ORDER BY" + orderby;
		List<Lot> lots = lotService.findByPage(pg, query);
		model.addAttribute("lots", lots);
		model.addAttribute("pageNav", pageService.buildPageNav("#", lotService
				.findLots(query).size(), pg, 6, 2));
		return "lots";
	}

	@RequestMapping(value = "/userlots/{userId}")
	public String userLotsByPage(@PathVariable Long userId, Model model,
			HttpServletRequest request) {
		int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
		String query = "SELECT * FROM lot WHERE user_id=" + userId
				+ "ORDER BY lotid DESC";
		List<Lot> lots = lotService.findByPage(pg, query);
		model.addAttribute("lots", lots);
		model.addAttribute("pageNav", pageService.buildPageNav("#", lotService
				.findLots(query).size(), pg, 6, 2));
		return "userlots";
	}

	@RequestMapping(value = "/deleteLot/{lotId}")
	public String deleteLot(@PathVariable Long lotId, Model model,
			RedirectAttributes redirectAttributes) {
		if (!lotService.deleteLot(lotId)) {
			redirectAttributes.addFlashAttribute("errormsg",
					"Your Lot is not deleted!");
			return "redirect:/lots";
		}
		return "redirect:/lots";
	}

	@RequestMapping(value = "/newlot")
	public String newLot() {
		return "newlot";
	}

	@RequestMapping(value = "/lotDetail/{lotId}")
	public String lotDetail(@PathVariable Long lotId, Model model) {
		model.addAttribute("lot", lotService.findById(lotId));
		return "lotDetail";
	}
}
