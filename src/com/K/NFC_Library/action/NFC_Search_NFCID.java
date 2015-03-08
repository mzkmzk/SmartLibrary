package com.K.NFC_Library.action;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.K.NFC_Library.bean.book;
import com.K.NFC_Library.bean.temporary_values;
import com.K.NFC_Library.dao.Book_Dao;
import com.opensymphony.xwork2.Action;

/**
 * NFC刷卡添加用户
 * @author maizhikun
 *
 */
public class NFC_Search_NFCID implements Action{
	private int id;
	private String NFCID;
	
	@Override
	public String execute() throws Exception {
		NFCID=temporary_values.NFCID_NOW;
		return SUCCESS;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNFCID() {
		return NFCID;
	}

	public void setNFCID(String nFCID) {
		NFCID = nFCID;
	}
	

}
