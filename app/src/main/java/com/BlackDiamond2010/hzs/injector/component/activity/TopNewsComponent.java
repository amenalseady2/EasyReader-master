package com.BlackDiamond2010.hzs.injector.component.activity;

import com.BlackDiamond2010.hzs.injector.module.http.TopNewsHttpModule;
import com.BlackDiamond2010.hzs.ui.activity.topnews.NBAActivity;
import com.BlackDiamond2010.hzs.ui.activity.topnews.TopNewsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Singleton
@Component(modules = { TopNewsHttpModule.class})
public interface TopNewsComponent {
    void injectTopNews(TopNewsActivity topNewsActivity);
    void injectNBA(NBAActivity nbaActivity);
}
