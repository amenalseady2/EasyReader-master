package com.BlackDiamond2010.hzs.ui.activity.callback;

import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.BaseConfig;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.show.L;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import rx.Subscriber;

public abstract class HttpResultCallBack<S, M> extends Subscriber<S> {

    /**
     * 请求返回
     */
    public abstract void onResponse(M m, String msg);
    public abstract void onErr(String err, int status);
    /**
     * 请求错误
     */
    public void onErr(M m, String msg) {

    }
    /**
     * 请求完成
     */
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

        //网络异常或json解析失败
        try {
            if (e != null) {
                if (e.getMessage().contains("Expected BEGIN_ARRAY but was BEGIN_OBJECT")) {
                    //请求数据为空
                    onResponse(null, "");
                }else {
                    //网络异常或json解析失败
                    onErr("获取信息失败", BaseConfig.HttpStatusConstants.RESULT_FAILURE);
                }
                L.d("HttpUtil", "mvvm结果,解析失败：" + getResultClassName() + e.getMessage());
            }
        }catch (Exception e2){
            onErr("获取信息失败", BaseConfig.HttpStatusConstants.RESULT_FAILURE);
            L.d("HttpUtil", "mvvm结果,解析失败2：" + e2.getMessage());
        }
        onCompleted();
    }

    /**
     * Http请求失败
     */
    private void onHttpFail(String msg, int status){
        onErr(msg, status);
    }

    @Override
    public void onNext(S s) {
        String jsonResponse = new Gson().toJson(s);
        L.d("HttpUtil", "返回：" + getResultClassName() + jsonResponse);
        HttpResult<M> result = (HttpResult<M>) s;
        if (result.getStatus() == BaseConfig.HttpStatusConstants.RESULT_OK) {
            onResponse(result.getData(), result.getShowMessage());
        } else {
            try {
                onErr(result.getData(), result.getShowMessage());
            }catch (Exception e){

            }
            onHttpFail(result.getShowMessage(), result.getStatus());
        }
    }

    /**
     * 区分数据类型
     */
    private String getResultClassName(){
        Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        if(actualTypeArguments != null && actualTypeArguments.length > 0){
            String str = actualTypeArguments[0].toString();
            String name = str.substring(str.lastIndexOf(".") + 1, str.length());
            return name + "--------------------------------------------------------------------------------------------------" + "\n";
        }
        return "";
    }
}
