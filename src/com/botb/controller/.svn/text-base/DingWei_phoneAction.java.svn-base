package com.botb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.dao.DingWeiDao;
import com.opensymphony.xwork2.Action;

public class DingWei_phoneAction implements Action {

	private int id ;
	private String msg ;
	public String execute() throws Exception {
		DingWeiDao dwd =new DingWeiDao();
		HttpServletRequest request =ServletActionContext.getRequest();
		double[] d ={Double.parseDouble(request.getParameter("longitude")),Double.parseDouble(request.getParameter("dimensionality"))};
		  int flag = dwd.DingWei_shouji(d,request.getParameter("phone"));
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

}
