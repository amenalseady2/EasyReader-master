package com.BlackDiamond2010.hzs.ui.activity.lives.network;/**
 * Created by yq on 2016/8/8.
 */

import android.content.Context;

import com.google.gson.Gson;
import com.BlackDiamond2010.hzs.ui.activity.callback.HttpServiceCallBack;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.BaseConfig;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.show.L;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名称：Aicaomei_MVVMFrame_Retrofit
 * 类描述：
 * 创建人：yq
 * 创建时间：2016/8/8 14:37
 * 修改人：yq
 * 修改时间：2016/8/8 14:37
 * 修改备注：
 */
public abstract class HttpServiceCall<T> extends HttpServiceCallBack<T> implements Callback<HttpResult<T>> {
    private String TAG = "HttpUtil";
    private Context mContext;

    public HttpServiceCall(){}
    public HttpServiceCall(Context context) {
        this.mContext = context;
    }

    @Override
    public void onResponse(Call<HttpResult<T>> call, Response<HttpResult<T>> response) {
        HttpResult<T> result = response.body();
        if (result == null) {
            //通常是服务器出错返回了非约定格式
            onHttpFail(response.code(), "");
        } else {
            if (result.getStatus() == BaseConfig.HttpStatusConstants.RESULT_OK) {
                //正确返回约定的OK码
                ////登录成功，将登录凭证保存在本地，从服务器响应头中获取ticket
                String cookie = response.headers().get("Set-Cookie");
                if (!StringUtil.isEmpty(cookie) && cookie.contains("ticket=")) {
                    String[] setCookies = cookie.split(";");
                    if (setCookies != null && setCookies.length > 0) {
                        cookie = setCookies[0].substring(7);
                    }
                    //存储加密后的cookie
//                    PreferenceHelper.getIntance().saveString(Constant.TICKET, AESEncryptor.encrypt(BaseConfig.APPMDF, cookie));
                }

                onHttpSuccess(result.getData(), result.getShowMessage());
            } else {
                //返回约定的其他类型码，可根据返回码进行相对应的操作
                onHttpFail(result.getStatus(), result.getShowMessage());
            }
            String jsonResponse = new Gson().toJson(result);
            L.d(TAG, "结果：" + jsonResponse);

        }
        onHttpComplete();
    }

    @Override
    public void onFailure(Call<HttpResult<T>> call, Throwable t) {
        super.onFailure(call, t);
    }

    @Override
    public void onHttpSuccess(T resultData, String msg) {

    }

    @Override
    public void onHttpFail(int code, String msg) {
    }

    @Override
    public void onNetWorkError() {
    }

    @Override
    public void onHttpComplete() {

    }
}
