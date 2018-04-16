package com.BlackDiamond2010.hzs.presenter.impl;

import com.BlackDiamond2010.hzs.bean.topnews.NBAListBean;
import com.BlackDiamond2010.hzs.http.service.TopNewsService;
import com.BlackDiamond2010.hzs.http.utils.Callback;
import com.BlackDiamond2010.hzs.presenter.BasePresenter;
import com.BlackDiamond2010.hzs.presenter.NBAPresenter;

import javax.inject.Inject;

/**
 * Created by quantan.liu on 2017/4/12.
 */

public class NBAPresenterImpl extends BasePresenter<NBAPresenter.View> implements NBAPresenter.Presenter {
    private TopNewsService mTopNewsService;

    @Inject
    public NBAPresenterImpl(TopNewsService mTopNewsService) {
        this.mTopNewsService = mTopNewsService;
    }

    @Override
    public void fetchNBAList(int id) {
        invoke(mTopNewsService.fetchNBA(id),new Callback<NBAListBean>());
    }
}
