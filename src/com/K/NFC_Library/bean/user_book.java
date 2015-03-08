package com.K.NFC_Library.bean;

/**
 * 用户和书的借阅关系
 * @author maizhikun
 *
 */
public class user_book {

	/**
	 * 用户和借阅书关系的NO;
	 */
	private int U_B_NO;
	
	/**
	 * 借书用户的NO;
	 */
	private String U_NO;
	
	/**
	 * 被借阅书的NO;
	 */
	private String B_NO;
	
	/**
	 * 用户的借书籍开始时间;
	 */
	private String U_B_borrow_time;
	
	/**
	 * 用户应该归还书籍的时间;
	 */
	private String U_B_return_time;
	
	public int getU_B_NO() {
		return U_B_NO;
	}
	public void setU_B_NO(int u_B_NO) {
		U_B_NO = u_B_NO;
	}
	public String getU_NO() {
		return U_NO;
	}
	public void setU_NO(String u_NO) {
		U_NO = u_NO;
	}
	public String getB_NO() {
		return B_NO;
	}
	public void setB_NO(String b_NO) {
		B_NO = b_NO;
	}
	public String getU_B_borrow_time() {
		return U_B_borrow_time;
	}
	public void setU_B_borrow_time(String u_B_borrow_time) {
		U_B_borrow_time = u_B_borrow_time;
	}
	public String getU_B_return_time() {
		return U_B_return_time;
	}
	public void setU_B_return_time(String u_B_return_time) {
		U_B_return_time = u_B_return_time;
	}
	
	
	
	
}
