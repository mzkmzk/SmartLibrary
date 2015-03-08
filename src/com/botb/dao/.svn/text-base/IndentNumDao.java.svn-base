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

public class IndentNumDao {
	
    private DBUtil util;
	
	public IndentNumDao(){
		util = DBUtil.getInstance();
	}
	
	//根据电话和商店确定indent
	public indent selectIndentDao(int sid ,String uphone ){
		Connection conn  = null;
		PreparedStatement ps_query_num=null;
		ResultSet rs_query_num=null;
		indent i =new indent();
		try {
			conn= util.getConnection();
			String 	 sql_query_2 ="select* from indent where sid =? and uphone=?";
			ps_query_num=conn.prepareStatement(sql_query_2);
			ps_query_num.setInt(1, sid);
			ps_query_num.setString(2, uphone);
			rs_query_num=ps_query_num.executeQuery();
			if(rs_query_num.next()){
				i.setIid(rs_query_num.getInt("iid"));
				i.setInfo(rs_query_num.getString("info"));
				i.setIno(rs_query_num.getInt("ino"));
				i.setSid(sid);
				i.setUphone(uphone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn,  ps_query_num, rs_query_num);
		}
		return i;
		
	}
	
	//查询前面还有多少个人
	public indent selectIndentNumDao(int sid ,String uphone){
		Connection conn  = null;
		PreparedStatement ps_query_num=null;
		ResultSet rs_query_num=null;
		indent i = selectIndentDao(sid,uphone);
		try {
			conn= util.getConnection();
			String 	 sql_query_2 ="select count(iid) as num from indent where sid =? and info =? and ino < ?";
			ps_query_num=conn.prepareStatement(sql_query_2);
			ps_query_num.setInt(1, sid);
			ps_query_num.setString(2, i.getInfo());
			ps_query_num.setInt(3, i.getIno());
			rs_query_num=ps_query_num.executeQuery();
			rs_query_num.next();
			i.setDengdai(rs_query_num.getInt("num"));
			System.out.println(i.getDengdai());
			System.out.println(i.getInfo());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn,  ps_query_num, rs_query_num);
		}
		return i;
		
	}
	
	
    

}
