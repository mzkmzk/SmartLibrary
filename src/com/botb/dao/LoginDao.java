package com.botb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.botb.bean.shop;
import com.botb.bean.user;
import com.botb.util.DBUtil;

/**
 * 用户/商铺登陆类
 * @author K
 *
 */
public class LoginDao {
      private DBUtil util;
	
	public LoginDao(){
		util = DBUtil.getInstance();
	}
	/**
	 * 用户登陆
	 * @param u
	 * @return
	 */
	public user LoginDaoUser(user u ){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			String sql = "select uid from user where uphone = ? and passwd = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getUphone());
			stmt.setString(2, u.getPasswd());
			rs = stmt.executeQuery();
			if(rs.next()){
				u.setUid(rs.getInt("uid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn, stmt, rs);
		}
		return u;
	}
	
	/**
	 * 商铺登陆
	 * @param s
	 * @return
	 */
	public shop LoginDaoShop(shop s ){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			String sql = "select * from shop where sphone = ? and passwd = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, s.getSphone());
			stmt.setString(2, s.getPasswd());
			rs = stmt.executeQuery();
			if(rs.next()){
				s.setAddress(rs.getString("address"));
				s.setSid(rs.getInt("sid"));
				s.setSname(rs.getString("sname"));
				s.setSNow(rs.getInt("sNow"));
				s.setType(rs.getInt("type"));
                s.setInfo(rs.getString("info"));
                s.setSelectInfo(rs.getString("selectinfo"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn, stmt, rs);
		}
		return s;
	}
}
