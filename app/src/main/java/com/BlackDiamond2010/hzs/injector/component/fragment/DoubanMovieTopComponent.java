package com.BlackDiamond2010.hzs.injector.component.fragment;

import com.BlackDiamond2010.hzs.injector.module.fragment.DoubanMovieTopModule;
import com.BlackDiamond2010.hzs.injector.module.http.DoubanHttpModule;
import com.BlackDiamond2010.hzs.ui.fragment.home.child.DouBanMovieTopFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Singleton
@Component(modules = { DoubanHttpModule.class,DoubanMovieTopModule.class})
public interface DoubanMovieTopComponent {
    void injectDoubanMovieTop(DouBanMovieTopFragment douBanMovieTopFragment);
}
