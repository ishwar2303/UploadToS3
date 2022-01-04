package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConDao {
	
	String api = "jdbc:mysql://";
	String server = "mydb.cakcwgna7dgk.ap-south-1.rds.amazonaws.com";
	String port = "3306";
	String database = "app";
	String username = "ishwar2303";
	String password = "23031999";
	
	public Connection connection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = api + server + ":" + port + "/" + database;
		Connection con = DriverManager.getConnection(url, username, password);
		return con;
	}
	
}
