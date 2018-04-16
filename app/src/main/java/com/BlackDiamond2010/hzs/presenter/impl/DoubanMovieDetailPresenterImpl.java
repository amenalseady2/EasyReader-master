package com.BlackDiamond2010.hzs.presenter.impl;

import com.BlackDiamond2010.hzs.bean.douban.MovieDetailBean;
import com.BlackDiamond2010.hzs.http.service.DoubanService;
import com.BlackDiamond2010.hzs.http.utils.Callback;
import com.BlackDiamond2010.hzs.presenter.BasePresenter;
import com.BlackDiamond2010.hzs.presenter.DoubanMovieDetailPresenter;

import javax.inject.Inject;

/**
 * Created by quantan.liu on 2017/3/28.
 */

public class DoubanMovieDetailPresenterImpl extends BasePresenter<DoubanMovieDetailPresenter.View> implements DoubanMovieDetailPresenter.Presenter {
    private DoubanService mDoubanService;

    @Inject
    public DoubanMovieDetailPresenterImpl(DoubanService mDoubanService) {
        this.mDoubanService = mDoubanService;
    }

    @Override
    public void fetchMovieDetail(String id) {
        invoke(mDoubanService.fetchMovieDetail(id),new Callback<MovieDetailBean>(){
        });
    }
}
