package com.botb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.bean.kemu;
import com.botb.bean.user;
import com.botb.dao.KeMuAddDao;
import com.botb.dao.RegisterDao;
import com.opensymphony.xwork2.Action;

public class KeMuAddAction implements Action {

	private int id;
	private String msg ;
	private KeMuAddDao  kmd =new KeMuAddDao();
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
			kemu k =new kemu();
			k.setSid(Integer.parseInt(request.getParameter("sid")));
			k.setKmname(request.getParameter("kmname"));
			int flag =kmd.addKemu(k);
			if(flag==0){
				msg ="false";
			}else if (flag == 1 ){
				msg ="success";
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
