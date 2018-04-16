package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.content)
    TextView content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("关于");


    }
}
