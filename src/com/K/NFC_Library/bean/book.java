package com.K.NFC_Library.bean;

public class book {

	private String B_NO;
	private String B_name;
	private String B_address;
	private String B_press;
	private String B_accessBookTime;
	private String B_returnBookTime;
	
	public String getB_NO() {
		return B_NO;
	}
	public void setB_NO(String b_NO) {
		B_NO = b_NO;
	}
	public String getB_name() {
		return B_name;
	}
	public void setB_name(String b_name) {
		B_name = b_name;
	}
	public String getB_address() {
		return B_address;
	}
	public void setB_address(String b_address) {
		B_address = b_address;
	}
	public String getB_press() {
		return B_press;
	}
	public void setB_press(String b_press) {
		B_press = b_press;
	}
	public book() {
		super();
	}
	public book(String b_NO) {
		super();
		B_NO = b_NO;
	}
	public String getB_accessBookTime() {
		return B_accessBookTime;
	}
	public void setB_accessBookTime(String b_accessBookTime) {
		B_accessBookTime = b_accessBookTime;
	}
	public String getB_returnBookTime() {
		return B_returnBookTime;
	}
	public void setB_returnBookTime(String b_returnBookTime) {
		B_returnBookTime = b_returnBookTime;
	}
	
	
}
