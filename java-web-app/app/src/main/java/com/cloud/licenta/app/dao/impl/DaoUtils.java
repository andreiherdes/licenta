package com.cloud.licenta.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cloud.licenta.app.model.User;

public class DaoUtils {

	static void loadUser(ResultSet result, User user) throws SQLException {
		user.setId(result.getLong(User.FLD_ID));
		user.setPassword(result.getString(User.FLD_PASSWORD));
		user.setEmail(result.getString(User.FLD_EMAIL));
		user.setFirstName(result.getString(User.FLD_FIRST_NAME));
		user.setLastName(result.getString(User.FLD_LAST_NAME));
		user.setPlan(result.getString(User.FLD_PLAN));
		user.setOrganization(result.getBoolean(User.FLD_IS_ORGANIZATION));
	}
}
