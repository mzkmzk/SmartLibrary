package com.botb.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.bean.indent;
import com.botb.bean.shop;
import com.botb.dao.TongZhiIndentDao;
import com.opensymphony.xwork2.Action;

public class TongZhiIndentAction implements Action {

	private int id;
	private indent i =null;
	private shop s =null;
	private String msg ;
	private TongZhiIndentDao tzid =new TongZhiIndentDao();
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		i = tzid.TongZhiIndentDaol(request.getParameter("uphone"));
		if(i!=null){
			s=tzid.selectShopIndentDao(i.getSid());
			System.out.println(s.getSname());
			msg ="success";
		}else{
			s=null;
			msg="false";
		}
		return SUCCESS;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public indent getI() {
		return i;
	}
	public void setI(indent i) {
		this.i = i;
	}
	public shop getS() {
		return s;
	}
	public void setS(shop s) {
		this.s = s;
	}

}
