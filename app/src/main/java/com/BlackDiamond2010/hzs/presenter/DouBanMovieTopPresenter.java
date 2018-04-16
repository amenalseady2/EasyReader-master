package com.BlackDiamond2010.hzs.presenter;

import com.BlackDiamond2010.hzs.bean.douban.HotMovieBean;

/**
 * Created by quantan.liu on 2017/3/28.
 */

public interface DouBanMovieTopPresenter {
    interface View extends BaseView<HotMovieBean> {
        void showLoadMoreError();
    }

    interface Presenter {
        void fetchMovieTop250(int start,int count);
    }
}
