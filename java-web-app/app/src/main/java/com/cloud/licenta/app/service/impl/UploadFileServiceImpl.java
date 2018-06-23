package com.cloud.licenta.app.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import com.cloud.licenta.app.service.UploadFileService;

@SuppressWarnings("deprecation")
@Service(value = "uploadService")
public class UploadFileServiceImpl implements UploadFileService {

	/**
	 * A generic method to execute any type of Http Request and constructs a
	 * response object
	 * 
	 * @param requestBase
	 *            the request that needs to be exeuted
	 * @return server response as <code>String</code>
	 */
	@SuppressWarnings("resource")
	private static String executeRequest(HttpRequestBase requestBase) {
		String responseString = "";

		InputStream responseStream = null;
		HttpClient client = new DefaultHttpClient();
		try {
			HttpResponse response = client.execute(requestBase);
			if (response != null) {
				HttpEntity responseEntity = response.getEntity();

				if (responseEntity != null) {
					responseStream = responseEntity.getContent();
					if (responseStream != null) {
						BufferedReader br = new BufferedReader(new InputStreamReader(responseStream));
						String responseLine = br.readLine();
						String tempResponseString = "";
						while (responseLine != null) {
							tempResponseString = tempResponseString + responseLine
									+ System.getProperty("line.separator");
							responseLine = br.readLine();
						}
						br.close();
						if (tempResponseString.length() > 0) {
							responseString = tempResponseString;
						}
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (responseStream != null) {
				try {
					responseStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		client.getConnectionManager().shutdown();

		return responseString;
	}

	/**
	 * Method that builds the multi-part form data request
	 * 
	 * @param urlString
	 *            the urlString to which the file needs to be uploaded
	 * @param file
	 *            the actual file instance that needs to be uploaded
	 * @param fileName
	 *            name of the file, just to show how to add the usual form
	 *            parameters
	 * @param fileDescription
	 *            some description for the file, just to show how to add the usual
	 *            form parameters
	 * @return server response as <code>String</code>
	 */
	public String executeMultiPartRequest(String urlString, File file, String fileName, String fileDescription) {

		HttpPost postRequest = new HttpPost(urlString);

		FileBody fileBody = new FileBody(file, ContentType.IMAGE_JPEG);
		StringBody stringBody1 = new StringBody("LOL SA TI DAU LABOGA 1", ContentType.MULTIPART_FORM_DATA);
		StringBody stringBody2 = new StringBody("LOL SA TI DAU LABOGA 2", ContentType.MULTIPART_FORM_DATA);
		//
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart("photo", fileBody);
		builder.addPart("text1", stringBody1);
		builder.addPart("text2", stringBody2);
		HttpEntity entity = builder.build();
		//
		postRequest.setEntity(entity);
		return executeRequest(postRequest);
	}

	/*
	 * public static void main(String args[]){ SampleFileUpload fileUpload = new
	 * SampleFileUpload () ; File file = new File ("Hydrangeas.jpg") ;
	 * 
	 * String response = fileUpload.executeMultiPartRequest("<Request URL>", file,
	 * file.getName(), "File Upload test Hydrangeas.jpg description") ;
	 * System.out.println("Response : "+response); }
	 */

}
