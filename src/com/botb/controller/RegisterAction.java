package com.botb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.botb.bean.shop;
import com.botb.bean.user;
import com.botb.dao.RegisterDao;
import com.opensymphony.xwork2.Action;

public class RegisterAction implements Action {

	private int id;
	private String msg ;
	private RegisterDao  dao =new RegisterDao();
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		if(request.getParameter("address").equals("")){
			user u =new user();
			System.out.println(request.getParameter("phone"));
			System.out.println(request.getParameter("passwd"));
			u.setUphone(request.getParameter("phone"));
			u.setPasswd(request.getParameter("passwd"));
			int flag = dao.addUser(u);
			System.out.println(flag);
			if(flag==0){
				msg ="手机已经注册过,请选择别的手机号";
			}else if (flag == 1 ){
				msg ="success";
			}
		}else{
			shop s =new shop();
			s.setAddress(request.getParameter("address"));
			s.setPasswd(request.getParameter("passwd"));
			s.setSname(request.getParameter("user"));
			s.setSphone(request.getParameter("phone"));
			String type =request.getParameter("type");
			int typeInt =0;
			if(type.equals("热门餐厅")){
				typeInt=1;
			}else if(type.equals("医院")){
				typeInt=2;
			}else if(type.equals("银行")){
				typeInt=3;
			}
			s.setType(typeInt);
			s.setInfo(request.getParameter("info"));
			System.out.println("action : "+request.getParameter("info"));
			int flag = dao.addShop(s);
			if(flag==0){
				msg ="此企业已注册";
			}else if (flag == 1 ){
				msg ="success";
			}
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
