package com.K.NFC_Library.action;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.K.NFC_Library.bean.temporary_values;
import com.K.NFC_Library.bean.user;
import com.opensymphony.xwork2.Action;

/**
 * 此类 用作处理 Web前台请求显示借书数据
 * @author maizhikun
 *
 */
public class WebVisitTemporary_values_return implements Action{
	
	@Override
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		request.setAttribute("LB_return", temporary_values.LU_return);
		return "repayBook.jsp";
	}
	
	/**
	 * 重置Web前端还书记录
	 * @return
	 */
	public String Clean_WebVisitTemporary_values_return(){
		temporary_values.LU_return= new  ArrayList<user>();
		return "WebVisitTemporary_values_return";
	}

}
