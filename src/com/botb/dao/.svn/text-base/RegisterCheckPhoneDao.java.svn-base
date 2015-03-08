package com.botb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.botb.util.DBUtil;

public class RegisterCheckPhoneDao {
	 private DBUtil util;
		
		public RegisterCheckPhoneDao(){
			util = DBUtil.getInstance();
		}
		
		/**
		 * 检查顾客手机是否重复
		 */
		public int checkUser(String phone){
			Connection conn = null;
			PreparedStatement stmt = null;
			PreparedStatement stmt_query =null;
			ResultSet rs =null;
			int flag=1;
			try {
				conn = util.getConnection();
				String sql_query ="select uid from user where uphone=?";
				stmt_query =conn.prepareStatement(sql_query);
				stmt_query.setString(1, phone);
				rs=stmt_query.executeQuery();
				if(rs.next()){
					return 0;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn, stmt, rs);
			}
			return flag;
		}
		
		/**
		 * 检查商家手机
		 */
		public int checkShop(String phone){
			Connection conn = null;
			PreparedStatement stmt = null;
			PreparedStatement stmt_query =null;
			ResultSet rs =null;
			int flag=1;
			try {
				conn = util.getConnection();
				String sql_query ="select sid from shop where sphone=?";
				stmt_query =conn.prepareStatement(sql_query);
				stmt_query.setString(1, phone);
				rs=stmt_query.executeQuery();
				if(rs.next()){
					return 0;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn, stmt, rs);
			}
			return flag;
		}
}
