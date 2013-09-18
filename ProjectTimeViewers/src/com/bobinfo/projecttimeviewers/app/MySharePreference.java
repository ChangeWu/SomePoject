package com.bobinfo.projecttimeviewers.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 保存配置文件
 * @author Change
 *
 */
public class MySharePreference{
	private static SharedPreferences preferences = null;
	private static SharedPreferences.Editor  editor = null;
	
	public MySharePreference(Context context){
		preferences = context.getSharedPreferences("System",Context.MODE_PRIVATE);
		editor = preferences.edit();
	}
	//保存xxx
	public static void saveXxx(String xxx){
		editor.putString(xxx, xxx);
		editor.commit();
	}
	//获取xxx
	public static String getXxx(){
		return preferences.getString("xxx", null);
	}
}
