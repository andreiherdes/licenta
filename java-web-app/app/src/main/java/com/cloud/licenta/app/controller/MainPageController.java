package com.cloud.licenta.app.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloud.licenta.app.component.UserSession;
import com.cloud.licenta.app.dao.UserPlanDao;
import com.cloud.licenta.app.model.UserPlan;
import com.cloud.licenta.app.service.UploadFileService;

@Controller
@RequestMapping("/mainpage")
public class MainPageController {

	@Autowired
	private UserSession userSession;

	@Autowired
	private UserPlanDao userPlanDao;

	@Autowired
	public UploadFileService uploadService;

	@RequestMapping(method = RequestMethod.GET)
	public String loadMainPage(Model model, HttpServletRequest request) throws SQLException {
		model.addAttribute("sessionUserName", userSession.getLoggedInUser().getFirstName());
		return "mainpage";
	}

	@PostMapping("/uploadFile")
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
			throws IllegalStateException, IOException {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/mainpage";
		}
		String response = uploadService.executeMultiPartRequest(
				"http://emotion-detection-api.appspot.com/predict_image", file, file.getName(), "Send flask api TEST");
		redirectAttributes.addFlashAttribute("message", "Response: " + response);
		return "redirect:/mainpage";
	}

	@PostMapping("/generateKey")
	public String singleFileUpload(@RequestParam("plan") String plan, RedirectAttributes redirectAttributes)
			throws IllegalStateException, IOException, SQLException {
		if (userSession.getLoggedInUser().getUserPlan().getId() == 0) {
			UserPlan userPlan = new UserPlan(userSession.getLoggedInUser().getId());
			if (plan.equals("1")) {
				userPlan.setApiType("Personal");
				userPlan.setRequestsRemaining((long) 200);
			} else if (plan.equals("2")) {
				userPlan.setApiType("Business");
				userPlan.setRequestsRemaining((long) 500000);
			} else if (plan.equals("3")) {
				userPlan.setApiType("Enterprise");
			}

			userPlanDao.persistUserPlan(userPlan);
			userSession.getLoggedInUser().setUserPlan(userPlan);
			return "redirect:/profile";
		}
		redirectAttributes.addFlashAttribute("message", "You already have a generated key!");
		return "redirect:/mainpage";
	}

	public UploadFileService getUploadService() {
		return uploadService;
	}

	public void setUploadService(UploadFileService uploadService) {
		this.uploadService = uploadService;
	}
}
