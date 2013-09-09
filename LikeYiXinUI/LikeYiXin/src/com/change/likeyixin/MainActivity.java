package com.change.likeyixin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.change.likeyixin.MenuFragment.OnMeunSelectListener;
import com.change.likeyixin.MyTemplateHeader.onClickHeaderListener;
import com.change.likeyixin.app.BaseActivity;

/**
 * 主界面
 * 
 * @author Change
 * 
 */
public class MainActivity extends BaseActivity {
	private Fragment yixinF, pengyouF, settingF;
	private  MyTemplateHeader header;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		header = (MyTemplateHeader)findViewById(R.id.header);
		header.setClickHeaderListener(new onClickHeaderListener() {
			
			@Override
			public void OnClickRightButton() {
				slidingMenu.showMenu();
			}
			
			@Override
			public void OnClickLeftButton() {
				slidingMenu.showSecondaryMenu();
			}
		});
		initMenuFragment();
		FragmentTransaction tr = this.getSupportFragmentManager().beginTransaction();
		RightFragment rightFrag = new RightFragment();
		slidingMenu.setSecondaryMenu(R.layout.right_frame);
		slidingMenu.setBehindOffset(getWindowManager().getDefaultDisplay()
				.getWidth() / 2);
		slidingMenu.setSecondBehindOffset(getWindowManager()
				.getDefaultDisplay().getWidth() / 6); // 设置第二菜单偏移量，slidingmenu源码仅因此做了修改，增加了此内容
		tr.replace(R.id.right_frame, rightFrag);
		tr.replace(R.id.content, yixinF);
		tr.commit();
		setMenuAction();
	}

	private void initMenuFragment() {
		yixinF = new YiXinFragment();
		pengyouF = new PengyouFragment();
		settingF = new SettingFragment();
	}

	private void setMenuAction() {
		mFrag.setOnMenuSelectListener(new OnMeunSelectListener() {

			@Override
			public void onSelected(int position) {
				FragmentTransaction tr = MainActivity.this.getSupportFragmentManager().beginTransaction();
				switch (position) {
				case MenuFragment.FLAG_YIXIN:
					slidingMenu.showContent(true);
					tr.replace(R.id.content, yixinF);
					break;
				case MenuFragment.FLAG_PENGYOU:
					slidingMenu.showContent(true);
					tr.replace(R.id.content, pengyouF);
					break;
				case MenuFragment.FLAG_SETTING:
					slidingMenu.showContent(true);
					tr.replace(R.id.content, settingF);
					break;
				default:
					break;
				}
				tr.commit();
			}
		});
	}

}
