package com.cloud.licenta.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.cloud.licenta.app.dao.UserDao;
import com.cloud.licenta.app.database.CloudSqlConnection;
import com.cloud.licenta.app.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	private Connection conn;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User getById(Long id) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM " + User.USER_TABLE + " WHERE " + User.FLD_ID + " = ?");
		stmt.setLong(1, id);

		ResultSet result = stmt.executeQuery();

		User user = new User();

		DaoUtils.loadUser(result, user);

		conn.close();

		return user;
	}

	@Override
	public List<User> getAll() throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + User.USER_TABLE);
		ResultSet result = stmt.executeQuery();

		List<User> users = new ArrayList<>();
		while (result.next()) {
			User user = new User();

			users.add(user);
			DaoUtils.loadUser(result, user);
		}
		conn.close();
		return users;
	}

	@Override
	public void persist(User entity) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + User.USER_TABLE + "(" + User.FLD_PASSWORD + ","
				+ User.FLD_EMAIL + "," + User.FLD_FIRST_NAME + "," + User.FLD_LAST_NAME + "," + User.FLD_PHONE_NUMBER
				+ ") VALUES (?,?,?,?,?)");
		stmt.setString(1, passwordEncoder.encode(entity.getPassword()));
		stmt.setString(2, entity.getEmail());
		stmt.setString(3, entity.getFirstName());
		stmt.setString(4, entity.getLastName());
		stmt.setString(5, entity.getPhoneNumber());

		stmt.executeUpdate();
		conn.close();

	}

	@Override
	public void deleteById(long id) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + User.USER_TABLE + " WHERE ID = ?");
		stmt.setLong(1, id);

		int deleted = stmt.executeUpdate();

		if (deleted == 0) {
			throw new SQLException("Nothing to delete");
		}
		conn.close();
	}

	@Override
	public boolean isEmailValid(String email) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM " + User.USER_TABLE + " WHERE " + User.FLD_EMAIL + " = ?");
		stmt.setString(1, email);

		ResultSet result = stmt.executeQuery();

		boolean isValid = result.next() ? false : true;
		conn.close();

		return isValid;
	}

	@Override
	public User getByCredentials(String password, String email) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM " + User.USER_TABLE + " WHERE " + User.FLD_EMAIL + " = ?");
		stmt.setString(1, email);

		ResultSet result = stmt.executeQuery();
		User user = new User();

		if (result.next()) {
			String hashedPassword = result.getString(User.FLD_PASSWORD);
			if (passwordEncoder.matches(password, hashedPassword))

			{
				DaoUtils.loadUser(result, user);
			}
		}

		conn.close();

		return user;
	}

}
