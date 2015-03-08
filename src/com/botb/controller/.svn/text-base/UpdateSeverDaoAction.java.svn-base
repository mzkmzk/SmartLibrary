package com.botb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.bean.shop;
import com.botb.dao.DingWeiDao;
import com.botb.dao.updateSeverDao;
import com.opensymphony.xwork2.Action;

public class UpdateSeverDaoAction implements Action {

	private int id ;
	private String msg ;
	private shop s;
	public String execute() throws Exception {
		updateSeverDao usd =new updateSeverDao();
		HttpServletRequest request =ServletActionContext.getRequest();
		s =new shop();
		s.setAddress(request.getParameter("address"));
		s.setSname(request.getParameter("sname"));
		s.setInfo(request.getParameter("info"));
		s.setSid(Integer.parseInt(request.getParameter("sid")));
		  int flag = usd.updateSever(s);
		 if(flag ==1){
			 msg="success";
		 }else {
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
	public shop getS() {
		return s;
	}
	public void setS(shop s) {
		this.s = s;
	}

}
