package com.botb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.botb.bean.shop;
import com.botb.util.DBUtil;

public class FindShopDao {
	
    private DBUtil util;
	private FindKeMuDao fkmd =new FindKeMuDao();
	public FindShopDao(){
		util = DBUtil.getInstance();
	}
	
	/**
     * 搜索商铺
     */
    public List<shop> FindShopDaol(String info ,int type){
    	Connection conn  = null;
    	PreparedStatement ps=null;
		ResultSet rs=null;
		String  sql =null;
		if(type==0){
			 sql ="select * from shop where  sname like '%"+info+"%'";
		}else if(type==1){
			sql ="select * from shop where  sname like '%"+info+"%' and type=1";
		}else if(type==2){
			sql ="select * from shop where  sname like '%"+info+"%' and type=2";
		}else if(type==3){
			sql ="select * from shop where  sname like '%"+info+"%' and type=3";
		}
			
		List<shop> shops =new ArrayList<shop>();
		try {
			conn= util.getConnection();
			ps = conn.prepareStatement(sql);
			rs =ps.executeQuery();
			while(rs.next()){
				shop shop =new shop();
				shop.setAddress(rs.getString("address"));
				shop.setSid(rs.getInt("sid"));
				shop.setSname(rs.getString("sname"));
				shop.setSNow(rs.getInt("sNow"));
				shop.setSphone(rs.getString("sphone"));
				shop.setInfo(rs.getString("info"));
				shop.setLongtude(rs.getString("longitude"));
				shop.setDimensionality(rs.getString("dimensionality"));
				System.out.println(rs.getString("dimensionality"));
				shop.setSelectInfo(rs.getString("selectinfo"));
				shop.setType(rs.getInt("type"));
				shop.setSelectInfo_2(fkmd.FindKemuSDaol(rs.getInt("sid")));
				shops.add(shop);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn,  ps, rs);
		}
		return shops;
    }
    

    
}
