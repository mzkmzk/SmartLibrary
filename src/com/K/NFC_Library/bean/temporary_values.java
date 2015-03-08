package com.K.NFC_Library.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类 为系统全局变量 主要作为系统和前台界面交互时的作用
 * @author maizhikun
 *
 */
public class temporary_values {

	
	public static String NFCID_NOW;
	/**
	 * 借书者
	 */
	public static user U = null;
	
	/**
	 * 所借书的集合
	 */
	public static List<book> LB =new ArrayList<book>();
	
	/**
	 * 当此值为1时 表示 借书已经完成.
	 */
	public static int CheckAccessSuccess  ;

	/**
	 * 记录 当前还书的记录
	 */
	public static List<user> LU_return = new ArrayList<user>();
	
	
	
}
