package com.BlackDiamond2010.hzs.presenter;

import com.BlackDiamond2010.hzs.bean.topnews.NBAListBean;

/**
 * Created by quantan.liu on 2017/4/12.
 */

public interface NBAPresenter {

    interface View extends BaseView<NBAListBean> {
    }

    interface Presenter {
        void fetchNBAList(int id);
    }

}
