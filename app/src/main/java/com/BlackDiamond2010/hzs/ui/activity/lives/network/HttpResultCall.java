package com.BlackDiamond2010.hzs.ui.activity.lives.network;


import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.callback.HttpResultCallBack;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.show.T;

public class HttpResultCall<S, M> extends HttpResultCallBack<S,M> {
    @Override
    public void onResponse(M m, String msg) {

    }

    /**
     * 请求失败
     * @param err
     * @param status
     */
    @Override
    public void onErr(String err, int status) {
        T.showShort(MyApplication.instance.getApplicationContext(),err);
//        if(status== BaseConfig.HttpStatusConstants.RESULT_UNLOGIN){
//            PreferenceHelper.getIntance().saveBoolean(Constant.LOGINED,false);
//        }
    }
}
