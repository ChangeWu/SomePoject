package com.change.delayloaddemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 两种延迟加载方式Demo android推荐采用viewStub方式。
 * 
 * @author Change
 * 
 */
public class MainActivity extends Activity implements OnClickListener {
	private LinearLayout main;
	private TextView normalInflate, delayByViewStub;
	private ViewStub mStub;
	private Button byInflate, byViewStub;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
	}
	
	private void initViews(){
		main = (LinearLayout) findViewById(R.id.main);
		byInflate = (Button)findViewById(R.id.btn_by_inflate);
		byViewStub = (Button)findViewById(R.id.btn_by_viewstub);
		
		byInflate.setOnClickListener(this);
		byViewStub.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_by_inflate:
			normalInflate = (TextView) getLayoutInflater().inflate(
					R.layout.delay_by_viewstub, null);
			main.addView(normalInflate);
			normalInflate.setText("我是通常的inflate方法加载的view");
			normalInflate.setBackgroundColor(Color.parseColor("#00ffff"));
			break;
		case R.id.btn_by_viewstub:
			mStub = (ViewStub)findViewById(R.id.viewstub);
			if(null!=mStub)//mStub执行了一次inflate()方法之后会被置空，所以此处判空
			delayByViewStub = (TextView)mStub.inflate();
//			mStub.setVisibility(View.VISIBLE);//或者这样可以inflate出view.
			break;

		default:
			break;
		}
	}

}
