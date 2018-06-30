package com.cloud.licenta.app.dao;

import java.sql.SQLException;

import com.cloud.licenta.app.model.UserPlan;

public interface UserPlanDao {

	UserPlan getById(Long id) throws SQLException;

	UserPlan getByUserId(Long userId) throws SQLException;

	void persistUserPlan(UserPlan entity) throws SQLException;

	void deleteUserPlanById(Long id) throws SQLException;

	boolean isApiEnabled(String key) throws SQLException;

}
