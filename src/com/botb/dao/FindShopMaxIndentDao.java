package com.botb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.botb.bean.shop;
import com.botb.util.DBUtil;

public class FindShopMaxIndentDao {
	
    private DBUtil util;
	
	public FindShopMaxIndentDao(){
		util = DBUtil.getInstance();
	}
	
	/**
     * 查找当前最大订单号
     */
    public int FindShopDaol(String info ,int sid){
    	Connection conn  = null;
    	PreparedStatement ps=null;
		ResultSet rs=null;
		String 	 sql ="select count(iid) as max from indent where info =? and sid = ?";
		int max =-1;
		try {
			conn=util.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, info);
			ps.setInt(2, sid);
			rs =ps.executeQuery();
			rs.next();
			max=rs.getInt("max");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn,  ps, rs);
		}
		return max;
    }
    

}
