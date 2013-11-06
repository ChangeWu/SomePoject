package com.change.libs;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;

public class MediaRecorder extends Service {
	private android.media.MediaRecorder mMediaRecorder;
	private String testFile;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
	
	
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			mMediaRecorder.prepare();
			mMediaRecorder.start();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mMediaRecorder.release();
			}
		}, 1000);
		return super.onStartCommand(intent, flags, startId);
	}
}
