package com.revature.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConnectionUtil {
	private static Logger log = Logger.getLogger(ConnectionUtil.class);

	public static Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			log.warn("Cannot load database driver.", e);
		}
		Properties props = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Connection conn = null;
		try {
			props.load(loader.getResourceAsStream("connection.properties"));
			String url = props.getProperty("url");
			String dbUsername = props.getProperty("username");
			String dbPassword = props.getProperty("password");

			try {
				conn = DriverManager.getConnection(url, dbUsername, dbPassword);
			} catch (SQLException e) {
				log.warn("Unable to connect to the db", e);
			}
		} catch (IOException ex) {
			log.warn("Could not read properties file", ex);
		}
		return conn;
	}
}
