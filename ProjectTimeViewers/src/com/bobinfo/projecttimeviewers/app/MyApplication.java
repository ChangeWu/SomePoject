package com.bobinfo.projecttimeviewers.app;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.app.Application;

/**
 * 应用程序实体
 * @author Change
 *
 */
public class MyApplication extends Application {
	private ActivityManager activityManager = null;
    private MySharePreference mySharePreference = null;
    private final static Executor mMainExecutor = Executors.newFixedThreadPool(2);
    private final static Executor mSerialExecutor = Executors.newFixedThreadPool(1);
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		activityManager = ActivityManager.initActivityManager();
		mySharePreference = new MySharePreference(this);		
		super.onCreate();
	}

	public ActivityManager getActivityManager(){
		return activityManager;
	}
	
	public MySharePreference getSharePreference(){
		return mySharePreference;
	}
	
    public Executor getMainExecutor() {
    	return mMainExecutor;
    }
    
    public Executor getSerialExecutor() {
    	return mSerialExecutor;
    }
}
