package com.cloud.licenta.app.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.licenta.app.dao.UserPlanDao;
import com.cloud.licenta.app.service.UploadFileService;

@RestController
public class ApiController {

	@Autowired
	private UploadFileService uploadService;

	@Autowired
	private UserPlanDao userPlanDao;

	@PostMapping("/emoApi/{key}")
	public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable("key") String key)
			throws IllegalStateException, IOException, SQLException {

		if (!userPlanDao.isApiEnabled(key)) {
			ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		if (file.isEmpty()) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		String response = uploadService.executeMultiPartRequest(
				"http://emotion-detection-api.appspot.com/predict_image", file, file.getName(), "Send flask api TEST");

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
