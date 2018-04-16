package com.BlackDiamond2010.hzs.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.GridView;


/**
 * 菜单自图片的显示
 */
public class XGridView extends GridView {

	private int mNumColumns = 3;
	private int mRequestedHorizontalSpacing;
	private int mVerticalSpacing;

	public XGridView(Context context) {
		super(context);
		setFocusable(false);
	}

	public XGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(false);
	}

	@Override
	public boolean isFocused() {
		return true;
	}

	/**
	 * 设置不滚动
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
		getLayoutParams().height = getMeasuredHeight();

	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	/**
	 * Set the amount of horizontal (x) spacing to place between each item in
	 * the grid.
	 * 
	 * @param horizontalSpacing
	 *            The amount of horizontal space between items, in pixels.
	 * @attr ref android.R.styleable#GridView_horizontalSpacing
	 */
	@Override
	public void setHorizontalSpacing(int horizontalSpacing) {
		if (horizontalSpacing != mRequestedHorizontalSpacing) {
			mRequestedHorizontalSpacing = horizontalSpacing;
		}
		super.setHorizontalSpacing(horizontalSpacing);
	}

	public int getMyHorizontalSpacing() {
		return mRequestedHorizontalSpacing;
	}

	@Override
	public void setVerticalSpacing(int verticalSpacing) {
		if (verticalSpacing != mVerticalSpacing) {
			mVerticalSpacing = verticalSpacing;

		}
		super.setVerticalSpacing(verticalSpacing);
	}

	public int getMyVerticalSpacing() {
		return mVerticalSpacing;
	}

	@Override
	public void setNumColumns(int numColumns) {
		this.mNumColumns = numColumns;
		super.setNumColumns(numColumns);
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		int width = drawable.getIntrinsicWidth();
		width = width > 0 ? width : 1;
		int height = drawable.getIntrinsicHeight();
		height = height > 0 ? height : 1;

		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

}