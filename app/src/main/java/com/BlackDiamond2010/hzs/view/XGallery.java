package com.BlackDiamond2010.hzs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.SpinnerAdapter;

import com.BlackDiamond2010.hzs.R;


@SuppressWarnings("deprecation")
public class XGallery extends Gallery {
	ViewPager mPager;
	private int mDataSourceCount;
	private long mDelay;
	private boolean isAutoScroll = false;
	private Runnable mAction;
	private MarginLayoutParams params;
	private FrameLayout parentView;
	private int mSpacing;

	public XGallery(Context context) {
		this(context, null);
	}

	public XGallery(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public XGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XGallery);
		isAutoScroll = a.getBoolean(R.styleable.XGallery_autoScroll, true);
		mDelay = a.getInt(R.styleable.XGallery_delay, 3000);
		/*if (isAutoScroll) {
			openAutoScroll(mDelay);
		}*/
		
		isAutoScroll = true;
		a.recycle();
	}
	
	@Override
	public void setSpacing(int spacing) {
		super.setSpacing(spacing);
		mSpacing = spacing;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		int kEvent = 0;
		if (isScrollingLeft(e1, e2)) {
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;// 向左滑动
		} else {
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;// 向右滑动
		}

		onKeyDown(kEvent, null);
		if (this.getSelectedItemPosition() == 0) {// 实现后退功能
			this.setSelection(mDataSourceCount * 1000 - 1);
		}
		return false;
	}
	
	@Override
	public void playSoundEffect(int soundConstant) {
		//super.playSoundEffect(soundConstant);
		// 去除声音
	}

	public void openAutoScroll() {
		openAutoScroll(mDelay == 0 ? 5000 : mDelay);
	}

	public void openAutoScroll(long delay) {
		postDelayed(mAction = new Runnable() {
			@Override
			public void run() {
				if (isAutoScroll) {
					openAutoScroll(mDelay);
					if(parentView != null){
						if (params == null) {
							if (parentView.getLayoutParams() instanceof MarginLayoutParams) {
								params = (MarginLayoutParams) parentView.getLayoutParams();
							}
						}
						if (params != null) {
							//LogHelper.i("XGallery", "parentView.getPaddingLeft()="+parentView.getPaddingLeft()+",parentView.getPaddingRight()="+parentView.getPaddingRight()+",params.leftMargin="+params.leftMargin+",params.rightMargin="+params.rightMargin);
							onScroll(null, null, parentView.getPaddingLeft()+ parentView.getPaddingRight() + getPaddingLeft() + getPaddingRight() + params.leftMargin + params.rightMargin + mSpacing + 1, 0);
						} else {
							onScroll(null, null, parentView.getPaddingLeft()+ parentView.getPaddingRight() + getPaddingLeft() + getPaddingRight() + mSpacing + 1, 0);
						}
					} else {
						onScroll(null, null, 0, 0);
					}
					
					
					onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
					onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
				}
			}
		}, mDelay = delay);
	}

	public void setAutoScroll(boolean isAutoScroll) {
		this.isAutoScroll = isAutoScroll;
		if (isAutoScroll) {
			if (mAction != null) {
				removeCallbacks(mAction);
			}
			openAutoScroll();
		}
	}

	public void setDelay(long period) {
		mDelay = period;
	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}
	
	@Override
	public void setAdapter(SpinnerAdapter adapter) {
		super.setAdapter(adapter);
		mDataSourceCount = adapter.getCount();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (mPager != null) {
			mPager.requestDisallowInterceptTouchEvent(true);
		}
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return super.onScroll(e1, e2, distanceX/* * 1.5f */, distanceY);
	}

	public ViewPager getPager() {
		return mPager;
	}

	public void setPager(ViewPager pager) {
		mPager = pager;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (mPager != null) {
			mPager.requestDisallowInterceptTouchEvent(true);
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mPager != null) {
			mPager.requestDisallowInterceptTouchEvent(true);
		}
		return super.onTouchEvent(event);
	}
	
	public void setRealParent(FrameLayout parentView){
		this.parentView = parentView;
	}
}
