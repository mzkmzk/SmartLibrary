package com.botb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.dao.DingWeiDao;
import com.opensymphony.xwork2.Action;

public class DingWeiAction implements Action {

	private int id ;
	private String msg ;
	public String execute() throws Exception {
		DingWeiDao dwd =new DingWeiDao();
		HttpServletRequest request =ServletActionContext.getRequest();
		double[] d ={Double.parseDouble(request.getParameter("longitude")),Double.parseDouble(request.getParameter("dimensionality"))};
		  int flag = dwd.DingWei(d,Integer.parseInt(request.getParameter("sid")));
		 if(flag ==1){
			 msg="success";
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
