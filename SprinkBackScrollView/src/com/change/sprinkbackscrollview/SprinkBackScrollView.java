package com.change.sprinkbackscrollview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;



/** 
* @ClassName: SprinkBackScrollView 
* @Description:回弹scrollview
* @author ycf_Change 
* @date 2014年9月17日 下午3:39:55 
* @version from 1.0 
*/

public class SprinkBackScrollView extends ScrollView{
	private View inner;//内部控件

	private float preY;
	private float downY;
	private float disY;
	private float demp = 0.3f; //阻尼系数
	private Rect normal = new Rect();//记录位置
	
	public SprinkBackScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		if (getChildCount() != 0) {
			inner = getChildAt(0);
		}
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (inner == null) {
			return super.onTouchEvent(ev);
		} else {
			commOnTouchEvent(ev);
		}
		return super.onTouchEvent(ev);
	}
	
	

	/** 
	* @Title: commOnTouchEvent 
	* @Description:  触摸事件处理
	* @param @param ev   
	* @return void    
	* @throws 
	*/
	public void commOnTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			downY = ev.getY(); 
			preY = downY;
			break;
		case MotionEvent.ACTION_UP:
			float upY = ev.getY();
			disY = Math.abs(upY-downY);
			if (isNeedAnimation()) {
				animationBack();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			float nowY = ev.getY();
			int distance = (int) ((preY - nowY));
			preY = nowY;
			if (isNeedMove(distance)) {
				if (normal.isEmpty()) {
					// 保存正常的布局位置
					normal.set(inner.getLeft(), inner.getTop(),
							inner.getRight(), inner.getBottom());

				}
				// 移动布局
				inner.layout(inner.getLeft(), inner.getTop() - (int)(distance*demp),
						inner.getRight(), inner.getBottom() - (int)(distance*demp));
			}
			break;

		default:
			break;
		}
	}


	/** 
	* @Title: animation 
	* @Description: 回弹动画 
	* @param    
	* @return void    
	* @throws 
	*/
	public void animationBack() {
		TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
				normal.top);
		ta.setDuration(200);
		inner.startAnimation(ta);
		// 设置回到正常的布局位置
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);
		normal.setEmpty();
	}
	
	/** 
	* @Title: animationTo 
	* @Description:越界动画
	* @param    
	* @return void    
	* @throws 
	*/
	public void animationTo(){
		
	}

	/** 
	* @Title: isNeedAnimation 
	* @Description:  是否开启动画
	* @param @return   
	* @return boolean    
	* @throws 
	*/
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	/** 
	* @Title: isNeedMove 
	* @Description:  判断是否到底部跟顶部
	* @param @param distance
	* @param @return   
	* @return boolean    
	* @throws 
	*/
	public boolean isNeedMove(int distance) {

		int offset = inner.getMeasuredHeight() - getHeight();
		int scrollY = getScrollY();
		if ((scrollY == 0&&distance<0) || (scrollY == offset&&distance>0)) {
			return true;
		}
		return false;
	}
	
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
	}

}
