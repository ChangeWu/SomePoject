package com.bobinfo.projecttimeviewers.app;

import java.util.Stack;

import android.app.Activity;

/**
 * activity管理类
 * @author Change
 *
 */
public class ActivityManager {
	private static Stack<Activity> activityStack = null;
	private static ActivityManager activityManager = null;
	
	private ActivityManager(){}
	
	public static ActivityManager initActivityManager(){
		if(activityManager==null){
			activityManager = new ActivityManager();
		}
		return activityManager;
	}
	
	
	//出栈
	public void popActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
		}
	}
	//结束
	public void endActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}
	//获得当前
	public Activity currentActivity() {
		Activity activity = null;
		if (!activityStack.empty())
			activity = activityStack.lastElement();
		return activity;
	}

	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	public void popAllActivityExceptOne(Class<Activity> cls) {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			popActivity(activity);
		}
	}
	
	public void finishAllActivityExceptOne(Class<Activity> cls) {
		while (!activityStack.empty()) {
			Activity activity = currentActivity();
			if (activity.getClass().equals(cls)) {
				popActivity(activity);
			} else {
				endActivity(activity);
			}
		}
	}
	
	public void finishAllActivity() {
		while (!activityStack.empty()) {
			Activity activity = currentActivity();
				endActivity(activity);
		}
	}
}
