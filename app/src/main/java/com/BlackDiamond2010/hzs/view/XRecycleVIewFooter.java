package com.BlackDiamond2010.hzs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;

/**
 * 项目名称：Aicaomei_MVVMFrame_Retrofit
 * 类描述：
 * 创建人：yq
 * 创建时间：2016/8/6 15:21
 * 修改人：yq
 * 修改时间：2016/8/6 15:21
 * 修改备注：
 */
public class XRecycleVIewFooter extends LinearLayout{
    private ImageView recycleview_foot_loadmore;
    public TextView tv_y_recycleview_foot_loadmore_status;
    /**
     * 加载中
     */
    public final static int STATE_LOADING = 0;
    /**
     * 加载完成
     */
    public final static int STATE_COMPLETE = 1;
    /**
     * 正常状态
     */
    public final static int STATE_NOMORE = 2;
    /**
     * 刷新状态
     */
    public final static int STATE_REFRESH = 3;

    public final static int STATE_GONE = 4;

    private RotateAnimation mAnimation;

    public XRecycleVIewFooter(Context context) {
        super(context);
        initView(context);
    }

    public XRecycleVIewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * 初始化
     */
    private void initView(Context context) {
        //设置内部内容居中
        setGravity(Gravity.CENTER);
        //设置宽高
        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //底部布局
        View mContentView = View.inflate(context, R.layout.foot_recycleview_loadmore, null);
        recycleview_foot_loadmore = (ImageView) mContentView.findViewById(R.id.recycleview_foot_loadmore);
        mAnimation = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setRepeatCount(RotateAnimation.INFINITE);
        mAnimation.setInterpolator(new LinearInterpolator());;
        mAnimation.setDuration(1500);
        mAnimation.setRepeatCount(-1);

        tv_y_recycleview_foot_loadmore_status = (TextView) mContentView.findViewById(R.id.tv_y_recycleview_foot_loadmore_status);
        addView(mContentView);
    }

    /**
     * 设置当前状态
     *
     * @param state
     */
    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                recycleview_foot_loadmore.setVisibility(View.VISIBLE);
                recycleview_foot_loadmore.setAnimation(mAnimation);
                mAnimation.start();
                tv_y_recycleview_foot_loadmore_status.setText("正在加载");
                findViewById(R.id.recycleview_foot_line2).setVisibility(View.GONE);
                findViewById(R.id.recycleview_foot_line1).setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                tv_y_recycleview_foot_loadmore_status.setText("正在加载");
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                findViewById(R.id.recycleview_foot_line2).setVisibility(View.VISIBLE);
                findViewById(R.id.recycleview_foot_line1).setVisibility(View.VISIBLE);
                tv_y_recycleview_foot_loadmore_status.setText("还是往上看看吧");
                recycleview_foot_loadmore.clearAnimation();
                recycleview_foot_loadmore.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_REFRESH:
                tv_y_recycleview_foot_loadmore_status.setText("");
                recycleview_foot_loadmore.setVisibility(View.GONE);
                findViewById(R.id.recycleview_foot_line2).setVisibility(View.GONE);
                findViewById(R.id.recycleview_foot_line1).setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }

    }
}
