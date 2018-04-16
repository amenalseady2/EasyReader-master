package com.BlackDiamond2010.hzs.injector.component.activity;

import com.BlackDiamond2010.hzs.injector.module.http.DoubanHttpModule;
import com.BlackDiamond2010.hzs.ui.activity.douban.MovieTopDetailActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Singleton
@Component(modules = { DoubanHttpModule.class})
public interface MovieDetailComponent {
    void injectMovieDetail(MovieTopDetailActivity movieTopDetailActivity);
}
