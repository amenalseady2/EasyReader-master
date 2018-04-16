package com.BlackDiamond2010.hzs.presenter;

import com.BlackDiamond2010.hzs.bean.zhihu.DetailExtraBean;
import com.BlackDiamond2010.hzs.bean.zhihu.ZhihuDetailBean;

/**
 * Created by quantan.liu on 2017/3/24.
 */

public interface ZhiHuDetailPresenter {

    interface View extends BaseView<ZhihuDetailBean> {
        void showExtraInfo(DetailExtraBean detailExtraBean);
    }

    interface Presenter{
        void fetchDetailInfo(int id);
        void fetchDetailExtraInfo(int id);
    }
}
