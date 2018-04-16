package com.BlackDiamond2010.hzs.ui.activity.lives.network;

import com.google.gson.Gson;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.lives.config.Constant;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AESEncryptor;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.BaseConfig;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.NetUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.PreferenceHelper;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.show.L;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpCrashInterceptor implements Interceptor {

    public static final String TAG = "HttpUtil";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .method(original.method(), original.body());
        //头数据
        Headers.Builder hb = new Headers.Builder();
        addHeader(hb);
        if (!NetUtils.isConnected(MyApplication.instance.getApplicationContext())) {//如果网络不可用
            requestBuilder.cacheControl(CacheControl.FORCE_CACHE);
            L.d(TAG, "网络不可用请求拦截");
        } else {//网络可用
            requestBuilder.cacheControl(CacheControl.FORCE_NETWORK);
        }
        Request request = requestBuilder.headers(hb.build()).build();
        L.d(TAG, "地址：" + request.url());
        String jsonRequest = new Gson().toJson(request.body());
        L.d(TAG, "请求参数：" + jsonRequest);
        Response response=null;
        try {
            response = chain.proceed(request);
            // 获取cookie
            String cookie = response.headers().get("Set-Cookie");
            if (!StringUtil.isEmpty(cookie) && cookie.contains("ticket=")) {
                String[] setCookies = cookie.split(";");
                if (setCookies != null && setCookies.length > 0) {
                    cookie = setCookies[0].substring(7);
                }
                //存储加密后的cookie
                PreferenceHelper.getIntance().saveString(Constant.TICKET, AESEncryptor.encrypt(BaseConfig.APPMDF, cookie));
            }
            if (NetUtils.isConnected(MyApplication.instance.getApplicationContext())) {//如果网络可用
                //覆盖服务器响应头的Cache-Control,用我们自己的,因为服务器响应回来的可能不支持缓存
                int maxAge = 60 * 3; // read from cache for 3 minutes
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24; // tolerate 1-day stale
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }catch (Exception e){
            L.d(HttpUtil.TAG,"HttpInterceptor:"+e.getMessage());
        }

        return response;
    }

    /**
     * 增加消息头
     */
    private  void addHeader(Headers.Builder header) {
        String user_agent="icaomei.com;user-mode;android;" + AndroidUtils.getVersionName(MyApplication.instance.getApplicationContext()) + ";"
                        + AndroidUtils.getSystemVersion() + ";" + AndroidUtils.getAndroidManufacturer() + ";"
                        + AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext());
        header.add("user-agent", user_agent);
    }
}
