package com.cloud.licenta.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.cloud.licenta.app.component.UserSession;

@Controller
@RequestMapping("/logout")
public class LogoutController {

	@Autowired
	private UserSession userSession;

	@RequestMapping(method = RequestMethod.POST)
	public RedirectView performLogout() {
		userSession.setLoggedInUser(null);
		return new RedirectView("/");
	}

}
