package com.botb.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.bean.indent;
import com.botb.bean.shop;
import com.botb.dao.IndentNumDao;
import com.botb.dao.TongZhiIndentDao;
import com.opensymphony.xwork2.Action;
/**
 * 我的排号页面需要查询当前还有多少人 和类型
 * @author hp
 *
 */
public class IndentNumAction implements Action {

	private int id;
	private indent i =null;
	private String msg ;
	private IndentNumDao idnd =new IndentNumDao();
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		i = idnd.selectIndentNumDao(Integer.parseInt(request.getParameter("sid")), request.getParameter("uphone"));
		if(i!=null){
			msg ="success";
		}else{
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

}
