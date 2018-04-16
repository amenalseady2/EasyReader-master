package com.BlackDiamond2010.hzs.presenter.impl;

import com.BlackDiamond2010.hzs.bean.douban.HotMovieBean;
import com.BlackDiamond2010.hzs.http.service.DoubanService;
import com.BlackDiamond2010.hzs.http.utils.Callback;
import com.BlackDiamond2010.hzs.presenter.BasePresenter;
import com.BlackDiamond2010.hzs.presenter.DoubanHotMoviePresenter;

import javax.inject.Inject;

/**
 * Created by quantan.liu on 2017/3/29.
 */

public class DoubanHotMoviePresenterImpl extends BasePresenter<DoubanHotMoviePresenter.View> implements  DoubanHotMoviePresenter.Presenter {
    private DoubanService mDoubanService;

    @Inject
    public DoubanHotMoviePresenterImpl(DoubanService mDoubanService) {
        this.mDoubanService = mDoubanService;
    }
    @Override
    public void fetchHotMovie() {
        invoke(mDoubanService.fetchHotMovie(),new Callback<HotMovieBean>(){});
    }
}
