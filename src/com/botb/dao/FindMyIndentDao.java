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

public class FindMyIndentDao {
	
    private DBUtil util;
    private FindKeMuDao fkmd =new FindKeMuDao();
	public FindMyIndentDao(){
		util = DBUtil.getInstance();
	}
	
	
	
	//查询订单商店
	public List<shop> selectMyIndentDao(String uphone ){
		Connection conn  = null;
		PreparedStatement ps_query_num=null;
		ResultSet rs_query_num=null;
		List<shop> ls =new ArrayList<shop>();
		try {
			conn= util.getConnection();
			String 	 sql_query_2 ="select * from shop,indent where shop.sid=indent.sid and indent.uphone=?";
			ps_query_num=conn.prepareStatement(sql_query_2);
			ps_query_num.setString(1, uphone);
			rs_query_num=ps_query_num.executeQuery();
			while(rs_query_num.next()){
				shop s =new shop();
				s.setSname(rs_query_num.getString("sname"));
				s.setAddress(rs_query_num.getString("address"));
				s.setDimensionality(rs_query_num.getString("dimensionality"));
				s.setInfo(rs_query_num.getString("info"));
				s.setLongtude(rs_query_num.getString("longitude"));
				s.setSid(rs_query_num.getInt("shop.sid"));
				s.setSphone(rs_query_num.getString("sphone"));
				s.setType(rs_query_num.getInt("type"));
				s.setInfo(rs_query_num.getString("info"));
				s.setSelectInfo(rs_query_num.getString("selectInfo"));
				s.setSelectInfo_2(fkmd.FindKemuSDaol(rs_query_num.getInt("shop.sid")));
				ls.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn,  ps_query_num, rs_query_num);
		}
		return ls;
		
	}
	
	
    

}
