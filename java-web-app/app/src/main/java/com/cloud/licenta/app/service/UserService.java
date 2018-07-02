package com.cloud.licenta.app.service;

import java.sql.SQLException;
import java.util.List;

import com.cloud.licenta.app.model.User;

public interface UserService {

	User getById(Long id) throws SQLException;

	List<User> getAll() throws SQLException;

	void processRegister(User entity) throws SQLException;

	User processLogin(String email, String password) throws SQLException;

	void deleteById(long id) throws SQLException;

	List<String> getEmailsFilteredByUserId(List<Long> ids) throws SQLException;
}
