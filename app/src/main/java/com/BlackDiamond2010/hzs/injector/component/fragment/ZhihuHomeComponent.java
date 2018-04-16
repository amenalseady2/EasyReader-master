package com.BlackDiamond2010.hzs.injector.component.fragment;

import com.BlackDiamond2010.hzs.injector.module.fragment.ZhihuHomeModule;
import com.BlackDiamond2010.hzs.injector.module.http.ZhihuHttpModule;
import com.BlackDiamond2010.hzs.ui.fragment.home.child.zhihu.ZhiHuHomeFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Singleton
@Component(modules = { ZhihuHttpModule.class,ZhihuHomeModule.class})
public interface ZhihuHomeComponent {
    void injectZhihuhome(ZhiHuHomeFragment zhiHuFragment);
}
