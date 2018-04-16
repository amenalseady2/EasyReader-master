package com.BlackDiamond2010.hzs.injector.component;

import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.injector.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quantan.liu on 2017/3/21.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    MyApplication getContext();  // 提供App的Context

}
