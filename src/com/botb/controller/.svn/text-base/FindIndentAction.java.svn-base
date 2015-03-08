package com.botb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.bean.indent;
import com.botb.bean.shop;
import com.botb.dao.FindIndentDao;
import com.botb.dao.FindShopDao;
import com.opensymphony.xwork2.Action;

public class FindIndentAction implements Action {

	private int id;
	private List<indent> li =new ArrayList<indent>();
	private String msg ;
	private FindIndentDao fid =new FindIndentDao();
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		li = fid.FindIndentDaol(Integer.parseInt(request.getParameter("sid")), request.getParameter("dataType"));
		if(li!=null)
			msg ="success";
		else
			msg="false";
		return SUCCESS;
	}
	public List<indent> getLi() {
		return li;
	}

	public void setLi(List<indent> li) {
		this.li = li;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
