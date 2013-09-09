package com.change.likeyixin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PengyouFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView pengyou = new TextView(getActivity());
		pengyou.setGravity(Gravity.CENTER);
		pengyou.setTextColor(Color.BLACK);
		pengyou.setText("PengyouFragment");
		return pengyou;
	}
}
