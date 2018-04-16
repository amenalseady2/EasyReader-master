package com.BlackDiamond2010.hzs.injector.module.http;

import com.BlackDiamond2010.hzs.http.service.GankIoService;
import com.BlackDiamond2010.hzs.injector.qualifier.GankUrl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Module
public class GankIoHttpModule extends BaseHttpModule {
    @Singleton
    @Provides
    @GankUrl
    Retrofit provideGankIoRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, GankIoService.API_GANKIO);
    }

    @Singleton
    @Provides
    GankIoService provideGankIoService(@GankUrl Retrofit retrofit) {
        return retrofit.create(GankIoService.class);
    }


}
