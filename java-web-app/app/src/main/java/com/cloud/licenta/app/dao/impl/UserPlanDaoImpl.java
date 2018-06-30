package com.cloud.licenta.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.cloud.licenta.app.dao.UserPlanDao;
import com.cloud.licenta.app.database.CloudSqlConnection;
import com.cloud.licenta.app.model.UserPlan;

@Repository
public class UserPlanDaoImpl implements UserPlanDao {

	private Connection conn;

	@Override
	public UserPlan getById(Long id) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();
		PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM " + UserPlan.USERPLAN_TABLE + " WHERE " + UserPlan.FLD_USERPLAN_ID + " = ?");
		stmt.setLong(1, id);

		ResultSet result = stmt.executeQuery();

		UserPlan userPlan = new UserPlan();
		if (result.next()) {
			DaoUtils.loadUserPlan(result, userPlan);
		}

		conn.close();

		return userPlan;
	}

	@Override
	public void persistUserPlan(UserPlan entity) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("INSERT INTO " + UserPlan.USERPLAN_TABLE + "(" + UserPlan.FLD_API_KEY + ","
						+ UserPlan.FLD_REQUESTS_REMAINING + "," + UserPlan.FLD_FK_USER_ID + ") VALUES (?,?,?)");
		stmt.setString(1, entity.getApiKey());
		stmt.setLong(2, entity.getRequestsRemaining());
		stmt.setLong(3, entity.getUserId());

		stmt.executeUpdate();
		conn.close();

	}

	@Override
	public void deleteUserPlanById(Long id) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public UserPlan getByUserId(Long userId) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();
		PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM " + UserPlan.USERPLAN_TABLE + " WHERE " + UserPlan.FLD_FK_USER_ID + " = ?");
		stmt.setLong(1, userId);

		ResultSet result = stmt.executeQuery();

		UserPlan userPlan = new UserPlan();
		if (result.next()) {
			DaoUtils.loadUserPlan(result, userPlan);
		}

		conn.close();

		return userPlan;
	}

	@Override
	public boolean isApiEnabled(String key) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();
		PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM " + UserPlan.USERPLAN_TABLE + " WHERE " + UserPlan.FLD_API_KEY + " = ?");
		stmt.setString(1, key);

		ResultSet result = stmt.executeQuery();

		boolean isApiEnabled = false;
		if (result.next()) {
			isApiEnabled = true;
		}

		conn.close();
		return isApiEnabled;
	}

}
