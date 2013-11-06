package com.change.libs.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import android.os.Handler;
import android.widget.TextView;

public class Timer { 
	private int seconds = 57600*1000;
	public static final int SECONDS = 0;
	public static final int CURRENTTIME = 1;
	private java.util.Timer timer;
	private int type = CURRENTTIME;
	private int between = 1;
	private TextView tv = null;
	private SimpleDateFormat sdf = null;
	private String format = "yyyy-MM-dd HH:mm:ss";
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			tv.setText((String)msg.obj);
		};
	};
	
	
	public void start(){
		timer = new java.util.Timer();
		timer.purge();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				switch (type) {
				case SECONDS:
					handler.sendMessage(handler.obtainMessage(SECONDS, sdf.format(new Date(seconds))));
					seconds++;
					break;
				case CURRENTTIME:
					handler.sendMessage(handler.obtainMessage(CURRENTTIME, sdf.format(new Date())));
					break;
				default:
					break;
				}
			}}, 0, between);
	}
	
	public Timer(TextView tv,int type,int between,String format) {
		this.type = type;
		this.between = between;
		this.tv = tv;
		this.format = format;
		sdf = new SimpleDateFormat(format);
	}
	
	public Timer(TextView tv) {
		this.tv = tv;
		sdf = new SimpleDateFormat(format);
	}
	
	public void reset(){
		timer.cancel();
		switch (type) {
		case SECONDS:
			seconds = 57600*1000;
			tv.setText(sdf.format(new Date(seconds)));
			break;
		case CURRENTTIME:
			tv.setText(sdf.format(new Date()));
			break;
		default:
			break;
		}
	}
	
	public void stop(){
		timer.cancel();
	}
	
}
