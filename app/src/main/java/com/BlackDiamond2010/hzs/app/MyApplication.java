package com.BlackDiamond2010.hzs.app;

import android.app.Application;

import com.BlackDiamond2010.hzs.injector.component.AppComponent;
import com.BlackDiamond2010.hzs.injector.component.DaggerAppComponent;
import com.BlackDiamond2010.hzs.injector.module.AppModule;
import com.blankj.utilcode.utils.Utils;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


/**
 * Created by codeest on 2016/8/2.
 */
public class MyApplication extends Application{

    //现在只完成了dagger2和Retrofit配合完成网络请求
    public static MyApplication instance;
    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);//一个utils库的初始化 https://github.com/Blankj/AndroidUtilCode/blob/master/README-CN.md
//        Config.DEBUG = true;
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wxfca75014cf3f347e",
                "a0b425cec52ba20bd7e1366d7c77fd48");
        PlatformConfig.setQQZone("1105672869", "SAEqZ44LIaLF3UO9");
        PlatformConfig.setSinaWeibo("3950126912", "352a5dd0047548b560157d7ee1ba0d87", "http://www.kanjian2020.com");


    }

    public static AppComponent getAppComponent(){
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .build();
        }
        return appComponent;
    }



}
