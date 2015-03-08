package com.botb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.botb.bean.indent;
import com.botb.util.DBUtil;

public class FindIndentDao {
	
    private DBUtil util;
	
	public FindIndentDao(){
		util = DBUtil.getInstance();
	}
	
	/**
     * 查找订单
     */
    public List<indent> FindIndentDaol(int sid ,String type){
    	Connection conn  = null;
    	PreparedStatement ps=null;
		ResultSet rs=null;
		String 	 sql ="select * from indent where  sid = ? and info =?";
		List<indent> indents =new ArrayList<indent>();
		try {
			conn= util.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sid);
			ps.setString(2, type);
			rs =ps.executeQuery();
			while(rs.next()){
				indent i =new indent();
				i.setIid(rs.getInt("iid"));
				i.setInfo(rs.getString("info"));
				i.setIno(rs.getInt("ino"));
				i.setSid(rs.getInt("sid"));
				i.setUphone(rs.getString("uphone"));
				indents.add(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn,  ps, rs);
		}
		return indents;
    }
    

}
