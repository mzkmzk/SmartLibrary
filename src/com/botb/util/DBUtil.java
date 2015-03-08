package com.botb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	
	private static DBUtil instance;
	private String dirverClassName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/botb";
	private String user = "root";
	private String password = "123456";
	
	private DBUtil() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName(dirverClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DBUtil getInstance(){
		if(instance  ==null){
			instance = new DBUtil();
		}
		return instance;
	}
	
	public Connection getConnection() throws SQLException{
		Connection conn =null;
		conn = DriverManager.getConnection(url,user,password);
		return conn;
	}
	
	public void free(Connection conn,Statement stmt,ResultSet rs){
		try {
			if(rs!=null){
				rs.close();
			}
			if(stmt!=null){
				stmt.close();
			}
			if(conn!=null){
				conn.close();
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	
}
