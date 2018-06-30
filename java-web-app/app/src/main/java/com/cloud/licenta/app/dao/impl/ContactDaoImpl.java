package com.cloud.licenta.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.cloud.licenta.app.dao.ContactDao;
import com.cloud.licenta.app.database.CloudSqlConnection;
import com.cloud.licenta.app.model.Contact;

@Repository
public class ContactDaoImpl implements ContactDao {

	private Connection conn;

	@Override
	public void persistContactRequest(Contact entity) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement(
				"INSERT INTO " + Contact.CONTACT_TABLE + "(" + Contact.FLD_EMAIL + "," + Contact.FLD_FIRST_NAME + ","
						+ Contact.FLD_LAST_NAME + "," + Contact.FLD_TEXT + ") VALUES (?,?,?,?)");
		stmt.setString(1, entity.getEmail());
		stmt.setString(2, entity.getFirstName());
		stmt.setString(3, entity.getLastName());
		stmt.setString(4, entity.getText());

		stmt.executeUpdate();
		conn.close();
	}

}
