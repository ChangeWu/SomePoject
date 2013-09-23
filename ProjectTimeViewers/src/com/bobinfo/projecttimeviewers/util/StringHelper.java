package com.bobinfo.projecttimeviewers.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 字符串相关帮助类
 * @author Change
 * @date 2013-9-18[下午2:42:44]
 */
public class StringHelper {
	/**
	 * 输入流转字符串
	 * @param in
	 * @return
	 */
	public static String InputStreamToString(InputStream in){
		StringBuffer sbf = new StringBuffer();
		BufferedReader brd = new BufferedReader(new InputStreamReader(in));
		String temp = "";
		try {
			while((temp = brd.readLine())!=null){
				sbf.append(temp);
			}
			return sbf.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
