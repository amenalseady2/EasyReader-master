package com.BlackDiamond2010.hzs.presenter;

import com.BlackDiamond2010.hzs.bean.topnews.NewsDetailBean;
import com.BlackDiamond2010.hzs.bean.topnews.NewsListBean;

/**
 * Created by quantan.liu on 2017/3/27.
 */

public interface TopNewsPresenter {

    interface View extends BaseView<NewsListBean>{
    }

    interface Presenter{
        void fetchTopNewsList(int id);
    }
    interface ViewActivity extends View {
        void refreshActivityView(NewsDetailBean newsDetailBean);
    }

}
