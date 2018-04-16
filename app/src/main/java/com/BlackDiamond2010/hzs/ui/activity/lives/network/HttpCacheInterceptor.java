package com.BlackDiamond2010.hzs.ui.activity.lives.network;

import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AESencryption;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.NetUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.show.L;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class HttpCacheInterceptor implements Interceptor {

    public static final String TAG = "HttpUtil";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        RequestBody requestBody = original.body();
        String signString = "";
        if (!original.url().toString().contains("uploadPic")&&
                !original.url().toString().contains("insertDynamic") ) {
            String unSignString = null;
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                unSignString = buffer.readString(Charset.defaultCharset());
            }
            //加密之后的数据
            signString = AESencryption.encryption(unSignString);
        }
        Request.Builder requestBuilder = original.newBuilder()
                .method(original.method(), original.body());
        //头数据
        Headers.Builder hb = new Headers.Builder();
        addHeader(hb, "");

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


        Response response = chain.proceed(request);
        try {
            // 获取cookie
            String cookie = response.headers().get("Set-Cookie");
            if (!StringUtil.isEmpty(cookie) && cookie.contains("ticket=")) {
                String[] setCookies = cookie.split(";");
                if (setCookies != null && setCookies.length > 0) {
                    cookie = setCookies[0].substring(7);
                }
                //存储加密后的cookie
//                PreferenceHelper.getIntance().saveString(Constant.TICKET, AESEncryptor.encrypt(BaseConfig.APPMDF, cookie));
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
        } catch (Exception e) {
            L.d(HttpUtil.TAG, "HttpInterceptor:" + e.getMessage());
        }

        return response;
    }

    /**
     * 增加消息头
     *
     * @param signString 参数加密后的字符串
     */
    private void addHeader(Headers.Builder header, String signString) {
//        String timeStamp = String.valueOf(System.currentTimeMillis());
//        String ticket = AESEncryptor.decrypt(BaseConfig.APPMDF, PreferenceHelper.getIntance().readString(Constant.TICKET));
//        String sign = MD5Util.MD5Encode(ticket + timeStamp + Constant.secretKey, "");
//        String terminalDevice = "android;" + AndroidUtils.getSystemVersion() + ";" + AndroidUtils.getDeviceModel();
//        //设备唯一识别码
//        header.add("deviceNo", AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext()));
//        //客户端渠道 ("USER_APP", "用户app"), ("SHOP_APP", "商户app"), ("PC", "电脑浏览器"), ("H5", "微信H5"),
//        header.add("loginChannel", "USER_APP");
//        //签名=MD5(ticket + timeStamp + 秘钥)
//        header.add("sign", sign);
//        //app当前版本号
//        header.add("sysVersion", "" + AndroidUtils.getVersionCode(MyApplication.instance.getApplicationContext()));
//        //app当前版本名
//        header.add("sysVersionName", "" + AndroidUtils.getVersionName(MyApplication.instance.getApplicationContext()));
//        //客户端设备信息  [android/ios];[设备系统版本];[设备名称]
//        header.add("terminalDevice", terminalDevice);
//        header.add("timeStamp", timeStamp);
//        //登录凭证，区别上述放在cookie
//        header.add("Cookie", "ticket=" + ticket);
       String token =  SHPUtils.getParame(MyApplication.instance.getApplicationContext(),SHPUtils.TOKEN);
        header.add("Authorization", "Bearer "+token);//
        // if (!TextUtils.isEmpty(signString)) {
//            L.d(TAG, "signature：" + signString);
//            header.add("signature", signString);//加密后的数据也放在header中
//        }
    }
}
