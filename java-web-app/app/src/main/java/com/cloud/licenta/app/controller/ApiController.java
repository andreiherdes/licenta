package com.cloud.licenta.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.licenta.app.component.ApiSession;
import com.cloud.licenta.app.dao.UserPlanDao;
import com.cloud.licenta.app.model.UserPlan;
import com.cloud.licenta.app.service.UploadFileService;
import com.cloud.licenta.app.utils.Credentials;

@RestController
public class ApiController {

	@Autowired
	private UploadFileService uploadService;

	@Autowired
	private UserPlanDao userPlanDao;

	@RequestMapping(method = RequestMethod.POST, value = "/emoApi/{key}", produces = "application/json")
	public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable("key") String key)
			throws Exception {
		Optional<UserPlan> result = ApiSession.INSTANCE.getUserPlans().stream().filter(x -> x.getApiKey().equals(key))
				.findAny();
		UserPlan userPlan = null;
		if (result.isPresent()) {
			userPlan = result.get();
		} else {
			userPlan = userPlanDao.isApiEnabled(key);
			ApiSession.INSTANCE.getUserPlans().add(userPlan);
		}

		if (userPlan.getId() == 0 || userPlan.getRequestsRemaining() <= 0) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		String response = uploadService.executeMultiPartRequest(Credentials.API_URI, file, file.getName(),
				"Send flask api TEST");
		if (!userPlan.getApiType().equals("Enterprise") || !response.contains("number_of_faces: 0")) {
			userPlan.setRequestsRemaining(userPlan.getRequestsRemaining() - 1);
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
