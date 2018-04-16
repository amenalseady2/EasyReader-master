package com.BlackDiamond2010.hzs.ui.activity.lives.network;

import android.content.Context;
import android.os.Build;

import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.lives.cachefile.CacheFile;
import com.BlackDiamond2010.hzs.ui.activity.lives.config.Config;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class HttpUtil {

    public static final String TAG = "HttpUtil";

    private HttpService mHttpService;
    private Retrofit mRetrofit;
    private static HttpUtil httpUtil;
    private static final int TimeOut = 60;
    private static final int ReadTimeOut = 60;
    private static final int WriteTimeOut = 60;

    public HttpUtil(Context context) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)//查看Config
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getBuilder().build())
                .build();
    }

    public static HttpUtil getInstance(Context context) {
        if (httpUtil == null) {
            httpUtil = new HttpUtil(context);
        }
        return httpUtil;
    }

    public HttpService sendRequest() {
        if(mHttpService == null){
            mHttpService = mRetrofit.create(HttpService.class);
        }
        return mHttpService;
    }


    private OkHttpClient.Builder getBuilder() {
        //缓存路径
        File cacheFile = new File(MyApplication.instance.getApplicationContext().getCacheDir().getAbsolutePath(), CacheFile.getCacheFile());
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);//缓存文件为100MB

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new HttpCacheInterceptor());
        builder.cache(cache);
        builder.readTimeout(TimeOut, TimeUnit.SECONDS);
        builder.connectTimeout(ReadTimeOut, TimeUnit.SECONDS);
        builder.writeTimeout(WriteTimeOut, TimeUnit.SECONDS);
        if(!Config.ISDEBUG&&(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)){
        }

        return builder;
    }

//    public void sendCrashLog( final String content, Subscriber subscriber) {
//        mRetrofit = new Retrofit.Builder()
//                .baseUrl(Config.SERVER)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .client(getBuilder().build())
//                .build();
//        mRetrofit.create(HttpService.class)
//                .crashLog(AndroidUtils.getVersionName(MyApplication.instance.getApplicationContext()),
//                        content,
//                        AndroidUtils.getAndroidManufacturer(),
//                        NetUtils.getNetWorkType(MyApplication.instance.getApplicationContext())+"",
//                        AndroidUtils.getSystemVersion(),
//                        "android_user")
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(Schedulers.computation())
//                .doOnNext(new Action1<HttpResult>() {
//                    @Override
//                    public void call(HttpResult httpResult) {
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }

    private OkHttpClient.Builder getCrashBuilder() {
        //缓存路径
        File cacheFile = new File(MyApplication.instance.getApplicationContext().getCacheDir().getAbsolutePath(), CacheFile.getCacheFile());
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);//缓存文件为100MB

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new HttpCrashInterceptor());
        builder.readTimeout(TimeOut, TimeUnit.SECONDS);
        builder.connectTimeout(ReadTimeOut, TimeUnit.SECONDS);
        builder.writeTimeout(WriteTimeOut, TimeUnit.SECONDS);
        return builder;
    }
}
