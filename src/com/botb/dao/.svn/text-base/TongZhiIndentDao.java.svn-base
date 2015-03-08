package com.botb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.botb.bean.indent;
import com.botb.bean.shop;
import com.botb.util.DBUtil;

public class TongZhiIndentDao {
	
    private DBUtil util;
	
	public TongZhiIndentDao(){
		util = DBUtil.getInstance();
	}
	
	//查询前面还有多少个人
	public int selectIndentNumDao(int sid ,String info ,int ino){
		Connection conn  = null;
		PreparedStatement ps_query_num=null;
		ResultSet rs_query_num=null;
		try {
			conn= util.getConnection();
			String 	 sql_query_2 ="select count(iid) as num from indent where sid =? and info =? and ino < ?";
			ps_query_num=conn.prepareStatement(sql_query_2);
			ps_query_num.setInt(1, sid);
			ps_query_num.setString(2, info);
			ps_query_num.setInt(3, ino);
			rs_query_num=ps_query_num.executeQuery();
			rs_query_num.next();
			return rs_query_num.getInt("num");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn,  ps_query_num, rs_query_num);
		}
		return 10;
		
	}
	
	//查询商店
	public shop selectShopIndentDao(int sid ){
		Connection conn  = null;
		PreparedStatement ps_query_num=null;
		ResultSet rs_query_num=null;
		try {
			conn= util.getConnection();
			String 	 sql_query_2 ="select * from shop where sid =?";
			ps_query_num=conn.prepareStatement(sql_query_2);
			ps_query_num.setInt(1, sid);
			rs_query_num=ps_query_num.executeQuery();
			shop s =new shop();
			if(rs_query_num.next()){
				s.setSname(rs_query_num.getString("sname"));
				s.setAddress(rs_query_num.getString("address"));
				s.setDimensionality(rs_query_num.getString("dimensionality"));
				s.setInfo(rs_query_num.getString("info"));
				s.setLongtude(rs_query_num.getString("longitude"));
				s.setSid(rs_query_num.getInt("sid"));
				s.setSphone(rs_query_num.getString("sphone"));
				s.setType(rs_query_num.getInt("type"));
				return s;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn,  ps_query_num, rs_query_num);
		}
		return null;
		
	}
	
	/**
     * 通知订单
     */
    public indent TongZhiIndentDaol(String uphone){
    	Connection conn  = null;
    	PreparedStatement ps_query=null;
		ResultSet rs_query=null;
		PreparedStatement ps_queding=null;
		ResultSet rs_queding=null;
		try {
			conn= util.getConnection();
			String sql_query="select * from indent where  uphone =? and tongzhi =0";
			ps_query=conn.prepareStatement(sql_query);
			ps_query.setString(1, uphone);
			rs_query = ps_query.executeQuery();
			indent ii =new indent();
			while(rs_query.next()){
				int num = selectIndentNumDao(rs_query.getInt("sid"),rs_query.getString("info"),rs_query.getInt("ino"));
				if(num<5){
					
					ii.setIid(rs_query.getInt("iid"));
					ii.setDengdai(num);
					ii.setInfo(rs_query.getString("info"));
					ii.setIno(rs_query.getInt("ino"));
					ii.setSid(rs_query.getInt("sid"));
					ii.setUphone(rs_query.getString("uphone"));
					String sql_queding="update indent set tongzhi =1 where iid =?";
					ps_queding=conn.prepareStatement(sql_queding);
					ps_queding.setInt(1, rs_query.getInt("iid"));
					ps_queding.executeUpdate();
					return ii;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(null, ps_queding, rs_queding);
			util.free(conn,  ps_query, rs_query);
		}
		return null;
    }
    

}
