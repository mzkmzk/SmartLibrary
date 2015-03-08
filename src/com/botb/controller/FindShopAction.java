package com.botb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.bean.shop;
import com.botb.dao.FindShopDao;
import com.opensymphony.xwork2.Action;

public class FindShopAction implements Action {

	private int id;
	private List<shop> ls =new ArrayList<shop>();
	private String msg ;
	private FindShopDao fsd =new FindShopDao();
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		//ls = fsd.FindShopDaol(request.getParameter("sinfo"),Integer.parseInt(request.getParameter("sid")));
		ls = fsd.FindShopDaol(request.getParameter("sinfo"),Integer.parseInt(request.getParameter("type")));
		if(ls!=null)
			msg ="success";
		else
			msg="false";
		return SUCCESS;
	}
	public List<shop> getLs() {
		return ls;
	}
	public void setLs(List<shop> ls) {
		this.ls = ls;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
