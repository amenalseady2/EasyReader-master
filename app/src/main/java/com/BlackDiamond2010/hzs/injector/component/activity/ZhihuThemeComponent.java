package com.BlackDiamond2010.hzs.injector.component.activity;

import com.BlackDiamond2010.hzs.injector.module.activity.ZhihuThemeModule;
import com.BlackDiamond2010.hzs.injector.module.http.ZhihuHttpModule;
import com.BlackDiamond2010.hzs.ui.activity.zhihu.ZhihuThemeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Singleton
@Component(modules = { ZhihuHttpModule.class,ZhihuThemeModule.class})
public interface ZhihuThemeComponent {
    void injectZhiHuTheme(ZhihuThemeActivity zhihuThemeActivity);
}
