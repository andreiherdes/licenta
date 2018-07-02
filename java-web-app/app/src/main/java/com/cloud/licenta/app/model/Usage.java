package com.cloud.licenta.app.model;

public class Usage {

	public static final String FLD_USAGE_ID = "USAGE_ID";
	public static final String FLD_REQUESTS_REMAINING = "REMAINING_REQUESTS";
	public static final String FLD_USERPLAN_ID = "FK__USER_PLAN_ID";

	private Long id;
	private Long requestsRemaining;
	private Long userPlanId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRequestsRemaining() {
		return requestsRemaining;
	}

	public void setRequestsRemaining(Long requestsRemaining) {
		this.requestsRemaining = requestsRemaining;
	}

	public Long getUserPlanId() {
		return userPlanId;
	}

	public void setUserPlanId(Long userPlanId) {
		this.userPlanId = userPlanId;
	}
}
