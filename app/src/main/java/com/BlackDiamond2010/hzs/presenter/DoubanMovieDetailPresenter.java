package com.BlackDiamond2010.hzs.presenter;

import com.BlackDiamond2010.hzs.bean.douban.MovieDetailBean;

/**
 * Created by quantan.liu on 2017/3/28.
 */

public interface DoubanMovieDetailPresenter {
    interface View extends BaseView<MovieDetailBean> {
    }

    interface Presenter {
        void fetchMovieDetail(String id);
    }
}
