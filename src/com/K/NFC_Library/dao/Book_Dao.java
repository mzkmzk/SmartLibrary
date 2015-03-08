package com.K.NFC_Library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.K.NFC_Library.bean.book;
import com.K.NFC_Library.bean.temporary_values;
import com.K.NFC_Library.bean.user;
import com.K.NFC_Library.utils.DBUtil;

public class Book_Dao {

	 private DBUtil util;
	 
	 public Book_Dao(){
			util=DBUtil.getInstance();
		}
	 /**
	  * 用户借书Dao操作类
	  * @return
	  */
	 public int accessBook(String accessTime){
		 Connection conn  = null;
	      PreparedStatement stmt=null;
		  String sql_accessBook = "insert into user_book(U_NO,B_NO,accessBookTime) values (?,?,?)";
		  int sum =0;
		  try {
			conn = util.getConnection();
			stmt =conn.prepareStatement(sql_accessBook);
			for(int i=0;i<temporary_values.LB.size();i++){
				stmt.setString(1, temporary_values.U.getU_NO());
				stmt.setString(2, temporary_values.LB.get(i).getB_NO());
				stmt.setString(3, accessTime);
				sum +=stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn, stmt, null);
		}
		 return sum;
	 }
	 
	 /**
	  * 借书时查询有无此书
	  * @param B_NO
	  * @return
	  */
	 public book queryBook(String B_NO){
		 Connection conn  = null;
	      PreparedStatement stmt=null;
		  ResultSet rs=null;
		  String sql =" select * from book where b_no = ?";
		  book b = null;
		  try {
			conn = util.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, B_NO);
			rs = stmt.executeQuery();
			if(rs.next()){
				b =new book();
				b.setB_NO(B_NO);
				b.setB_name(rs.getString("B_name"));
				b.setB_address(rs.getString("B_address"));
				b.setB_press(rs.getString("B_press"));
				System.out.println("找到书籍 : "+b.getB_name());
			}
		  } catch (SQLException e) {
			e.printStackTrace();
		  }finally{
			util.free(conn, stmt, rs);
		  }
		  return b ; 
	 }
	 
	 /**
	  * 当用户还书时 提醒还了什么书籍
	  * @param B_NO
	  * @return
	  */
	 @SuppressWarnings("resource")
	public user queryBook_return(String B_NO,String returnTime){
		  Connection conn  = null;
	      PreparedStatement stmt=null;
		  ResultSet rs=null;
		  String sql_update ="update user_book set checkReturn = 1 ,returnBookTime = ? where B_NO = ?";
		  String sql_query  ="select * from user_book,book,user where user_book.B_NO = ? and user_book.B_NO = book.B_NO and user_book.U_NO = user.U_NO and checkReturn = 1";
		  user u = null;
		  try {
			conn = util.getConnection();
			stmt = conn.prepareStatement(sql_update);
			stmt.setString(1, returnTime);
			stmt.setString(2, B_NO);
			int updateSuccess = stmt.executeUpdate();
			if(updateSuccess >= 1){
				stmt = conn.prepareStatement(sql_query);
				stmt.setString(1, B_NO);
				rs = stmt.executeQuery();
				if(rs.next()){
					u =new user();
					u.setU_Name(rs.getString("U_name"));
					System.out.print("借书人  : "+u.getU_Name());
					book book_return =new book();
					u.setBook_return(book_return);
					book_return.setB_NO(B_NO);
					book_return.setB_name(rs.getString("B_name"));
					book_return.setB_address(rs.getString("B_address"));
					book_return.setB_press(rs.getString("B_press"));
					System.out.println("找到书籍 : "+book_return.getB_name());
				}
			}
			
		  } catch (SQLException e) {
			e.printStackTrace();
		  }finally{
			util.free(conn, stmt, rs);
		  }
		  return u ; 
	 }
	 
	 /**
	   * 刷卡添加书籍
	   * @param b
	   * @return
	   */
	  public int NFC_InsertBook(book b ){
		  Connection conn  = null;
	      PreparedStatement stmt=null;
		  String sql_accessBook = "insert into book(B_NO,B_name,B_address,B_press) values (?,?,?,?)";
		  int result =0;
		  try {
			conn = util.getConnection();
			stmt =conn.prepareStatement(sql_accessBook);
				stmt.setString(1, b.getB_NO());
				stmt.setString(2, b.getB_name());
				stmt.setString(3, b.getB_address());
				stmt.setString(4, b.getB_press());
				result=stmt.executeUpdate();
				System.out.println("添加书籍 : "+ b.getB_name());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			util.free(conn, stmt, null);
		}
		 return result;
	  }
}
