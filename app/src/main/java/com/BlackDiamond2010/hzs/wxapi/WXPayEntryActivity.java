package com.BlackDiamond2010.hzs.wxapi;


import android.content.Intent;
import android.os.Bundle;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MessageEvent;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.BaseConfig;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.RxBus;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * 微信支付结果返回界面
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected int getLayoutId() {
      return R.layout.activity_payment_success;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, BaseConfig.APP_ID);
        api.handleIntent(getIntent(), this);
        setTitle("支付");

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
//		0	成功	展示成功页面
//		-1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
//		-2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Intent intent = new Intent(BaseConfig.WXPay_Broadcast);
            if (resp.errCode == 0) {
                //支付成功
                RxBus.getInstance().post(new MessageEvent(0));
                intent.putExtra(BaseConfig.IntentKey.DATA, 0);
            } else {
                //支付失败
                RxBus.getInstance().post(new MessageEvent(1));
                intent.putExtra(BaseConfig.IntentKey.DATA, 1);
            }
            sendBroadcast(intent);
            finish();
        }
    }
}