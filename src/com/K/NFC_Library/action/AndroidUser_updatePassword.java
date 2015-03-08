package com.K.NFC_Library.action;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.K.NFC_Library.dao.User_Dao;
import com.opensymphony.xwork2.Action;

/**
 * Android 用户修改 密码 
 * @author maizhikun
 *
 */
public class AndroidUser_updatePassword implements Action{
	private int id;
	private int result = 0 ;
	@Override
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		User_Dao ud =new User_Dao();
		String U_studentID =  request.getParameter("U_studentID");
		String U_password = request.getParameter("U_password");
		System.out.println("Android 用户 修改 密码 学号为 : "+U_studentID);
		result = ud.Android_updatePassword(U_studentID, U_password);
		return SUCCESS;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	

}
