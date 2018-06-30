package com.cloud.licenta.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloud.licenta.app.component.UserSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private UserSession userSession;

	@RequestMapping(method = RequestMethod.GET)
	public String loadProfilePage(Model model, HttpServletRequest request) {
		model.addAttribute("sessionUserName", userSession.getLoggedInUser().getFirstName());
		model.addAttribute("key", userSession.getLoggedInUser().getUserPlan().getApiKey());
		model.addAttribute("currPlan", userSession.getLoggedInUser().getUserPlan().getApiType());
		model.addAttribute("remainingReq", userSession.getLoggedInUser().getUserPlan().getRequestsRemaining());
		return "profile";
	}
}
