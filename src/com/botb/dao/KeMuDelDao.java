package com.botb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.botb.bean.kemu;
import com.botb.util.DBUtil;

/**
 * 商店删除科目
 * @author K
 *
 */
public class KeMuDelDao {
	 private DBUtil util;
		
		public KeMuDelDao(){
			util = DBUtil.getInstance();
		}
		
		/**
		 * 删除科目
		 */
		public int deleKemu(kemu k){
			Connection conn = null;
			PreparedStatement stmt = null;
			PreparedStatement stmt_query = null;
			int flag = 0; 
			try {
				conn = util.getConnection();
				String sql  ="delete from kemu where kmname =? and sid =?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, k.getKmname());
				stmt.setInt(2, k.getSid());
				flag = stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn, stmt, null);
			}
			return flag;
		}
}
