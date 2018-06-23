package com.cloud.licenta.app.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
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
import org.springframework.web.multipart.MultipartFile;

import com.cloud.licenta.app.service.UploadFileService;
import com.mashape.unirest.http.exceptions.UnirestException;

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
			org.apache.http.HttpResponse response = client.execute(requestBase);
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
	 * @throws UnirestException
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@Override
	public String executeMultiPartRequest(String urlString, MultipartFile file, String fileName, String fileDescription)
			throws UnirestException, IllegalStateException, IOException {

		HttpPost postRequest = new HttpPost(urlString);

		File convertedFile = convert(file);

		FileBody fileBody = new FileBody(convertedFile, ContentType.create("multipart/form-data", Consts.UTF_8));
		//StringBody stringBody1 = new StringBody("LOL SA TI DAU LABOGA 1", ContentType.MULTIPART_FORM_DATA);
		//StringBody stringBody2 = new StringBody("LOL SA TI DAU LABOGA 2", ContentType.MULTIPART_FORM_DATA);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart("photo", fileBody);
		//builder.addPart("text1", stringBody1);
		//builder.addPart("text2", stringBody2);
		HttpEntity entity = builder.build();
		postRequest.setEntity(entity);
		return executeRequest(postRequest);

		/*
		 * final byte[] bytes = file.getBytes(); final
		 * com.mashape.unirest.http.HttpResponse<JsonNode> jsonResponse =
		 * Unirest.post(urlString) .header("Content-type", "raw").field("photo",
		 * bytes).asJson();
		 * 
		 * com.mashape.unirest.http.HttpResponse<JsonNode> jsonResponse =
		 * Unirest.post(urlString) .header("Content-type",
		 * 
		 * "multipart/form-data").header("accept", "application/json").field("photo",
		 * file) .asJson();
		 * 
		 * return jsonResponse.getBody().toString();
		 */
	}

	private static File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
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
