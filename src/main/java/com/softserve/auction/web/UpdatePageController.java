package com.softserve.auction.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softserve.auction.service.HibernateChangesListener;

@Controller
public class UpdatePageController {
	@Resource
	private HibernateChangesListener listener;

	@RequestMapping(value = { "/update" })
	public @ResponseBody long home(Model model) {
		//System.out.println("update");
		return listener.getVersion();
	}
}
