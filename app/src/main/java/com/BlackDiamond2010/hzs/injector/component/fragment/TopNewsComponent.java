package com.BlackDiamond2010.hzs.injector.component.fragment;

import com.BlackDiamond2010.hzs.injector.module.fragment.TopNewsModule;
import com.BlackDiamond2010.hzs.injector.module.http.TopNewsHttpModule;
import com.BlackDiamond2010.hzs.ui.fragment.home.child.TopNewsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Singleton
@Component(modules = { TopNewsHttpModule.class,TopNewsModule.class})
public interface TopNewsComponent {
    void injectTopNews(TopNewsFragment topNewsFragment);
}
