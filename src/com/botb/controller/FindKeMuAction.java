package com.botb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.bean.indent;
import com.botb.bean.kemu;
import com.botb.bean.shop;
import com.botb.dao.FindIndentDao;
import com.botb.dao.FindKeMuDao;
import com.botb.dao.FindShopDao;
import com.opensymphony.xwork2.Action;

public class FindKeMuAction implements Action {

	private int id;
	private List<kemu> lk =new ArrayList<kemu>();
	private String msg ;
	private FindKeMuDao fkmd =new FindKeMuDao();
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		lk = fkmd.FindKemuDaol(Integer.parseInt(request.getParameter("sid")));
		if(lk!=null)
			msg ="success";
		else
			msg="false";
		return SUCCESS;
	}

	public List<kemu> getLk() {
		return lk;
	}

	public void setLk(List<kemu> lk) {
		this.lk = lk;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
