package com.cloud.licenta.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cloud.licenta.app.model.User;
import com.cloud.licenta.app.model.UserPlan;

public class DaoUtils {

	static void loadUser(ResultSet result, User user) throws SQLException {
		user.setId(result.getLong(User.FLD_ID));
		user.setPassword(result.getString(User.FLD_PASSWORD));
		user.setEmail(result.getString(User.FLD_EMAIL));
		user.setFirstName(result.getString(User.FLD_FIRST_NAME));
		user.setLastName(result.getString(User.FLD_LAST_NAME));
		user.setOrganization(result.getBoolean(User.FLD_IS_ORGANIZATION));
	}

	static void loadUserPlan(ResultSet result, UserPlan userPlan) throws SQLException {
		userPlan.setId(result.getLong(UserPlan.FLD_USERPLAN_ID));
		userPlan.setApiKey(result.getString(UserPlan.FLD_API_KEY));
		userPlan.setUserId(result.getLong(UserPlan.FLD_FK_USER_ID));
		userPlan.setApiType(result.getString(UserPlan.FLD_API_TYPE));
		userPlan.setRequestsRemaining(result.getLong(UserPlan.FLD_REQUESTS_REMAINING));
	}
}
