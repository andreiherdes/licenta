package com.cloud.licenta.app.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloud.licenta.app.component.UserSession;
import com.cloud.licenta.app.service.UploadFileService;

@Controller
@RequestMapping("/mainpage")
public class MainPageController {

	@Autowired
	private UserSession userSession;

	@RequestMapping(method = RequestMethod.GET)
	public String loadPage(Model model, HttpServletRequest request) throws SQLException {
		model.addAttribute("sessionUserName", userSession.getLoggedInUser().getFirstName());
		return "mainpage";

	}

	@Autowired
	public UploadFileService uploadService;

	@GetMapping("/upload")
	public String upload() {
		return "upload";
	}

	@PostMapping("/uploadFile") // //new annotation since 4.3
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
			throws IllegalStateException, IOException {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/mainpage";
		}
		String response = uploadService.executeMultiPartRequest(
				"http://emotion-detection-api.appspot.com/predict_image", file, file.getName(), "Send flask api TEST");
		redirectAttributes.addFlashAttribute("message", "Emotion: '" + response + "'");
		return "redirect:/mainpage";
	}

	@GetMapping("/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}

	public UploadFileService getUploadService() {
		return uploadService;
	}

	public void setUploadService(UploadFileService uploadService) {
		this.uploadService = uploadService;
	}
}
