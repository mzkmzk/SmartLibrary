package com.K.NFC_Library.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	/**
	 * 获取系统时间
	 * @return yyyy-MM-dd
	 */
	public   static String getSystemTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
}
