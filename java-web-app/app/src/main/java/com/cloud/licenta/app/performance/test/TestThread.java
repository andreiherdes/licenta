package com.cloud.licenta.app.performance.test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.cloud.licenta.app.service.UploadFileService;
import com.cloud.licenta.app.service.impl.UploadFileServiceImpl;

public class TestThread extends Thread {

	private final static Logger LOGGER = Logger.getLogger(TestThread.class);

	private Thread t;
	private String threadName;
	UploadFileService uploadService;

	TestThread(String name) {
		threadName = name;
		uploadService = new UploadFileServiceImpl();
		LOGGER.debug("Creating " + threadName);
	}

	// File representing the folder that you select using a FileChooser
	static final File dir = new File("e:\\Facultate\\licenta\\python-flask-api\\frames\\29-4-2018\\");

	// array of supported extensions (use a List if you prefer)
	static final String[] EXTENSIONS = new String[] { "jpg", "png", "bmp" // and other formats you need
	};
	// filter to identify images based on their extensions
	static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(final File dir, final String name) {
			for (final String ext : EXTENSIONS) {
				if (name.endsWith("." + ext)) {
					return (true);
				}
			}
			return (false);
		}
	};

	@Override
	public void run() {
		LOGGER.debug("Running " + threadName);

		if (dir.isDirectory()) { // make sure it's a directory
			for (final File f : dir.listFiles(IMAGE_FILTER)) {
				try {
					String response = uploadService.testExecutePostFile(
							"http://emotion-detection-api.appspot.com/predict_image", f, f.getName(),
							"Send flask api TEST");
					LOGGER.debug("Response: " + response);
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		LOGGER.debug("Thread " + threadName + " exiting.");
	}

	@Override
	public void start() {
		LOGGER.debug("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}