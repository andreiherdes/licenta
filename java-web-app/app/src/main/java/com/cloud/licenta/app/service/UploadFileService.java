package com.cloud.licenta.app.service;

import java.io.File;

public interface UploadFileService {

	String executeMultiPartRequest(String urlString, File file, String fileName, String fileDescription);
}
