package com.BlackDiamond2010.hzs.ui.activity.callback;

import android.content.Context;

import com.google.gson.Gson;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.BaseConfig;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.show.L;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 类描述：通用的网络回调
 * 创建人：yq
 * 创建时间：2016/7/14 11:33
 * 修改人：yq
 * 修改时间：2016/7/14 11:33
 * 修改备注：
 */
public abstract class HttpServiceCallBack<T> implements Callback<HttpResult<T>> {
    private String TAG="HttpUtil";
    private Context mContext;

    @Override
    public void onResponse(Call<HttpResult<T>> call, Response<HttpResult<T>> response) {

        HttpResult<T> result = response.body();
            if (result == null) {
                //通常是服务器出错返回了非约定格式
                onHttpFail(response.code(),"");
            } else {
                if (result.getStatus() == BaseConfig.HttpStatusConstants.RESULT_OK) {
                    //正确返回约定的OK码
                    onHttpSuccess(result.getData(),result.getShowMessage());
                }
                else {
                    //返回约定的其他类型码，可根据返回码进行相对应的操作
                    onHttpFail(result.getStatus(),result.getShowMessage());
                }
                String jsonResponse=new Gson().toJson(result);
                L.d(TAG,"mvvm结果："+jsonResponse);
            }

        onHttpComplete();
    }

    @Override
    public void onFailure(Call<HttpResult<T>> call, Throwable t) {
        if(t!=null){
            if(!t.getMessage().contains("Expected BEGIN_ARRAY but was BEGIN_OBJECT")){
                //网络异常或json解析失败
                onNetWorkError();
            }else{
                //请求数据为空
                onHttpSuccess(null,"");
            }
        }
        onHttpComplete();
        L.d(TAG,"mvvm结果,解析失败："+t.getMessage());
    }


    public abstract void onHttpSuccess(T resultData,String msg);
    public abstract void onHttpFail(int code, String msg);
    public abstract void onNetWorkError();
    public abstract void onHttpComplete();
}
