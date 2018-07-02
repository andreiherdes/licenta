package com.cloud.licenta.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	public void persistUserPlan(UserPlan entity) throws Exception {
		PreparedStatement stmt = null;
		try {
			conn = CloudSqlConnection.INSTANCE.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement("INSERT INTO " + UserPlan.USERPLAN_TABLE + "(" + UserPlan.FLD_API_KEY + ","
					+ UserPlan.FLD_REQUESTS_REMAINING + "," + UserPlan.FLD_FK_USER_ID + "," + UserPlan.FLD_API_TYPE
					+ ") VALUES (?,?,?,?)");
			stmt.setString(1, entity.getApiKey());
			stmt.setLong(2, entity.getRequestsRemaining());
			stmt.setLong(3, entity.getUserId());
			stmt.setString(4, entity.getApiType());

			stmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			throw new Exception("Transaction failed!", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}

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
	public UserPlan isApiEnabled(String key) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();
		PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM " + UserPlan.USERPLAN_TABLE + " WHERE " + UserPlan.FLD_API_KEY + " = ?");
		stmt.setString(1, key);

		ResultSet result = stmt.executeQuery();

		UserPlan userPlan = new UserPlan();
		if (result.next()) {
			DaoUtils.loadUserPlan(result, userPlan);
		}

		conn.close();
		return userPlan;
	}

	@Override
	public void performBatchUpdate() throws Exception {
		PreparedStatement stmt = null;
		try {
			conn = CloudSqlConnection.INSTANCE.getConnection();

			conn.setAutoCommit(false);

			stmt = conn.prepareStatement("UPDATE " + UserPlan.USERPLAN_TABLE + " SET " + UserPlan.FLD_REQUESTS_REMAINING
					+ " = ? WHERE " + UserPlan.FLD_API_TYPE + " = ? AND " + UserPlan.FLD_REQUESTS_REMAINING + " != ?");

			stmt.setLong(1, 200);
			stmt.setString(2, "Personal");
			stmt.setLong(3, 200);
			stmt.addBatch();

			stmt.setLong(1, 500000);
			stmt.setString(2, "Bussines");
			stmt.setLong(3, 500000);
			stmt.addBatch();

			stmt.executeBatch();

			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Transaction failed!", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	@Override
	public void performRequestsRemainingUpdate(List<UserPlan> userPlans) throws Exception {
		PreparedStatement stmt = null;
		try {
			conn = CloudSqlConnection.INSTANCE.getConnection();

			conn.setAutoCommit(false);

			stmt = conn.prepareStatement("UPDATE " + UserPlan.USERPLAN_TABLE + " SET " + UserPlan.FLD_REQUESTS_REMAINING
					+ " = ? WHERE " + UserPlan.FLD_USERPLAN_ID + " = ?");

			for (UserPlan userPlan : userPlans) {
				stmt.setLong(1, userPlan.getRequestsRemaining());
				stmt.setLong(2, userPlan.getId());
				stmt.addBatch();
			}

			stmt.executeBatch();

			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Transaction failed!", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	@Override
	public void performRequestsRemainingUpdate(Long requests, String apiKey) throws Exception {

		PreparedStatement stmt = null;
		try {
			conn = CloudSqlConnection.INSTANCE.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement("UPDATE " + UserPlan.USERPLAN_TABLE + " SET " + UserPlan.FLD_REQUESTS_REMAINING
					+ " = ? WHERE " + UserPlan.FLD_API_KEY + " = ?");

			stmt.setLong(1, --requests);
			stmt.setString(2, apiKey);

			stmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw new Exception("Transaction failed!", e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<Long> getUserPlansWithLowRequestsRemaining() throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("SELECT " + UserPlan.FLD_FK_USER_ID + " FROM " + UserPlan.USERPLAN_TABLE + " WHERE "
						+ UserPlan.FLD_REQUESTS_REMAINING + " < 100 AND " + UserPlan.FLD_REQUESTS_REMAINING + ">0");
		ResultSet result = stmt.executeQuery();

		List<Long> usersWithLowUsage = new ArrayList<>();
		while (result.next()) {
			usersWithLowUsage.add(result.getLong(UserPlan.FLD_FK_USER_ID));
		}

		stmt.close();
		conn.close();

		return usersWithLowUsage;

	}

}
