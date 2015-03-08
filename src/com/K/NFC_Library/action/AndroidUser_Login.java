package com.K.NFC_Library.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.tomcat.jni.User;

import com.K.NFC_Library.bean.book;
import com.K.NFC_Library.bean.user;
import com.K.NFC_Library.dao.User_Dao;
import com.opensymphony.xwork2.Action;

/**
 * 此类 Android 用户登录 
 * @author maizhikun
 *
 */
public class AndroidUser_Login implements Action{
	private int id;
	private user u=new user();
	
	@Override
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		User_Dao ud =new User_Dao();
		String U_studentID =  request.getParameter("U_studentID");
		String U_password =request.getParameter("U_password");
		u  = ud.Android_LoginUser(U_studentID, U_password);
		if(u!=null)
		System.out.println("Android 用户登录 姓名为 :  "+u.getU_Name());
		return SUCCESS;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public user getU() {
		return u;
	}

	public void setU(user u) {
		this.u = u;
	}

	
	

}
