package com.BlackDiamond2010.hzs.presenter.impl;

import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.bean.topnews.NewsDetailBean;
import com.BlackDiamond2010.hzs.bean.topnews.NewsListBean;
import com.BlackDiamond2010.hzs.http.Stateful;
import com.BlackDiamond2010.hzs.http.service.TopNewsService;
import com.BlackDiamond2010.hzs.http.utils.Callback;
import com.BlackDiamond2010.hzs.presenter.BasePresenter;
import com.BlackDiamond2010.hzs.presenter.TopNewsPresenter;
import com.BlackDiamond2010.hzs.utils.NewsJsonUtils;
import com.BlackDiamond2010.hzs.utils.OkHttpUtils;

import javax.inject.Inject;

/**
 * Created by quantan.liu on 2017/3/27.
 */

public class TopNewsPresenterImpl  extends BasePresenter<TopNewsPresenter.View> implements TopNewsPresenter.Presenter {
    private TopNewsService mTopNewsService;

    @Inject
    public TopNewsPresenterImpl(TopNewsService mTopNewsService) {
        this.mTopNewsService = mTopNewsService;
    }


    @Override
    public void fetchTopNewsList(int id) {
        invoke(mTopNewsService.fetchNews(id),new Callback<NewsListBean>(){
            @Override
            public void onResponse(NewsListBean data) {
                checkState(data.getNewsList());
                mView.refreshView(data);
            }
        });
    }

    public void getDescrible(final String docid) {
        String url = getDetailUrl(docid);
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                if (response==null){
                    if (mView instanceof Stateful)
                        ((Stateful) mView).setState(AppConstants.STATE_EMPTY);
                }
                NewsDetailBean newsDetailBean = NewsJsonUtils.readJsonNewsDetailBeans(response, docid);
                ((TopNewsPresenter.ViewActivity) mView).refreshActivityView(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);
    }
    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer("http://c.m.163.com/nc/article/");
        sb.append(docId).append("/full.html");
        return sb.toString();
    }
}
