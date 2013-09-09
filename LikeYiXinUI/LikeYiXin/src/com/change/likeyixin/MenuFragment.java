package com.change.likeyixin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 左边菜单
 * @author Change
 *
 */
public class MenuFragment extends Fragment
{
	private ImageView head;
	private RadioGroup menu_radio;
	private OnMeunSelectListener lis;
	public static final int FLAG_YIXIN = 0;
	public static final int FLAG_PENGYOU = 1;
	public static final int FLAG_SETTING = 2;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	View v = inflater.inflate(R.layout.fragment_menu, null);
    	head = (ImageView)v.findViewById(R.id.head);
    	menu_radio = (RadioGroup)v.findViewById(R.id.menu_radio);
    	menu_radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.yixin:
					lis.onSelected(FLAG_YIXIN);
					break;
				case R.id.penyou:
					lis.onSelected(FLAG_PENGYOU);
					break;
				case R.id.setting:
					lis.onSelected(FLAG_SETTING);
					break;
				default:
					break;
				}
			}
		});
    	head.setImageBitmap(getHeadBitmap());
    	return v;
    }
    
    
    /**
     * 获得圆形头像
     * @return
     */
    private Bitmap getHeadBitmap(){
    	Bitmap head = BitmapFactory.decodeResource(getResources(), R.drawable.head_default_local);
    	return new BitmapHalper(getActivity()).getWhiteCicleBitmap(head);
    }

    /**
     * 选中菜单监听
     * @author Change
     *
     */
    public interface OnMeunSelectListener{
    	/**
    	 * @param position 菜单项编号，依次从0开始
    	 */
    	public void onSelected(int position);
    }
    
    public void setOnMenuSelectListener(OnMeunSelectListener lis){
    	this.lis = lis;
    }
    
}
