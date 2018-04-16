package com.BlackDiamond2010.hzs.injector.component.fragment;

import com.BlackDiamond2010.hzs.injector.module.fragment.AndroidModule;
import com.BlackDiamond2010.hzs.injector.module.http.GankIoHttpModule;
import com.BlackDiamond2010.hzs.ui.fragment.gank.AndroidFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Singleton
@Component(modules = { GankIoHttpModule.class,AndroidModule.class})
public interface AndroidComponent {
    void injectAndroid(AndroidFragment androidFragment);
}
