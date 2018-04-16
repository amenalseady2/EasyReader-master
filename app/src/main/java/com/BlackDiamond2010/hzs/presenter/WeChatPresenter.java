package com.BlackDiamond2010.hzs.presenter;

import com.BlackDiamond2010.hzs.bean.wechat.WXItemBean;

import java.util.List;

/**
 * Created by quantan.liu on 2017/3/28.
 */

public interface WeChatPresenter {
    interface View extends BaseView<List<WXItemBean>> {
    }

    interface Presenter {
        void fetchWeChatHot(int num, int page);
        void fetchWXHotSearch(int num, int page, String word);
    }
}
