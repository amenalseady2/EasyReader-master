package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.SureOrderAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.AddressModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MessageEvent;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderItem;
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
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;
import com.BlackDiamond2010.hzs.view.ListViewForScrollView;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;

import Decoder.BASE64Encoder;
import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class SuerOrderActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.goodslist)
    ListViewForScrollView goodslist;
    @BindView(R.id.shuoming)
    TextView shuoming;
    @BindView(R.id.weixin_icon)
    ImageView weixinIcon;
    @BindView(R.id.rl_weixin)
    RelativeLayout rlWeixin;
    @BindView(R.id.zhifubo_icon)
    ImageView zhifuboIcon;
    @BindView(R.id.rl_zhifubao)
    RelativeLayout rlZhifubao;
    @BindView(R.id.goods_recycle)
    ScrollView goodsRecycle;
    @BindView(R.id.shopcart_price)
    TextView shopcartPrice;
    @BindView(R.id.shopcart_topay)
    TextView shopcartTopay;
    @BindView(R.id.shopcart_price_relat)
    RelativeLayout shopcartPriceRelat;
    @BindView(R.id.shopcart_delect)
    TextView shopcartDelect;
    @BindView(R.id.info_rela)
    RelativeLayout infoRela;
    @BindView(R.id.to_pay_relative)
    RelativeLayout toPayRelative;
    ArrayList<GoodsModel> shopModelList;
    @BindView(R.id.goodsprice)
    TextView goodsprice;
    @BindView(R.id.yunfei)
    TextView yunfei;
    @BindView(R.id.paymentmoney)
    TextView paymentmoney;
    @BindView(R.id.beizhu)
    EditText beizhu;
    BigDecimal total;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_suer_order;
    }

    private Subscription rxSbscription;
    //微信支付结果回调

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消订阅
        if (rxSbscription != null && !rxSbscription.isUnsubscribed()) {
            rxSbscription.unsubscribe();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("确认订单");
        Bundle bundle = getIntent().getBundleExtra("bundle");
        shopModelList = (ArrayList<GoodsModel>) bundle.getSerializable("list");

        rxSbscription = RxBus.getInstance().toObserverable(MessageEvent.class)
                .subscribe(new Action1<MessageEvent>() {
                    @Override
                    public void call(MessageEvent message) {
                        int id = message.getStatus();
                        if (id == 0) {
                            MyOrderItem item = new MyOrderItem();
                            item.order_sn = ordersn;
                            item.status = 1;
                            CommonUtils.startActWithData(SuerOrderActivity.this, OrderDetailActivity.class, "bean", item);
                            SuerOrderActivity.this.finish();
                        } else {
                        }
                    }
                });

        paymentmoney.setText("￥"+getIntent().getDoubleExtra("total",0));

//        //加数据
//        MyOrderItem item = new MyOrderItem();
//        item.order_sn = "NO11201711221109052548";
//        item.status = 1;
//        CommonUtils.startActWithData(SuerOrderActivity.this, OrderDetailActivity.class, "bean", item);
//        finish();


        setData(shopModelList);
    }

    StringBuffer bf;

    private void setData(ArrayList<GoodsModel> shopModelList) {
//        goodslist.setAdapter(new OrderGoodsAdapter((shopModelList)));

        bf = new StringBuffer();
        for (int i = 0; i < shopModelList.size(); i++) {
            GoodsModel model = shopModelList.get(i);
            if (i == shopModelList.size() - 1) {
                bf.append(model.id);
            } else {
                bf.append(model.id + ",");
            }
            total = new BigDecimal(0.00f + "");
            total = total.add(new BigDecimal(model.price + "")
                    .multiply(new BigDecimal(model.num + "")));
            goodsprice.setText("￥" + total.toString());
        }

        goodslist.setAdapter(new SureOrderAdapter(shopModelList));

        addressId = SHPUtils.getParame(getApplicationContext(), SHPUtils.ADDRESSID);
        if (addressId == null) {
            phone.setText("添加地址");
        } else {
            getData(Base64());
            name.setText(SHPUtils.getParame(getApplicationContext(), SHPUtils.NAME));
            address.setText("收货地址： " + SHPUtils.getParame(getApplicationContext(), SHPUtils.ADDRESS));
            phone.setText(SHPUtils.getParame(getApplicationContext(), SHPUtils.PHONE));
        }
    }

    private String addressId;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            AddressModel receiptAddress = (AddressModel) data
                    .getSerializableExtra("addaddress");
            if (receiptAddress != null) {
                addressId = receiptAddress.id;
                name.setText(receiptAddress.name);
                address.setText("收货地址： " + receiptAddress.address + receiptAddress.info);
                phone.setText(receiptAddress.phone);
                getData(Base64());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private int paymentType = 0;

    @OnClick({R.id.ll_address, R.id.rl_weixin, R.id.rl_zhifubao, R.id.shopcart_topay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                Intent intent = new Intent(this, MyAddressActivity.class);
                intent.putExtra("type", "onlinestore");
                startActivityForResult(intent, 10001);
                break;
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
            case R.id.shopcart_topay:
                if (StringUtil.isEmpty(addressId)) {
                    mackToastLONG("填写收货人信息", getApplicationContext());
                    return;

                }

                if (paymentType == 0) {
                    mackToastLONG("请选择支付方式", getApplicationContext());
                    return;

                }

                Gson gson = new Gson();
                String info = gson.toJson(shopModelList);
                BASE64Encoder encoder = new BASE64Encoder();
                byte b[] = info.getBytes();
                String yy = encoder.encode(b);

//                getData(yy);
                if (paymentType == 1) {
                    creatorder(Base64(), 1, beizhu.getText().toString());
                } else if (paymentType == 2) {
                    creatorder(Base64(), 2, beizhu.getText().toString());
                }

                break;
        }
    }


    private String Base64() {
        Gson gson = new Gson();
        String info = gson.toJson(shopModelList);
        BASE64Encoder encoder = new BASE64Encoder();
        byte b[] = info.getBytes();
        return encoder.encode(b);
    }

    //预览
    private void getData(String info) {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        submitOrder(addressId, info, bf.toString(), AndroidUtils.getAndroidId(this)),
                new HttpResultCall<HttpResult<MyOrderItem>, MyOrderItem>() {


                    @Override
                    public void onResponse(MyOrderItem shopBeen, String msg) {
                        paymentmoney.setText("￥" + shopBeen.total);
                        shopcartPrice.setText("￥" + shopBeen.total);
                        yunfei.setText("￥" + shopBeen.freight);
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                    }
                });
    }

    public String ordersn;

    //type 1 weixin 2alipay
    private void creatorder(String info, int type, String beizhu) {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        creatorder(type, beizhu, addressId, info, bf.toString(), AndroidUtils.getAndroidId(this)),
                new HttpResultCall<HttpResult<Risgter>, Risgter>() {


                    @Override
                    public void onResponse(Risgter shopBeen, String msg) {
                        ordersn = shopBeen.order_sn;
                        if (paymentType == 1) {
                            WXpayment(ordersn, 1);
                        } else {
                            Alipaypayment(ordersn, 1);
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    public void WXpayment(String id, int type) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(null).sendRequest().wxpaymentvip(AndroidUtils.getAndroidId(this), id, type),
                new HttpResultCall<HttpResult<WXPreOrderBean>, WXPreOrderBean>() {
                    @Override
                    public void onResponse(WXPreOrderBean result, String msg) {
                        WXPayUtils wxPayUtils = new WXPayUtils(SuerOrderActivity.this, result);

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
                        AliPayUtils aliPayUtils = new AliPayUtils(SuerOrderActivity.this, mReflushListener);


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
                MyOrderItem item = new MyOrderItem();
                item.order_sn = ordersn;
                item.status = 1;
                CommonUtils.startActWithData(SuerOrderActivity.this, OrderDetailActivity.class, "bean", item);
                finish();
            } else {
                //支付宝支付失败
//                paySuccess = false;
            }
        }
    };
}
