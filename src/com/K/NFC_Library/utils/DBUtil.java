package com.K.NFC_Library.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

	
	public class DBUtil {
		
		private static DBUtil instance;
		private String dirverClassName = "com.mysql.jdbc.Driver";
		private String url = "jdbc:mysql://localhost:3306/NFC_Library";
		private String user = "root";
		private String password = "";
		private String host = "localhost";
		
		private DBUtil() {
			try {
				Class.forName(dirverClassName);
			} catch (ClassNotFoundException e) {
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
				e.printStackTrace();
			}
			
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		
		
	}
