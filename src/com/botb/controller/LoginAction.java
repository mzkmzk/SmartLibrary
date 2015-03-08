package com.botb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.bean.user;
import com.botb.dao.LoginDao;
import com.opensymphony.xwork2.Action;

public class LoginAction implements Action {

	private int id;
	private String msg ;
	private LoginDao ld =new LoginDao();
	private user u ;
	private String type ="user";
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		String uphone = request.getParameter("uphone");
		String passwd = request.getParameter("passwd");
		u =new user();
		u.setUphone(uphone);
		u.setPasswd(passwd);
		ld.LoginDaoUser(u);
		System.out.println(u.getUid());
		if(u.getUid()!=0){
			msg = "success";
		}else{
			msg ="false";
		}
		return SUCCESS;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public user getU() {
		return u;
	}

	public void setU(user u) {
		this.u = u;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
