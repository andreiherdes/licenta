package com.cloud.licenta.app.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {

	String executeMultiPartRequest(String urlString, MultipartFile file, String fileName, String fileDescription)
			throws IllegalStateException, IOException;
}
