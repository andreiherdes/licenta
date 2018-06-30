package com.cloud.licenta.app.dao;

import java.sql.SQLException;

import com.cloud.licenta.app.model.Contact;

public interface ContactDao {

	void persistContactRequest(Contact contact) throws SQLException;
}
