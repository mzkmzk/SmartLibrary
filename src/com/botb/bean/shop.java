package com.botb.bean;

import java.util.List;

public class shop {

	private int sid;
	private int sNow;
	private String sname;
	private String address;
	private String passwd;
	private String sphone;
	private String longtude;//经度
	private String dimensionality;//纬度
	private int type; //类型 1餐厅 2 银行 3医院
	private String info ; //简介
	private String selectInfo ; //医院可选择的科目
	private List<String> selectInfo_2;//商店科目
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getSNow() {
		return sNow;
	}
	public void setSNow(int now) {
		sNow = now;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getSphone() {
		return sphone;
	}
	public void setSphone(String sphone) {
		this.sphone = sphone;
	}
	public String getLongtude() {
		return longtude;
	}
	public void setLongtude(String longtude) {
		this.longtude = longtude;
	}
	public String getDimensionality() {
		return dimensionality;
	}
	public void setDimensionality(String dimensionality) {
		this.dimensionality = dimensionality;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getSelectInfo() {
		return selectInfo;
	}
	public void setSelectInfo(String selectInfo) {
		this.selectInfo = selectInfo;
	}
	public List<String> getSelectInfo_2() {
		return selectInfo_2;
	}
	public void setSelectInfo_2(List<String> selectInfo_2) {
		this.selectInfo_2 = selectInfo_2;
	}
	
}
