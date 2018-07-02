package com.cloud.licenta.app.component;

import java.util.ArrayList;
import java.util.List;

import com.cloud.licenta.app.model.UserPlan;

public class ApiSession {

	public final static ApiSession INSTANCE = new ApiSession();

	private List<UserPlan> userPlans;

	private ApiSession() {
		userPlans = new ArrayList<>();
	}

	public List<UserPlan> getUserPlans() {
		return userPlans;
	}

}
