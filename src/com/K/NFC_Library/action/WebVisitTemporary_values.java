package com.K.NFC_Library.action;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.K.NFC_Library.bean.temporary_values;
import com.opensymphony.xwork2.Action;

/**
 * 此类 用作处理 Web前台请求显示借书数据
 * @author maizhikun
 *
 */
public class WebVisitTemporary_values implements Action{
	
	@Override
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		request.setAttribute("U", temporary_values.U);
		request.setAttribute("LB", temporary_values.LB);
		request.setAttribute("message", temporary_values.CheckAccessSuccess);
		//当获取过一次借书完成后 当借书状态重置为0
		if(temporary_values.CheckAccessSuccess==1){
			temporary_values.CheckAccessSuccess=0;
		}
		return "accessBook.jsp";
	}
	

}
