package com.K.NFC_Library.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.K.NFC_Library.bean.book;
import com.K.NFC_Library.dao.User_Dao;
import com.opensymphony.xwork2.Action;

/**
 * 此类 给android返回借书记录
 * @author maizhikun
 *
 */
public class AndroidVisitUser_Book implements Action{
	private int id;
	private List<book> lb =new ArrayList<book>();
	
	@Override
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		User_Dao ud =new User_Dao();
		String U_NO =  request.getParameter("U_NO");
		String checkReturn = request.getParameter("checkReturn");
		System.out.println("查询借书记录的 编号是 "+U_NO);
		lb=ud.AndroidVisitUser_Book(U_NO,Integer.parseInt(checkReturn));
		return SUCCESS;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<book> getLb() {
		return lb;
	}

	public void setLb(List<book> lb) {
		this.lb = lb;
	}
	

}
