package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	//Connection information
	private static final String URL = "jdbc:postgresql://localhost/postgres";
	private static final String USERNAME = "java_user";
	private static final String PASSWORD = "p4ssw0rd";
	private static Connection conn;
	
	public static Connection getConnection() {
		//Try to make the connection and then return it!
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
