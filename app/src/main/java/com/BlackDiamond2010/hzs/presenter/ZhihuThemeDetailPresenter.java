package com.BlackDiamond2010.hzs.presenter;

import com.BlackDiamond2010.hzs.bean.zhihu.SectionChildListBean;
import com.BlackDiamond2010.hzs.bean.zhihu.ThemeChildListBean;

/**
 * Created by quantan.liu on 2017/3/27.
 */

public interface ZhihuThemeDetailPresenter {

    interface View extends BaseView<ThemeChildListBean> {
        void refreshSectionData(SectionChildListBean data);
    }

    interface Presenter{
        void fetchThemeChildList(int id);
        void fetchSectionChildList(int id);
    }
}
