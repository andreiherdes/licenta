package com.cloud.licenta.app.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.licenta.app.dao.UserDao;
import com.cloud.licenta.app.dao.UserPlanDao;
import com.cloud.licenta.app.model.User;
import com.cloud.licenta.app.model.UserPlan;
import com.cloud.licenta.app.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserPlanDao userPlanDao;

	@Override
	public User getById(Long id) throws SQLException {
		return userDao.getById(id);
	}

	@Override
	public List<User> getAll() throws SQLException {
		return userDao.getAll();
	}

	@Override
	public void processRegister(User entity) throws SQLException {

		if (userDao.isEmailValid(entity.getEmail())) {
			userDao.persist(entity);
		} else {
			throw new SQLException("Invalid Email!");
		}

	}

	@Override
	public void deleteById(long id) throws SQLException {
		userDao.deleteById(id);
	}

	@Override
	public User processLogin(String email, String password) throws SQLException {
		User user = userDao.getByCredentials(password, email);
		if (user.getId() > 0) {
			Long userId = user.getId();
			UserPlan userPlan = userPlanDao.getByUserId(userId);
			user.setUserPlan(userPlan);
		}
		return user;
	}

	@Override
	public List<String> getEmailsFilteredByUserId(List<Long> ids) throws SQLException {
		List<User> users = userDao.getAll();

		List<String> filteredUsers = users.stream().filter(x -> ids.contains(x.getId())).map(User::getEmail)
				.collect(Collectors.toList());

		return filteredUsers;

	}

}
