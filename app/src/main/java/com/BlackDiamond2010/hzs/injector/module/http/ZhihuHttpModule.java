package com.BlackDiamond2010.hzs.injector.module.http;

import com.BlackDiamond2010.hzs.injector.qualifier.ZhihuUrl;
import com.BlackDiamond2010.hzs.http.service.ZhiHuService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Module
public class ZhihuHttpModule extends BaseHttpModule {
    @Provides
    @Singleton
    @ZhihuUrl
    Retrofit provideZhiHuRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ZhiHuService.HOST);
    }
    @Provides
    @Singleton
    ZhiHuService provideZhihuService(@ZhihuUrl Retrofit retrofit) {
        return retrofit.create(ZhiHuService.class);
    }

}
