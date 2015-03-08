package com.botb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.bean.indent;
import com.botb.dao.AccessNumberDao;
import com.opensymphony.xwork2.Action;

public class AccessNumberAction implements Action {

	private indent i ;
	private int id ;
	private int msg ;
	public String execute() throws Exception {
		AccessNumberDao and =new AccessNumberDao();
		HttpServletRequest request =ServletActionContext.getRequest();
		i =new indent();
		i.setSid(Integer.parseInt(request.getParameter("sid")));
		i.setUphone(request.getParameter("uphone"));
		i.setInfo(request.getParameter("info"));
		 msg = and.AccessNumber(i);
		return SUCCESS;
	}
	public indent getI() {
		return i;
	}
	public void setI(indent i) {
		this.i = i;
	}
	public int getMsg() {
		return msg;
	}
	public void setMsg(int msg) {
		this.msg = msg;
	}

}
