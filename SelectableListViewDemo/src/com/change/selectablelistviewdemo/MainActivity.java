package com.change.selectablelistviewdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.selectablelistviewdemo.R;

public class MainActivity extends Activity {
	List<String> names = new ArrayList<String>();
	ListView lv;
	SelAdapter adapter = new SelAdapter();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView)findViewById(R.id.lv);
		initData();
		lv.setAdapter(adapter);
	}
	
	private void initData(){
		for(int i=0;i<10;i++){
			names.add("name"+i);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class SelAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return names.size();
		}

		@Override
		public Object getItem(int arg0) {
			return names.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			if(null==arg1){
				arg1 = new SelectItemView(MainActivity.this);
			}
			((SelectItemView)arg1).setName(names.get(arg0));
			return arg1;
		}
		
		
	}
		public void choice(View v){
			int pos = lv.getCheckedItemPosition();
			if(ListView.INVALID_POSITION!=pos){
				Toast.makeText(MainActivity.this, "current pos="+pos, Toast.LENGTH_SHORT).show();
			}
		}
}
