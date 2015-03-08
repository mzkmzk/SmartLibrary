package com.botb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.botb.bean.kemu;
import com.botb.util.DBUtil;

public class FindKeMuDao {
	
    private DBUtil util;
	
	public FindKeMuDao(){
		util = DBUtil.getInstance();
	}
	
	/**
     * 查找科目
     */
    public List<kemu> FindKemuDaol(int sid){
    	Connection conn  = null;
    	PreparedStatement ps=null;
		ResultSet rs=null;
		String 	 sql ="select * from kemu where  sid = ? ";
		List<kemu> kemus =new ArrayList<kemu>();
		try {
			conn= util.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sid);
			rs =ps.executeQuery();
			while(rs.next()){
				kemu k =new kemu();
				k.setKmid(rs.getInt("kmid"));
				k.setSid(rs.getInt("sid"));
				k.setKmname(rs.getString("kmname"));
				kemus.add(k);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn,  ps, rs);
		}
		return kemus;
    }
    

    /**
     * 查找科目String
     */
    public List<String> FindKemuSDaol(int sid){
    	Connection conn  = null;
    	PreparedStatement ps=null;
		ResultSet rs=null;
		String 	 sql ="select * from kemu where  sid = ? ";
		List<String> kemus =new ArrayList<String>();
		try {
			conn= util.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sid);
			rs =ps.executeQuery();
			while(rs.next()){
				kemus.add(rs.getString("kmname"));
				System.out.println(rs.getString("kmname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn,  ps, rs);
		}
		return kemus;
    }
}
