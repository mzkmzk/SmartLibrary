package com.botb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.botb.bean.shop;
import com.botb.bean.user;
import com.botb.util.DBUtil;

/**
 * 商铺修改信息
 * @author K
 *
 */
public class updateSeverDao {
	 private DBUtil util;
		
		public updateSeverDao(){
			util = DBUtil.getInstance();
		}
		/**
		 * 商铺修改信息
		 */
		public int updateSever(shop s){
			Connection conn = null;
			PreparedStatement stmt = null;
			int flag=0;
			try {
				conn = util.getConnection();
				String sql  ="update shop set address =? ,info =? ,sname =? where sid =? ";
				
				stmt = conn.prepareStatement(sql);
				stmt.setString(1,s.getAddress());
				stmt.setString(2,s.getInfo());
				stmt.setString(3,s.getSname());
				stmt.setInt(4,s.getSid());
				flag = stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn, stmt, null);
			}
			return flag;
		}
}
