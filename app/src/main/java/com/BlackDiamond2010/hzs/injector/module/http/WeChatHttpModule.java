package com.BlackDiamond2010.hzs.injector.module.http;

import com.BlackDiamond2010.hzs.injector.qualifier.WeChatUrl;
import com.BlackDiamond2010.hzs.http.service.WeChatService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Module
public class WeChatHttpModule extends BaseHttpModule {
    @Singleton
    @Provides
    @WeChatUrl
    Retrofit provideWeChatRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, WeChatService.API_WeChat);
    }

    @Singleton
    @Provides
    WeChatService provideWeChatService(@WeChatUrl Retrofit retrofit) {
        return retrofit.create(WeChatService.class);
    }

}
