package com.BlackDiamond2010.hzs.injector.component.fragment;

import com.BlackDiamond2010.hzs.injector.module.fragment.DoubanMovieLatestModule;
import com.BlackDiamond2010.hzs.injector.module.http.DoubanHttpModule;
import com.BlackDiamond2010.hzs.ui.fragment.home.child.DouBanMovieLatestFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Singleton
@Component(modules = { DoubanHttpModule.class,DoubanMovieLatestModule.class})
public interface DoubanMovieLatestComponent {
    void injectDoubanMovieLatest(DouBanMovieLatestFragment douBanMovieLatestFragment);
}
