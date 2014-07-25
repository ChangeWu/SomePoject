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
 * �����ӳټ��ط�ʽDemo android�Ƽ�����viewStub��ʽ��
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
			normalInflate.setText("����ͨ����inflate�������ص�view");
			normalInflate.setBackgroundColor(Color.parseColor("#00ffff"));
			break;
		case R.id.btn_by_viewstub:
			mStub = (ViewStub)findViewById(R.id.viewstub);
			if(null!=mStub)//mStubִ����һ��inflate()����֮��ᱻ�ÿգ����Դ˴��п�
			delayByViewStub = (TextView)mStub.inflate();
//			mStub.setVisibility(View.VISIBLE);//������������inflate��view.
			break;

		default:
			break;
		}
	}

}
