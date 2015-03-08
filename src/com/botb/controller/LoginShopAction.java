package com.botb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.bean.shop;
import com.botb.bean.user;
import com.botb.dao.LoginDao;
import com.opensymphony.xwork2.Action;

public class LoginShopAction implements Action {

	private int id;
	private String msg ;
	private LoginDao ld =new LoginDao();
	private shop s ;
	private String type ="shop";
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		String uphone = request.getParameter("uphone");
		String passwd = request.getParameter("passwd");
		s =new shop();
		s.setSphone(uphone);
		s.setPasswd(passwd);
		ld.LoginDaoShop(s);
		if(s.getSid()!=0){
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

	public shop getS() {
		return s;
	}

	public void setS(shop s) {
		this.s = s;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
