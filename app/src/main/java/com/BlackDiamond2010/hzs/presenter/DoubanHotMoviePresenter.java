package com.BlackDiamond2010.hzs.presenter;

import com.BlackDiamond2010.hzs.bean.douban.HotMovieBean;

/**
 * Created by quantan.liu on 2017/3/29.
 */

public interface DoubanHotMoviePresenter {
    interface View extends BaseView<HotMovieBean> {
    }

    interface Presenter {
        void fetchHotMovie();
    }
}
