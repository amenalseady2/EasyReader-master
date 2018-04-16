package com.BlackDiamond2010.hzs.injector.module.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.BlackDiamond2010.hzs.adapter.MovieLatestAdapter;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Module
public class DoubanMovieLatestModule {
    @Provides
    @Singleton
    public BaseQuickAdapter provideAdapter() {
        return new MovieLatestAdapter(new ArrayList());
    }
}
