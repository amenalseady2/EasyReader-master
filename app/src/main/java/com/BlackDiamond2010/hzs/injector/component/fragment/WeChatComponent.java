package com.BlackDiamond2010.hzs.injector.component.fragment;

import com.BlackDiamond2010.hzs.injector.module.fragment.WeChatModule;
import com.BlackDiamond2010.hzs.injector.module.http.WeChatHttpModule;
import com.BlackDiamond2010.hzs.ui.fragment.wechat.WeChatFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Singleton
@Component(modules = { WeChatHttpModule.class,WeChatModule.class})
public interface WeChatComponent {
    void injectWeChat(WeChatFragment weChatFragment);
}
