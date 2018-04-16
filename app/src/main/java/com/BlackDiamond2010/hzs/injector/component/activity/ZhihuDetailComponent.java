package com.BlackDiamond2010.hzs.injector.component.activity;

import com.BlackDiamond2010.hzs.injector.module.http.ZhihuHttpModule;
import com.BlackDiamond2010.hzs.ui.activity.zhihu.ZhiHuDetailActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Singleton
@Component(modules = { ZhihuHttpModule.class})
public interface ZhihuDetailComponent {
    void injectZhiHuDetail(ZhiHuDetailActivity zhiHuDetailActivity);
}
