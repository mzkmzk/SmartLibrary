package com.K.NFC_Library.action;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.K.NFC_Library.bean.user;
import com.K.NFC_Library.dao.User_Dao;
import com.opensymphony.xwork2.Action;

/**
 * NFC刷卡添加用户
 * @author maizhikun
 *
 */
public class NFC_InsertUser implements Action{
	
	@Override
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		User_Dao ud = new User_Dao();
		user u =new user();
		u.setU_NO(request.getParameter("NFC_ID"));
		System.out.println(u.getU_NO()+"U_no");
		if(!u.getU_NO().isEmpty()){
		u.setU_studentID(request.getParameter("U_studentID"));
		u.setU_Name(request.getParameter("U_name"));
		request.getSession().setAttribute("message", 	(ud.NFC_InsertUser(u)+"").equals("1") ? "23" :"-1");
		}else{
			request.getSession().setAttribute("message", "-1");
		}
		System.out.println("添加用户 返回的message " +request.getSession().getAttribute("message"));
		return "Loding_NFC.jsp";
	}
	

}
