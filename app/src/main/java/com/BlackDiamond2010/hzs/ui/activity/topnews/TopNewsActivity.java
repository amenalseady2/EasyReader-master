package com.BlackDiamond2010.hzs.ui.activity.topnews;

import android.text.TextUtils;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.bean.topnews.NewsDetailBean;
import com.BlackDiamond2010.hzs.bean.topnews.NewsListBean;
import com.BlackDiamond2010.hzs.injector.component.activity.DaggerTopNewsComponent;
import com.BlackDiamond2010.hzs.injector.module.http.TopNewsHttpModule;
import com.BlackDiamond2010.hzs.presenter.TopNewsPresenter;
import com.BlackDiamond2010.hzs.presenter.impl.TopNewsPresenterImpl;
import com.BlackDiamond2010.hzs.ui.activity.base.ZhihuDetailBaseActivity;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;

/**
 * Created by quantan.liu on 2017/3/28.
 * 这个页面比较特殊没有用到之前的网络请求
 * 所以大家可以忽略这个页面不看。
 */

public class TopNewsActivity extends ZhihuDetailBaseActivity<TopNewsPresenterImpl> implements TopNewsPresenter.ViewActivity {

    @BindView(R.id.ht_news_content)
    HtmlTextView htNewsContent;
    private String url;

    @Override
    protected void loadData() {
        String id = getIntent().getStringExtra("id");//获取新闻的id
        //获取imgUrl
        url = getIntent().getStringExtra("url");
        mPresenter.getDescrible(id);
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_top_news;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initInject() {
        DaggerTopNewsComponent.builder()
                .topNewsHttpModule(new TopNewsHttpModule())
                .build().injectTopNews(this);
    }


    @Override
    public void refreshView(NewsListBean data) {

    }

    @Override
    public void refreshActivityView(NewsDetailBean newsDetailBean) {
        setState(AppConstants.STATE_SUCCESS);
        if (TextUtils.isEmpty(newsDetailBean.getBody())){
            return;
        }
        htNewsContent.setHtmlFromString(newsDetailBean.getBody(), new HtmlTextView.LocalImageGetter());
        setToolBar(toolbarZhihuDetail, newsDetailBean.getTitle());
        detailBarCopyright.setText(newsDetailBean.getSource());
        GlideUtils.load(this, url, detailBarImage);
    }
}
