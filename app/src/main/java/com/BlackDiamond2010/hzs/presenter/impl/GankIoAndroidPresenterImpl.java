package com.BlackDiamond2010.hzs.presenter.impl;

import com.BlackDiamond2010.hzs.bean.gankio.GankIoDataBean;
import com.BlackDiamond2010.hzs.http.service.GankIoService;
import com.BlackDiamond2010.hzs.http.utils.Callback;
import com.BlackDiamond2010.hzs.presenter.BasePresenter;
import com.BlackDiamond2010.hzs.presenter.GankIoAndroidPresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by quantan.liu on 2017/3/30.
 */

public class GankIoAndroidPresenterImpl extends BasePresenter<GankIoAndroidPresenter.View> implements GankIoAndroidPresenter.Presenter {
    private GankIoService mGankIoService;

    @Inject
    public GankIoAndroidPresenterImpl(GankIoService mGankIoService) {
        this.mGankIoService = mGankIoService;
    }


    @Override
    public void fetchGankIoData(int page, int pre_page) {
        invoke(mGankIoService.getGankIoData("Android",page,pre_page),new Callback<GankIoDataBean>(){
            @Override
            public void onResponse(GankIoDataBean data) {
                List<GankIoDataBean.ResultBean> results = data.getResults();
                checkState(results);
                mView.refreshView(results);

            }
        });
    }
}
