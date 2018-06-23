package com.cloud.licenta.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.cloud.licenta.app.model.User;

public interface UserDao {

	User getById(Long id) throws SQLException;

	List<User> getAll() throws SQLException;

	void persist(User entity) throws SQLException;

	void deleteById(long id) throws SQLException;

	boolean isEmailValid(String email) throws SQLException;

	User getByCredentials(String password, String email) throws SQLException;

}
