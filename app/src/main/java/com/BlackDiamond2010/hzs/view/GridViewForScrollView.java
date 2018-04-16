package com.BlackDiamond2010.hzs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * scrollview ��Ƕ�׵�grid ��ֹgridview ֻ��ʾһ��item
 * 
 * @author wyb
 * */

public class GridViewForScrollView extends GridView {
	public GridViewForScrollView(Context context) {
		super(context);
	}

	public GridViewForScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GridViewForScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}
}
