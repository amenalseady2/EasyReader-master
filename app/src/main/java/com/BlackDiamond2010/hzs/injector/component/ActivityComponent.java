package com.BlackDiamond2010.hzs.injector.component;

import android.app.Activity;

import com.BlackDiamond2010.hzs.injector.module.ActivityModule;
import com.BlackDiamond2010.hzs.injector.scope.ActivityScope;

import dagger.Component;

/**
 * Created by quantan.liu on 2017/3/21.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
}
