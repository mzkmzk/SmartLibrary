package com.K.NFC_Library.action;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.K.NFC_Library.bean.book;
import com.K.NFC_Library.dao.Book_Dao;
import com.opensymphony.xwork2.Action;

/**
 * NFC刷卡添加用户
 * @author maizhikun
 *
 */
public class NFC_InsertBook implements Action{
	
	@Override
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		Book_Dao bd = new Book_Dao();
		book b =new book();
		b.setB_NO(request.getParameter("NFC_ID"));
		if(!b.getB_NO().equals("")){
		b.setB_address(request.getParameter("B_address"));
		b.setB_name(request.getParameter("B_name"));
		b.setB_press(request.getParameter("B_press"));
		request.getSession().setAttribute("message",(bd.NFC_InsertBook(b)+"").equals("1") ? "23" :"-1");
		}else{
			request.getSession().setAttribute("message", "-1");
		}
		System.out.println("添加书籍 返回的message " +request.getSession().getAttribute("message"));
		return "Loding_NFC.jsp";
	}
	

}
