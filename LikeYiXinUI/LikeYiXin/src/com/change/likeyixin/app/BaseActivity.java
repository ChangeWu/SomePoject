package com.change.likeyixin.app;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.change.likeyixin.MenuFragment;
import com.change.likeyixin.MenuFragment.OnMeunSelectListener;
import com.change.likeyixin.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class BaseActivity extends SlidingFragmentActivity 
{
    protected MenuFragment  mFrag;
    protected SlidingMenu slidingMenu;
   
    @Override
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.menu_frame);
        FragmentTransaction tr = this.getSupportFragmentManager().beginTransaction();
        mFrag = new MenuFragment();
        tr.replace(R.id.menu_frame, mFrag); 
        tr.commit();
        slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.setShadowWidth(getWindowManager().getDefaultDisplay().getWidth() / 40);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setSecondaryShadowDrawable(R.drawable.right_shadow);
        slidingMenu.setFadeEnabled(true);
        slidingMenu.setFadeDegree(0.4f);
        slidingMenu.setBehindScrollScale(0);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
    }
    
  
}
