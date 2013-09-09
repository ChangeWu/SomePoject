package com.change.likeyixin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class YiXinFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView yixin = new TextView(getActivity());
		yixin.setGravity(Gravity.CENTER);
		yixin.setTextColor(Color.BLACK);
		yixin.setText("YiXinFragment");
		return yixin;
	}
	
}
