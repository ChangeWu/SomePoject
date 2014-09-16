package com.change.sprinkbackscrollview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;


/**
 * �ص�scrollview
 * @author Change
 *
 */
public class SprinkBackScrollView extends ScrollView {
	private View inner;//�ڲ��ؼ�

	private float y;
	private float demp = 0.3f; //����ϵ��
	private Rect normal = new Rect();//��¼λ��
	
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
			// �����������ϻ�������ʱ�Ͳ����ٹ�������ʱ�ƶ�����
			if (isNeedMove(distance)) {
				if (normal.isEmpty()) {
					// ���������Ĳ���λ��
					normal.set(inner.getLeft(), inner.getTop(),
							inner.getRight(), inner.getBottom());

				}
				// �ƶ�����
				inner.layout(inner.getLeft(), inner.getTop() - (int)(distance*demp),
						inner.getRight(), inner.getBottom() - (int)(distance*demp));
			}
			break;

		default:
			break;
		}
	}

	// ���������ƶ�

	public void animation() {
		// �����ƶ�����
		TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
				normal.top);
		ta.setDuration(200);
		inner.startAnimation(ta);
		// ���ûص������Ĳ���λ��
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);

		normal.setEmpty();

	}

	// �Ƿ���Ҫ��������
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	// �Ƿ���Ҫ�ƶ�����
	public boolean isNeedMove(int distance) {

		int offset = inner.getMeasuredHeight() - getHeight();
		int scrollY = getScrollY();
		if ((scrollY == 0&&distance<0) || (scrollY == offset&&distance>0)) {
			return true;
		}
		return false;
	}
}
