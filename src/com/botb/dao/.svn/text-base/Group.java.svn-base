package com.botb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.botb.util.DBUtil;

/**
 * 管理商店分组
 * @author hp
 *
 */
public class Group {
	 private DBUtil util;
		
		public Group(){
			util = DBUtil.getInstance();
		}
	
	//编辑分组
		public int updateGroup(String group , int sid ){
			Connection conn  = null;
	    	PreparedStatement ps=null;
			ResultSet rs=null;
			String 	 sql ="update shop set selectinfo =? where sid = ?";
			int flag =-1;
			try {
				conn=util.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, group);
				ps.setInt(2, sid);
				flag =ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn,  ps, rs);
			}
			return flag;
		}
}
