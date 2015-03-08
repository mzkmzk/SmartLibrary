package com.botb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.botb.bean.shop;
import com.botb.bean.user;
import com.botb.util.DBUtil;

/**
 * 商铺定位
 * @author K
 *
 */
public class DingWeiDao {
	 private DBUtil util;
		
		public DingWeiDao(){
			util = DBUtil.getInstance();
		}
		
		
		
		/**
		 * 商铺定位
		 */
		public int DingWei(double[] d,int sid ){
			Connection conn = null;
			PreparedStatement stmt = null;
			int flag=0;
			try {
				conn = util.getConnection();
				String sql  ="update shop set longitude =? ,dimensionality =? where sid =? ";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1,d[0]+"");
				stmt.setString(2,d[1]+"");
				stmt.setInt(3, sid);
				flag = stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn, stmt, null);
			}
			return flag;
		}
		
		/**
		 * 商铺_手机定位
		 */
		public int DingWei_shouji(double[] d,String phone ){
			Connection conn = null;
			PreparedStatement stmt = null;
			int flag=0;
			try {
				conn = util.getConnection();
				String sql  ="update shop set longitude =? ,dimensionality =? where sphone =? ";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1,d[0]+"");
				stmt.setString(2,d[1]+"");
				stmt.setString(3, phone);
				flag = stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn, stmt, null);
			}
			return flag;
		}
}
