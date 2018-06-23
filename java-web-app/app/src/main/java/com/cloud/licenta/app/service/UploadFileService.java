package com.cloud.licenta.app.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface UploadFileService {

	String executeMultiPartRequest(String urlString, MultipartFile file, String fileName, String fileDescription)
			throws UnirestException, IllegalStateException, IOException;
}
