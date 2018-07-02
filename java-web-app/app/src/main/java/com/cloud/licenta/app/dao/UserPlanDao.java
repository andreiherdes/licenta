package com.cloud.licenta.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.cloud.licenta.app.model.UserPlan;

public interface UserPlanDao {

	UserPlan getById(Long id) throws SQLException;

	UserPlan getByUserId(Long userId) throws SQLException;

	void persistUserPlan(UserPlan entity) throws Exception;

	void deleteUserPlanById(Long id) throws SQLException;

	UserPlan isApiEnabled(String key) throws SQLException;

	void performBatchUpdate() throws Exception;

	void performRequestsRemainingUpdate(Long requests, String apiKey) throws Exception;

	public List<Long> getUserPlansWithLowRequestsRemaining() throws SQLException;

	public void performRequestsRemainingUpdate(List<UserPlan> userPlans) throws Exception;

}
