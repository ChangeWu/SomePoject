package com.change.libs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.change.libs.util.Timer;

public class MainActivity extends Activity implements OnClickListener{
	private Timer timer1,timer2 = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	timer1 = new Timer((TextView)findViewById(R.id.test),Timer.SECONDS,1,"HH:mm:ss");
	timer2 = new Timer((TextView)findViewById(R.id.test2));
	findViewById(R.id.start).setOnClickListener(this);
	findViewById(R.id.stop).setOnClickListener(this);
	findViewById(R.id.reset).setOnClickListener(this);
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
		default:
			break;
		}
	}
}
