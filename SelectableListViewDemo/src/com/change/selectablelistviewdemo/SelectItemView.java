package com.change.selectablelistviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.selectablelistviewdemo.R;

public class SelectItemView extends LinearLayout implements Checkable {
	CheckBox cbx;
	TextView name;
	public SelectItemView(Context context){
		super(context);
		inflate(getContext(), R.layout.item_select, this);
		cbx = (CheckBox)findViewById(R.id.cbx);
		name = (TextView)findViewById(R.id.name);
	}
	
	@Override
	public boolean isChecked() {
		return cbx.isChecked();
	}

	@Override
	public void setChecked(boolean arg0) {
		cbx.setChecked(arg0);
	}

	@Override
	public void toggle() {
		cbx.toggle();
	}
	
	public void setName(String name){
		this.name.setText(name);
	}

}
