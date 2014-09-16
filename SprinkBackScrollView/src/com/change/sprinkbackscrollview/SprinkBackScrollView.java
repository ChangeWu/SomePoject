package com.change.sprinkbackscrollview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;


/**
 * 回弹scrollview
 * @author Change
 *
 */
public class SprinkBackScrollView extends ScrollView {
	private View inner;//内部控件

	private float y;
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
	
	
	
	

	public void commOnTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			y = ev.getY();
			break;
		case MotionEvent.ACTION_UP:
			if (isNeedAnimation()) {
				animation();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			final float preY = y;
			float nowY = ev.getY();
			int distance = (int) ((preY - nowY));
			y = nowY;
			// 当滚动到最上或者最下时就不会再滚动，这时移动布局
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

	// 开启动画移动

	public void animation() {
		// 开启移动动画
		TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
				normal.top);
		ta.setDuration(200);
		inner.startAnimation(ta);
		// 设置回到正常的布局位置
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);

		normal.setEmpty();

	}

	// 是否需要开启动画
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	// 是否需要移动布局
	public boolean isNeedMove(int distance) {

		int offset = inner.getMeasuredHeight() - getHeight();
		int scrollY = getScrollY();
		if ((scrollY == 0&&distance<0) || (scrollY == offset&&distance>0)) {
			return true;
		}
		return false;
	}
}
