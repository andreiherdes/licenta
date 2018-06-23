
package com.cloud.licenta.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloud.licenta.app.service.UploadFileService;
import com.mashape.unirest.http.exceptions.UnirestException;

@Controller
public class UploadController {

	// Save the uploaded file to this folder
	private static String UPLOADED_FOLDER = "E://temp//";

	@Autowired
	public UploadFileService uploadService;

	@GetMapping("/")
	public String index() {
		return "upload";
	}

	@PostMapping("/upload") // //new annotation since 4.3
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
			throws IllegalStateException, IOException, UnirestException {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:uploadStatus";
		}

		String response = uploadService.executeMultiPartRequest("http://127.0.0.1:8050/predict_image", file,
				file.getName(), "Send flask api TEST");

		redirectAttributes.addFlashAttribute("message", "Emotion: '" + response + "'");

		return "redirect:/uploadStatus";
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
