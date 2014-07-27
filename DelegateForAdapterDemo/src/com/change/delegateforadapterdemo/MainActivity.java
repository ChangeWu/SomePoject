package com.change.delegateforadapterdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.change.delegateforadapterdemo.DelegateForAdapter.DataControlDelegate;

/**
 * ʵ��adapter�е�����ί�нӿڣ������е���Ϊ��activity�����ưɡ�
 * @author Change
 *
 */
public class MainActivity extends Activity implements DataControlDelegate{
	private List<String> names = new ArrayList<String>();
	private DelegateForAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initDatas();
		ListView lv = (ListView)findViewById(R.id.lv);
		adapter = new DelegateForAdapter(this, names);
		lv.setAdapter(adapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		adapter.setDataControlDelegate(this);//��ί�ж���
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		adapter.setDataControlDelegate(null);//ȥ����ί�ж���
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void initDatas(){
		for(int i=0;i<10;i++){
			names.add("name"+i);
		}
	}

	@Override
	public void removeItem(String name) {
		names.remove(name);
		adapter.notifyDataSetChanged();
		Toast.makeText(this, "��ɾ��"+name, Toast.LENGTH_SHORT).show();
	}

}
