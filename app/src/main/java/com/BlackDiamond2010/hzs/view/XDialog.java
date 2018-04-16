package com.BlackDiamond2010.hzs.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;


public class XDialog extends Dialog {

    private TextView mLoadingText;
    private ImageView mLoadingImage;
    private ImageView mLoadingImageMor;
    private RotateAnimation mAnimation;
    private LinearLayout mLoadingContainer;
    private LinearLayout mLoadingContainerMor;
    private String mStr;

    public XDialog(Context context, int theme) {
        super(context, theme);
    }

    public XDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_dialog_loading);
        mLoadingImage = (ImageView) findViewById(R.id.dialog_loading_iv);
        mLoadingImageMor = (ImageView) findViewById(R.id.dialog_loading_iv_mor);
        mLoadingText = (TextView) findViewById(R.id.loading_texts);
        mLoadingContainer = (LinearLayout) findViewById(R.id.loading_container);
        mLoadingContainerMor = (LinearLayout) findViewById(R.id.loading_container_mor);

        mAnimation = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setRepeatCount(RotateAnimation.INFINITE);
        mAnimation.setInterpolator(new LinearInterpolator());//不停顿;
        mAnimation.setDuration(1500);// 设置动画持续时间
        mLoadingImage.setAnimation(mAnimation);
        mLoadingImageMor.setAnimation(mAnimation);
        mAnimation.start();
    }

    public void start(){
        if(mAnimation != null) {
            mLoadingImage.setAnimation(mAnimation);
            mLoadingImageMor.setAnimation(mAnimation);
            mAnimation.start();
        }

        try {
            show();
        }catch (Exception e){

        }
        if(!StringUtil.isEmpty(mStr)){
            mLoadingContainer.setVisibility(View.GONE);
            mLoadingContainerMor.setVisibility(View.VISIBLE);
            mLoadingText.setText(mStr);
        }else{
            mLoadingContainer.setVisibility(View.VISIBLE);
            mLoadingContainerMor.setVisibility(View.GONE);
        }
    }

    public void setLoadingText(String text){
        mStr = text;
    }
}
