package com.cloud.licenta.app.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.cloud.licenta.app.component.UserSession;
import com.cloud.licenta.app.component.validators.LoginValidator;
import com.cloud.licenta.app.component.validators.UserValidator;
import com.cloud.licenta.app.model.Login;
import com.cloud.licenta.app.model.User;
import com.cloud.licenta.app.service.UserService;

@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private LoginValidator loginValidator;

	@Autowired
	private UserSession userSession;

	@InitBinder("register")
	protected void initRegisterBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
		binder.addValidators(userValidator);
	}

	@InitBinder("login")
	protected void initLoginBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
		binder.addValidators(loginValidator);
	}

	@ModelAttribute("login")
	public Login getLoginObject() {
		return new Login();
	}

	@ModelAttribute("register")
	public User getRegisterObject() {
		return new User();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String loadIndex(Model model, HttpServletRequest request) throws SQLException {
		if (userSession.isUserLoggedIn()) {
			model.addAttribute("sessionUserName", userSession.getLoggedInUser().getFirstName());
			return "redirect:/mainpage";
		}
		model.addAttribute("sessionUserName", null);
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public RedirectView loginSubmit(RedirectAttributes attributes, @Valid @ModelAttribute Login login,
			BindingResult result, HttpServletRequest request) {
		if (!result.hasErrors()) {
			try {
				User user = userService.processLogin(login.getEmail(), login.getPassword());
				if (user.getId() > 0) {
					userSession.setLoggedInUser(user);
					return new RedirectView("mainpage");
				} else {
					attributes.addFlashAttribute("message", "Invalid credentials");
				}

			} catch (SQLException e) {
				e.printStackTrace();
				attributes.addFlashAttribute("message", "Login failed - server problem");
			}
		} else {
			attributes.addFlashAttribute("message", "Invalid input parameters");
		}

		return new RedirectView("/");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public RedirectView registerSubmit(RedirectAttributes attributes, @Valid @ModelAttribute User user,
			BindingResult result) {

		if (!result.hasErrors()) {
			try {
				userService.processRegister(user);
				attributes.addFlashAttribute("message", "Account created, you can now log in!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			attributes.addFlashAttribute("message", "Invalid input parameters");
		}
		return new RedirectView("/");
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
