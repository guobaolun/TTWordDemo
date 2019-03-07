package com.english.storm.widget;

import android.view.MotionEvent;
import android.widget.GridView;

public class FollowGridView extends GridView {

	private boolean isOnClickble = true;

	public FollowGridView(android.content.Context context,
			android.util.AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置不滚动
	 */
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}


	public void setOnClickble(boolean isOnClickble) {
		this.isOnClickble = isOnClickble;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (isOnClickble) {
			return super.onTouchEvent(ev);
		}else {
			return false;
		}
	}


}
