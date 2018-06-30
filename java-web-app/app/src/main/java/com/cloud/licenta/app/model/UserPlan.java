package com.cloud.licenta.app.model;

import java.util.UUID;

public class UserPlan {

	// TABLE CONSTANTS
	public static final String USERPLAN_TABLE = "USER_PLAN";
	public static final String FLD_USERPLAN_ID = "USER_PLAN_ID";
	public static final String FLD_API_KEY = "API_KEY";
	public static final String FLD_REQUESTS_REMAINING = "REMAINING_REQUESTS";
	public static final String FLD_API_TYPE = "API_TYPE";
	public static final String FLD_FK_USER_ID = "FK__USER_ID";

	private Long id;
	private String apiKey;
	private Long requestsRemaining;
	private String apiType;
	private Long userId;

	public UserPlan(Long userId) {
		this.id = (long) 0;
		this.apiKey = UUID.randomUUID().toString();
		this.requestsRemaining = (long) 0;
		this.userId = userId;
	}

	public UserPlan() {
		this.id = (long) 0;
		this.apiKey = "";
		this.requestsRemaining = (long) 0;
		this.userId = (long) 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Long getRequestsRemaining() {
		return requestsRemaining;
	}

	public void setRequestsRemaining(Long requestsRemaining) {
		this.requestsRemaining = requestsRemaining;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getApiType() {
		return apiType;
	}

	public void setApiType(String apiType) {
		this.apiType = apiType;
	}

}
