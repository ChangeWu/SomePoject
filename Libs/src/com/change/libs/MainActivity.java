package com.change.libs;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.change.libs.util.Timer;

public class MainActivity extends Activity implements OnClickListener,Callback{
	private Timer timer1,timer2 = null;
	
	private android.media.MediaRecorder mMediaRecorder;
	private String testFile;
	private SurfaceView sv;
	private SurfaceHolder sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	timer1 = new Timer((TextView)findViewById(R.id.test),Timer.SECONDS,1,"HH:mm:ss");
	timer2 = new Timer((TextView)findViewById(R.id.test2));
	findViewById(R.id.start).setOnClickListener(this);
	findViewById(R.id.stop).setOnClickListener(this);
	findViewById(R.id.reset).setOnClickListener(this);
	findViewById(R.id.record).setOnClickListener(this);
	sv = (SurfaceView)findViewById(R.id.suface);
	sh = sv.getHolder();
	testFile = Environment.getExternalStorageDirectory()+"/test.3gp";
	mMediaRecorder = new android.media.MediaRecorder();
//	mMediaRecorder.setCamera(mCamera);
	mMediaRecorder.setVideoSource(android.media.MediaRecorder.VideoSource.CAMERA);
	mMediaRecorder.setOutputFormat(android.media.MediaRecorder.OutputFormat.THREE_GPP);
	mMediaRecorder.setMaxDuration(1000);
	//mMediaRecorder.setMaxFileSize(Integer.MAX_VALUE);
	mMediaRecorder.setVideoEncoder(android.media.MediaRecorder.VideoEncoder.H264);
	mMediaRecorder.setPreviewDisplay(sh.getSurface());

	mMediaRecorder.setVideoFrameRate(20);
	mMediaRecorder.setVideoEncodingBitRate(500*1000);
	mMediaRecorder.setOutputFile(testFile);
	
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.start:
			timer1.start();
			timer2.start();
			break;
		case R.id.stop:
			timer1.stop();
			timer2.stop();
			break;
		case R.id.reset:
			timer1.reset();
			timer2.reset();
			break;
		case R.id.record:
//			Intent i = new Intent(this,MediaRecorder.class);
//			startService(i);
			
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
					mMediaRecorder.reset();
					testFile = Environment.getExternalStorageDirectory()+"/test_output.3gp";
//					mMediaRecorder = new android.media.MediaRecorder();
//					mMediaRecorder.setCamera(mCamera);
					mMediaRecorder.setVideoSource(android.media.MediaRecorder.VideoSource.CAMERA);
					mMediaRecorder.setOutputFormat(android.media.MediaRecorder.OutputFormat.THREE_GPP);
					//mMediaRecorder.setMaxFileSize(Integer.MAX_VALUE);
					mMediaRecorder.setVideoEncoder(android.media.MediaRecorder.VideoEncoder.H264);
					mMediaRecorder.setPreviewDisplay(sh.getSurface());

					mMediaRecorder.setVideoFrameRate(20);
					mMediaRecorder.setVideoEncodingBitRate(500*1000);
					mMediaRecorder.setOutputFile(testFile);
					
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
				}
			}, 2000);
			break;
		default:
			break;
		}
	}
	
	 @Override 

	    public void surfaceChanged(SurfaceHolder holder, int format, int width, 

	            int height) { 

	        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder 

	        sh = holder; 

	    } 

	 

	    @Override 

	    public void surfaceCreated(SurfaceHolder holder) { 

	        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder 

	        sh = holder; 

	    } 

	 

	    @Override 

	    public void surfaceDestroyed(SurfaceHolder holder) { 

	        // surfaceDestroyed的时候同时对象设置为null 

	        sv = null; 

	        sh = null; 

	        mMediaRecorder = null; 

	    } 
	    
	    @Override
	    protected void onDestroy() {
	    	// TODO Auto-generated method stub
	    	super.onDestroy();
	    	mMediaRecorder.release();
	    }

}
