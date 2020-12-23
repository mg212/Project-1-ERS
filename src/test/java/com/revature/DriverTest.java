package com.revature;

import static org.junit.Assert.assertTrue;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.sun.corba.se.pept.transport.Connection;

public class DriverTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		assertTrue(true);
	}
	
//	testing SQLException if DB login details are invalid
	@Test(expected = SQLException.class)
	public void establishedDbConnection() throws SQLException {
		java.sql.Connection connection = DriverManager.getConnection("1", "1", "1");
	}

}
