package com.botb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.botb.bean.shop;
import com.botb.bean.user;
import com.botb.util.DBUtil;

/**
 * 用户和商铺注册
 * @author K
 *
 */
public class RegisterDao {
	 private DBUtil util;
		
		public RegisterDao(){
			util = DBUtil.getInstance();
		}
		
		/**
		 * 添加商铺
		 */
		public int addShop(shop s){
			Connection conn = null;
			PreparedStatement stmt = null;
			PreparedStatement stmt_query =null;
			PreparedStatement stmt_indent =null;
			ResultSet rs =null;
			int flag=0;
			try {
				conn = util.getConnection();
				String sql_query ="select sid from shop where sname=?";
				stmt_query =conn.prepareStatement(sql_query);
				stmt_query.setString(1, s.getSname());
				rs=stmt_query.executeQuery();
				if(rs.next()){
					return 0;
				}
				String sql ="insert into shop(sname,address,passwd,sphone,type,info) values (?,?,?,?,?,?)";
				
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, s.getSname());
				stmt.setString(2, s.getAddress());
				stmt.setString(3, s.getPasswd());
				stmt.setString(4, s.getSphone());
				stmt.setInt(5, s.getType());
				stmt.setString(6, s.getInfo());
				System.out.println("action : "+ s.getInfo());
				flag = stmt.executeUpdate();
				rs=stmt_query.executeQuery();
				rs.next();
				s.setSid(rs.getInt("sid"));
				String sql_inser;
				if(s.getType()==1){
					sql_inser="insert into kemu(sid,kmname) values ("+s.getSid()+", '1~2人'),("+s.getSid()+", '3~4人'),("+s.getSid()+", '5~8人'),("+s.getSid()+", '9~12人'),("+s.getSid()+", '12~16人')";
				}else if(s.getType()==2){
					sql_inser="insert into kemu(sid,kmname) values ("+s.getSid()+", '骨科'),("+s.getSid()+", '普通外科'),("+s.getSid()+",'神经外科')";
				}else{
					sql_inser="insert into kemu(sid,kmname) values ("+s.getSid()+", '个人业务'),("+s.getSid()+", '企业业务')";
				}
				stmt_indent=conn.prepareStatement(sql_inser);
				stmt_indent.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn, stmt, rs);
			}
			return flag;
		}
		
		/**
		 * 添加客户
		 */
		public int addUser(user u){
			Connection conn = null;
			PreparedStatement stmt = null;
			PreparedStatement stmt_query =null;
			ResultSet rs =null;
			int flag=0;
			try {
				conn = util.getConnection();
				
				String sql_query ="select uid from user where uphone=?";
				stmt_query =conn.prepareStatement(sql_query);
				System.out.println(u.getUphone());
				stmt_query.setString(1, u.getUphone());
				rs=stmt_query.executeQuery();
				if(rs.next()){
					return 0;
				}
				String sql = null;
					sql ="insert into user(uphone,passwd) values (?,?)";
				stmt = conn.prepareStatement(sql);
				System.out.println(u.getUphone()+"2222222");
				System.out.println(u.getPasswd());
				stmt.setString(1, u.getUphone());
				stmt.setString(2, u.getPasswd());
				flag = stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				util.free(conn, stmt, null);
			}
			return flag;
		}
}
