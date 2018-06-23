package com.cloud.licenta.app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.cloud.licenta.app.utils.Credentials;

public class CloudSqlConnection {

	public final static CloudSqlConnection INSTANCE = new CloudSqlConnection();
	public final String sqlUrl;

	private CloudSqlConnection() {
		sqlUrl = String.format(
				"jdbc:mysql://google/%s?cloudSqlInstance=%s"
						+ "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false",
				"CLOUDCOMPUTING", Credentials.INSTANCE_NAME);
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(sqlUrl, Credentials.USERNAME, Credentials.PASSWORD);
	}

}
