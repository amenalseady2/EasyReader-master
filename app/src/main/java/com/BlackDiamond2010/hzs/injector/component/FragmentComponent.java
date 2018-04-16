package com.BlackDiamond2010.hzs.injector.component;

import android.app.Activity;

import com.BlackDiamond2010.hzs.injector.module.FragmentModule;
import com.BlackDiamond2010.hzs.injector.scope.FragmentScope;

import dagger.Component;

/**
 * Created by quantan.liu on 2017/3/21.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

}
