package com.BlackDiamond2010.hzs.presenter;

import com.BlackDiamond2010.hzs.bean.gankio.GankIoDataBean;

import java.util.List;

/**
 * Created by quantan.liu on 2017/3/30.
 */

public interface GankIoAndroidPresenter {

    interface View extends BaseView<List<GankIoDataBean.ResultBean>> {
    }

    interface Presenter{
        void fetchGankIoData(int page, int pre_page);
    }
}
