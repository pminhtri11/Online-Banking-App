package com.green.bank;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.green.bank.database.JDBC_Connect;
import com.green.bank.util.DatabaseException;

public class TestDatsource {
	
	static BasicDataSource  ds= new BasicDataSource();
	static {
		Properties p = new Properties();
		try {
		p.load(JDBC_Connect.class.getResourceAsStream("/database-test.properties"));
		ds.setUsername(p.getProperty("jdbc.username"));
		ds.setPassword(p.getProperty("jdbc.password"));
		ds.setUrl( p.getProperty("jdbc.url"));
		ds.setMaxTotal(200);
		ds.setMinIdle(50);
		ds.setMaxConnLifetimeMillis(2000);//after 2 sec, connection would end automatically
		ds.setMaxWaitMillis(1000);//200 requests, already got the connection
		//201 is coming, so withn 2 sec, possibility that the connection would be free
		}catch(IOException e) {
		}
	}
	public static Connection getConnection() throws DatabaseException{
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			throw new DatabaseException("Error obtaining connection: "+ e.getMessage());
		}
	}
	public static void main(String[] args) throws DatabaseException {
		TestDatsource.getConnection();
	}
	
}
