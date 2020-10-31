package com.afcs.web.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

	private static final String DASHBOARD = "dashboard";

	@GetMapping(value = DASHBOARD)
	public ModelAndView showDashboard(Map<String, Object> model, HttpSession session) {
		return new ModelAndView(DASHBOARD);
	}
}
