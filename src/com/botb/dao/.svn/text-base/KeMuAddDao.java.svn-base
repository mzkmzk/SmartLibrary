package com.botb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.botb.bean.kemu;
import com.botb.util.DBUtil;

/**
 * 商店增加科目
 * @author K
 *
 */
public class KeMuAddDao {
	 private DBUtil util;
		
		public KeMuAddDao(){
			util = DBUtil.getInstance();
		}
		
		/**
		 * 添加科目
		 */
		public int addKemu(kemu k){
			Connection conn = null;
			PreparedStatement stmt = null;
			PreparedStatement stmt_query =null;
			ResultSet rs =null;
			int flag=0;
			try {
				conn = util.getConnection();
				
				String sql_query ="select kmid from kemu where kmname=? and sid = ?";
				stmt_query =conn.prepareStatement(sql_query);
				stmt_query.setString(1, k.getKmname());
				stmt_query.setInt(2, k.getSid());
				rs=stmt_query.executeQuery();
				if(rs.next()){
					return 0;
				}
				String sql = null;
					sql ="insert into kemu(sid,kmname) values (?,?)";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, k.getSid());
				stmt.setString(2, k.getKmname());
				flag = stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn, stmt, null);
			}
			return flag;
		}
}
