package com.K.NFC_Library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.K.NFC_Library.bean.book;
import com.K.NFC_Library.bean.temporary_values;
import com.K.NFC_Library.bean.user;
import com.K.NFC_Library.utils.DBUtil;

/**
 * 此类 为User操作数据库Dao层类
 * @author maizhikun
 *
 */
public class User_Dao {
	  private DBUtil util;
	  
	  public User_Dao(){
			util=DBUtil.getInstance();
		}
	  
	  /**
	   * Android 用户修改密码
	   * @param U_studentID
	   * @param U_password
	   * @return
	   */
	  public int Android_updatePassword(String U_studentID , String U_password){
		  Connection conn  = null;
	      PreparedStatement stmt=null;
		  String sql ="update user set u_password = ?  where U_StudentID = ?";
		  int result =0;
		  try {
			conn = util.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, U_password);
			stmt.setString(2, U_studentID);
			result=  stmt.executeUpdate();
		  } catch (SQLException e) {
			e.printStackTrace();
		  }finally{
			util.free(conn, stmt, null);
		  }
		  return result ; 
	  }
	  /**
	   * 用户Android登录
	   * @param U_NO
	   * @return
	   */
	  public user Android_LoginUser(String u_StudentID,String U_password){
		  Connection conn  = null;
	      PreparedStatement stmt=null;
		  ResultSet rs=null;
		  String sql =" select * from user where u_StudentID = ? and u_password = ?";
		  user u = null;
		  try {
			conn = util.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, u_StudentID);
			stmt.setString(2, U_password);
			rs = stmt.executeQuery();
			if(rs.next()){
				u =new user();
				u.setU_NO(rs.getString("U_NO"));
				u.setU_studentID(u_StudentID);
				u.setU_Name(rs.getString("U_name"));
				System.out.println("Android 登录成功 : "+u.getU_Name());
			}
		  } catch (SQLException e) {
			e.printStackTrace();
		  }finally{
			util.free(conn, stmt, rs);
		  }
		  return u ; 
	  }
	  
	  /**
	   * 用户刷卡登录
	   * @param U_NO
	   * @return
	   */
	  public user NFC_LoginUser(String U_NO){
		  Connection conn  = null;
	      PreparedStatement stmt=null;
		  ResultSet rs=null;
		  String sql =" select * from user where u_no = ?";
		  user u = null;
		  try {
			conn = util.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, U_NO);
			rs = stmt.executeQuery();
			if(rs.next()){
				u =new user();
				u.setU_NO(U_NO);
				u.setU_Name(rs.getString("U_name"));
				System.out.println("登录成功 : "+u.getU_Name());
			}
		  } catch (SQLException e) {
			e.printStackTrace();
		  }finally{
			util.free(conn, stmt, rs);
		  }
		  return u ; 
	  }
	  
	  /**
	   * 刷卡添加用户
	   * @param u
	   * @return
	   */
	  public int NFC_InsertUser(user u ){
		  Connection conn  = null;
	      PreparedStatement stmt=null;
		  String sql_accessBook = "insert into user(U_NO,U_studentID,U_Name,U_password) values (?,?,?,'000')";
		  int result =0;
		  try {
			conn = util.getConnection();
			stmt =conn.prepareStatement(sql_accessBook);
				stmt.setString(1, u.getU_NO());
				stmt.setString(2, u.getU_studentID());
				stmt.setString(3, u.getU_Name());
				result=stmt.executeUpdate();
				System.out.println("添加用户 : "+ u.getU_studentID());

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn, stmt, null);
		}
		 return result;
	  }
	  
	  
	  /**
	   * android 通过 用户的id 查询 所借/所还的书籍
	   * @param U_NO
	   * @return
	   */
	  public List<book> AndroidVisitUser_Book(String U_NO, int checkReturn ){
		  Connection conn  = null;
	      PreparedStatement stmt=null;
		  ResultSet rs=null;
		  String sql =" select * from user_book,book where u_no = ? and book.B_NO =user_book.B_no  and checkReturn = ?";
		  List<book> lb =new ArrayList<book>();
		  try {
			conn = util.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, U_NO);
			stmt.setInt(2, checkReturn);
			rs = stmt.executeQuery();
			while(rs.next()){
				book b =new book();
				b.setB_NO(rs.getString("B_NO"));
				b.setB_name(rs.getString("book.B_name"));
				b.setB_address(rs.getString("book.B_address"));
				b.setB_press(rs.getString("book.B_press"));
				b.setB_accessBookTime(rs.getString("accessBookTime"));
				b.setB_returnBookTime(rs.getString("returnBookTime"));
				System.out.println("查询到书籍: "+b.getB_name());
				lb.add(b);
			}
		  } catch (SQLException e) {
			e.printStackTrace();
		  }finally{
			util.free(conn, stmt, rs);
		  }
		  return lb ; 
	  }
}
