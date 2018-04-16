package com.BlackDiamond2010.hzs.injector.module.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.BlackDiamond2010.hzs.adapter.ZhiHuAdapter;
import com.BlackDiamond2010.hzs.bean.zhihu.HomeListBean;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Module
public class ZhihuHomeModule {
    @Provides
    @Singleton
    public BaseQuickAdapter provideAdapter() {
        return new ZhiHuAdapter(new ArrayList<HomeListBean>());//new ArrayList()这样子也可以，不过这里我们为了给自己看就写了泛型。
    }
}
