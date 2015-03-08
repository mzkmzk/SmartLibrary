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

public class AccessNumberDao {
	 private DBUtil util;
		
		public AccessNumberDao(){
			util = DBUtil.getInstance();
		}
		
		//查询前面有多少个号
		public int selectAccessNumber(int sid ,String info ){
			Connection conn  = null;
	    	PreparedStatement ps=null;
			ResultSet rs=null;
			int num = 1;
			String 	 sql ="select max(ino) as num from indent where sid= ? and info =?";
			try {
				conn= util.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, sid);
				ps.setString(2, info);
				rs =ps.executeQuery();
				rs.next();
				num=rs.getInt("num")+1;
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn,  ps, rs);
			}
			return num;
		}
		
		//查询该号是否排过该商店的号
		public int selectUid(int sid ,String uphone){
			Connection conn  = null;
	    	PreparedStatement ps=null;
			ResultSet rs=null;
			int num = 0;
			String 	 sql ="select count(iid) as num from indent where sid= ? and uphone =?";
			try {
				conn= util.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, sid);
				ps.setString(2, uphone);
				rs =ps.executeQuery();
				rs.next();
				num=rs.getInt("num");
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn,  ps, rs);
			}
			return num;
		}
		
		/**
		 * 排号
		 * @param i
		 * @return
		 */
		public int AccessNumber(indent i ){
			Connection conn  = null;
	    	PreparedStatement ps=null;
			ResultSet rs=null;
			int flag =0;
			int ino =  selectAccessNumber(i.getSid(),i.getInfo());
			int  bo = selectUid(i.getSid() ,i.getUphone());
			if(bo!=0)
				return 0;
			String sql ="insert into indent(iid,sid,uphone,ino,info) values (?,?,?,?,?)" ;
			try {
				conn =util.getConnection();
				ps=conn.prepareStatement(sql);
				ps.setInt(1, i.getIid());
				ps.setInt(2, i.getSid());
				ps.setString(3, i.getUphone());
				ps.setInt(4, ino);
				ps.setString(5, i.getInfo());
				flag = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn,  ps, rs);
			}
			return flag;
		}
		
		//删除排号
		public int deleteNumber(indent i ){
			Connection conn  = null;
	    	PreparedStatement ps=null;
			ResultSet rs=null;
			int flag =0;
			String sql ="delete from indent where iid=?" ;
			try {
				conn =util.getConnection();
				ps=conn.prepareStatement(sql);
				System.out.println(i.getIid());
				ps.setInt(1, i.getIid());
				flag = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn,  ps, rs);
			}
			return flag;
		} 
}
