package com.botb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.bean.shop;
import com.botb.bean.user;
import com.botb.dao.RegisterCheckPhoneDao;
import com.botb.dao.RegisterDao;
import com.opensymphony.xwork2.Action;

public class RegisterCheckPhoneAction implements Action {

	private int id;
	private String msg ;
	private RegisterCheckPhoneDao  dao =new RegisterCheckPhoneDao();
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		if(request.getParameter("type").equals("2")){
			int flag = dao.checkUser(request.getParameter("phone"));
			System.out.println(flag);
			if(flag==0){
				msg ="手机已经注册过,请选择别的手机号";
			}else if (flag == 1 ){
				msg ="success";
			}
		}else{
			System.out.println(request.getParameter("phone"));
			int flag = dao.checkShop(request.getParameter("phone"));
			if(flag==0){
				msg ="此企业手机号已注册,请选择别的手机号";
			}else if (flag == 1 ){
				msg ="success";
			}
		}
		
		return SUCCESS;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
