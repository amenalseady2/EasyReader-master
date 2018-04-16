package com.BlackDiamond2010.hzs.ui.activity.main;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.base.ToolbarBaseActivity;
import com.BlackDiamond2010.hzs.webview.WebViewActivity;

import butterknife.OnClick;

/**
 * Created by quantan.liu on 2017/4/1.
 */

public class AboutUsActivity extends ToolbarBaseActivity {

    @OnClick(R.id.cv_github)
    public void cvGithub() {
        WebViewActivity.loadUrl(this, "https://github.com/laotan7237/EasyReader", "加载中。。。");
    }

    @Override
    protected void initUI() {
        tvToolbarTitle.setText("关于易读");
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_about_us;
    }


}
