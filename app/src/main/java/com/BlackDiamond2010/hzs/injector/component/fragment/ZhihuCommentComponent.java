package com.BlackDiamond2010.hzs.injector.component.fragment;

import com.BlackDiamond2010.hzs.injector.module.fragment.ZhihuCommentModule;
import com.BlackDiamond2010.hzs.injector.module.http.ZhihuHttpModule;
import com.BlackDiamond2010.hzs.ui.fragment.home.child.zhihu.ZhiHuCommentFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Singleton
@Component(modules = { ZhihuHttpModule.class,ZhihuCommentModule.class})
public interface ZhihuCommentComponent {
    void injectZhihuComment(ZhiHuCommentFragment zhiHuCommentFragment);
}
