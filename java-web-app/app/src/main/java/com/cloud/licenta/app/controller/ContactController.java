package com.cloud.licenta.app.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloud.licenta.app.component.EmailServiceImpl;
import com.cloud.licenta.app.component.validators.ContactValidator;
import com.cloud.licenta.app.dao.ContactDao;
import com.cloud.licenta.app.model.Contact;

@Controller
@RequestMapping("/contact")
public class ContactController {

	@Autowired
	private EmailServiceImpl emailService;

	@Autowired
	private ContactDao contactDao;

	@Autowired
	private ContactValidator contactValidator;

	@ModelAttribute("contact")
	public Contact getRegisterObject() {
		return new Contact();
	}

	@InitBinder("contact")
	protected void initRegisterBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
		binder.addValidators(contactValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String loadContactPage() {
		return "contact";
	}

	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public String handleContactRequest(RedirectAttributes redirectAttributes, @Valid @ModelAttribute Contact contact,
			BindingResult result, HttpServletRequest request) throws SQLException {
		if (!result.hasErrors()) {
			contactDao.persistContactRequest(contact);
			emailService.sendSimpleMessage(contact.getEmail(), "EmoAPI Contact Response",
					generateGreetingsText(contact.getFirstName()));

			redirectAttributes.addFlashAttribute("message", "Your e-mail was sent!");
			return "redirect:/";
		}

		redirectAttributes.addFlashAttribute("message", "Invalid input!");
		return "redirect:/contact";
	}

	private String generateGreetingsText(String firstName) {
		return "Hello, " + firstName + "! We got your e-mail, help is on the way!";
	}

}
