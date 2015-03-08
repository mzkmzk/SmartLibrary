package com.botb.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.dao.FindShopMaxIndentDao;
import com.opensymphony.xwork2.Action;

public class FindShopMaxIndentAction implements Action {

	private int id;
	private int max = -1;
	private FindShopMaxIndentDao fsdmx=new FindShopMaxIndentDao();
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		max = fsdmx.FindShopDaol(request.getParameter("info"), Integer.parseInt(request.getParameter("sid")));
		System.out.println(request.getParameter("info"));
		System.out.println(max);
		return SUCCESS;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}

}
