package com.BlackDiamond2010.hzs.presenter.impl;

import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.bean.topnews.NewsDetailBean;
import com.BlackDiamond2010.hzs.http.Stateful;
import com.BlackDiamond2010.hzs.http.service.TopNewsService;
import com.BlackDiamond2010.hzs.presenter.BasePresenter;
import com.BlackDiamond2010.hzs.presenter.NBADetailPresenter;
import com.BlackDiamond2010.hzs.utils.NewsJsonUtils;
import com.BlackDiamond2010.hzs.utils.OkHttpUtils;

import javax.inject.Inject;

/**
 * Created by quantan.liu on 2017/4/13.
 */

public class NBADetailPresenterImpl extends BasePresenter<NBADetailPresenter.View> implements NBADetailPresenter.Presenter {
    private TopNewsService mTopNewsService;

    @Inject
    public NBADetailPresenterImpl(TopNewsService mTopNewsService) {
        this.mTopNewsService = mTopNewsService;
    }
    @Override
    public void fetchNBADetail(final String id) {
        String url = getDetailUrl(id);
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                if (response==null){
                    if (mView instanceof Stateful)
                        ((Stateful) mView).setState(AppConstants.STATE_EMPTY);
                }
                NewsDetailBean newsDetailBean = NewsJsonUtils.readJsonNewsDetailBeans(response, id);
                mView.refreshView(newsDetailBean);
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
