package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MessageEvent;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Risgter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.WXPreOrderBean;
import com.BlackDiamond2010.hzs.ui.activity.lives.listener.ReflushListener;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.payutils.AliPayUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.payutils.WXPayUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.RxBus;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class PaymentActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.weixin_icon)
    ImageView weixinIcon;
    @BindView(R.id.rl_weixin)
    RelativeLayout rlWeixin;
    @BindView(R.id.zhifubo_icon)
    ImageView zhifuboIcon;
    @BindView(R.id.rl_zhifubao)
    RelativeLayout rlZhifubao;
    @BindView(R.id.submit)
    TextView submit;
    private int paymentType = 0;
    String ordre_sn;
    private String totalPrice;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment;
    }
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选择支付方式");
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            price.setText("328元");
            totalPrice = "328.00元";

        } else if (type == 2) {
            price.setText("188元");
            totalPrice = "188.00元";
            title.setText("6个月钻石会员");
        } else if (type == 3) {
            price.setText("98元");
            totalPrice = "98.00元";
            title.setText("季度钻石会员");
        }else {
            submit.setText("订单支付");
            String totolPrice = getIntent().getStringExtra("price");
            totalPrice = totolPrice+"元";
             ordre_sn = getIntent().getStringExtra("order_sn");
            price.setText(totolPrice+"元");
            title.setText("订单支付");
        }


        rxSbscription= RxBus.getInstance().toObserverable(MessageEvent.class)
                .subscribe(new Action1<MessageEvent>() {
                    @Override
                    public void call(MessageEvent message) {
                        int id = message.getStatus();
                        if (id == 0) {
                            // 支付成功，去确认
                            CommonUtils.startActWithData(PaymentActivity.this,PaymentSuccessActivity.class,"price",totalPrice);
                            finish();
                        } else {
                        }
                    }
                });


    }

    @OnClick({R.id.rl_weixin, R.id.rl_zhifubao, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_weixin:
                weixinIcon.setImageResource(R.mipmap.choice_normal_checked);
                zhifuboIcon.setImageResource(R.mipmap.choice_normal);
                paymentType = 1;
                break;
            case R.id.rl_zhifubao:
                paymentType = 2;
                weixinIcon.setImageResource(R.mipmap.choice_normal);
                zhifuboIcon.setImageResource(R.mipmap.choice_normal_checked);
                break;
            case R.id.submit:

                if (type ==4 ){
                    if (paymentType == 1){
                        WXGoodspayment(ordre_sn ,1);
                    }else{
                        Alipaypayment(ordre_sn, 1);
                    }
                }else{
                    creatVip(AndroidUtils.getAndroidId(this), getIntent().getIntExtra("type", 1));
                }
                break;
        }
    }


    //创建订单
    public void creatVip(String deviceId, int type) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(null).sendRequest().vipcreat(deviceId, type),
                new HttpResultCall<HttpResult<Risgter>, Risgter>() {
                    @Override
                    public void onResponse(Risgter result, String msg) {
//                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.TOKEN, result.order_sn);
                        if (paymentType == 1) {
                            WXpayment(result.order_sn, 2);
                        } else if (paymentType == 2) {
//                            alipaypayment(result.order_sn, 2);
                            Alipaypayment(result.order_sn, 2);
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }
                });
    }

    public void WXpayment(String id, int type) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(null).sendRequest().wxpaymentvip(AndroidUtils.getAndroidId(this), id, type),
                new HttpResultCall<HttpResult<WXPreOrderBean>, WXPreOrderBean>() {
                    @Override
                    public void onResponse(WXPreOrderBean result, String msg) {
                        WXPayUtils wxPayUtils = new WXPayUtils(PaymentActivity.this, result);
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }
                });
    }

    public void Alipaypayment(String id, int type) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(null).sendRequest().alipayaymentvip(AndroidUtils.getAndroidId(this), 1, id, type),
                new HttpResultCall<HttpResult<Risgter>, Risgter>() {
                    @Override
                    public void onResponse(Risgter result, String msg) {
//                        WXPayUtils wxPayUtils = new WXPayUtils(SuerOrderActivity.this, result);
                        //支付宝支付
                        AliPayUtils aliPayUtils = new AliPayUtils(PaymentActivity.this, mReflushListener);

                        String key  = new String(Base64.decode(result.key, Base64.DEFAULT));

                        aliPayUtils.pay(key);
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }
                });
    }

    //支付宝付款
    private ReflushListener mReflushListener = new ReflushListener() {

        @Override
        public void reflush(Object o) {
        }

        @Override
        public void reflush() {

        }

        @Override
        public void reflush(String tag, String des) {
            if ("0".equals(tag)) {

                // 支付成功，去确认
//                paySuccess = true;
//                T.showShort(PayNowActivity.this, "订单支付成功");
                CommonUtils.startActWithData(PaymentActivity.this,PaymentSuccessActivity.class,"price",totalPrice);
//                intent.putExtra(BaseConfig.IntentKey.ID,orderId);
//                //TODO  11月7日
//                intent.putExtra("payprice",payMoney.toString());
//                intent.putExtra("sumprice",sumMoney.toString());
//                intent.putExtra("paytime", System.currentTimeMillis()+"");
//                intent.putExtra("shopname",shopName);

//                startActivity(intent);
//                Intent databack = new Intent();
//                databack.putExtra(BaseConfig.IntentKey.DATA, 1);
//                setResult(Constant.Code.ORDER_PAY_RESULT_CODE, databack);
                finish();
            } else {
                //支付宝支付失败
//                paySuccess = false;
            }
        }
    };

    public void WXGoodspayment(String id, int type) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(getApplication()).sendRequest().wxpaymentvip(AndroidUtils.getAndroidId(this), id, type),
                new HttpResultCall<HttpResult<WXPreOrderBean>, WXPreOrderBean>() {
                    @Override
                    public void onResponse(WXPreOrderBean result, String msg) {
                        WXPayUtils wxPayUtils = new WXPayUtils(PaymentActivity.this, result);
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }
                });
    }

    public void AlipayGoodspayment(String id, int type) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(null).sendRequest().alipayaymentvip(AndroidUtils.getAndroidId(this), 1, id, type),
                new HttpResultCall<HttpResult<Risgter>, Risgter>() {
                    @Override
                    public void onResponse(Risgter result, String msg) {
//                        WXPayUtils wxPayUtils = new WXPayUtils(SuerOrderActivity.this, result);
                        mackToastLONG(msg, getApplicationContext());
                        //支付宝支付
                        AliPayUtils aliPayUtils = new AliPayUtils(PaymentActivity.this, mReflushListener);
                        aliPayUtils.pay(result.key);
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }
                });
    }

    private Subscription rxSbscription;
    //微信支付结果回调

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消订阅
        if (rxSbscription!=null&&!rxSbscription.isUnsubscribed()){
            rxSbscription.unsubscribe();
        }
    }
}








