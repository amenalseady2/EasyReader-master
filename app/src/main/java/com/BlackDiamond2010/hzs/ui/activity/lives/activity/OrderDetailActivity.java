package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.OrderGoodsAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MessageEvent;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderDeatilModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderItem;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.OrderDetailModel;
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
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;
import com.BlackDiamond2010.hzs.view.ListViewForScrollView;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class OrderDetailActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.info2)
    TextView info2;
    @BindView(R.id.top_price)
    TextView topPrice;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.rl_userinfo)
    RelativeLayout rlUserinfo;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.goodlist)
    ListViewForScrollView goodlist;
    @BindView(R.id.tv_orderid)
    TextView tvOrderid;
    @BindView(R.id.tv_creattime)
    TextView tvCreattime;
    @BindView(R.id.payway)
    TextView payway;
    @BindView(R.id.kuaidi_way)
    TextView kuaidiWay;
    @BindView(R.id.shop_phone)
    TextView shopPhone;
    @BindView(R.id.goods_totalprice)
    TextView goodsTotalprice;
    @BindView(R.id.yunfei)
    TextView yunfei;
    @BindView(R.id.total_topay)
    TextView totalTopay;
    @BindView(R.id.button1)
    TextView button1;
    @BindView(R.id.button2)
    TextView button2;
    @BindView(R.id.button3)
    TextView button3;
    @BindView(R.id.rl_bottom)
    RelativeLayout bottom;
    @BindView(R.id.tv_shopphonenum)
    TextView tv_shopphonenum;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.bottom_line)
    View bottomLine;
    private int status;
    //    MyOrderItem model;
    private Subscription rxSbscription;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("订单详情");
//        goodlist.setAdapter(new OrderGoodsAdapter());
        MyOrderItem model = (MyOrderItem) getIntent().getSerializableExtra("bean");
        status = model.status;
        getData(model.order_sn);
        ivRight.setImageResource(R.mipmap.kefu_dingdandetail);
        ivRight.setVisibility(View.VISIBLE);


        rxSbscription = RxBus.getInstance().toObserverable(MessageEvent.class)
                .subscribe(new Action1<MessageEvent>() {
                    @Override
                    public void call(MessageEvent message) {
                        status = 2;
                        getData(mShopBeen.detail.id);
                    }
                });
    }


    private void setData(MyOrderDeatilModel shopBeen) {
        OrderDetailModel model = shopBeen.detail;
        if (status == 0) {
            tvStatus.setText("等待付款");
            button1.setText("去支付");
            button2.setText("取消订单");
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);

            info2.setText("剩余时间：" + model.end_time);
            topPrice.setText("需支付：" + model.total_price);

        } else if (status == 1) {
            tvStatus.setText("等待发货");
            info2.setVisibility(View.GONE);
            topPrice.setVisibility(View.GONE);
            button1.setText("提醒发货");

            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.GONE);

        } else if (status == 2) {
            info2.setText("快递单号：" + model.express_sn);
            tvStatus.setText("待收货");
            button1.setText("确定收货");
            button2.setText("查看物流");
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
        } else if (status == 3) {
            tvStatus.setText("完成");
            if (model.is_review != 1) {
                button1.setText("晒单评价");
                button2.setText("申请售后");
                button3.setText("删除订单");
                button1.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
                button3.setVisibility(View.VISIBLE);
            } else {
                button3.setText("删除订单");
                button3.setVisibility(View.VISIBLE);

            }
        }
        name.setText(model.name);
        phone.setText(model.phone);
        address.setText(model.address);

        tvOrderid.setText("订单编号：" + model.order_sn);
        tvCreattime.setText("下单时间：" + model.create_at);
        if (model.pay_type == 1) {
            payway.setText("支付方式：微信支付");
        } else {
            payway.setText("支付方式：支付宝支付");
        }
        if (StringUtil.isEmpty(model.express_name)){
            kuaidiWay.setText("配送方式：暂无配送方式" );
        }else{
            kuaidiWay.setText("配送方式：" + model.express_name);
        }

        tv_shopphonenum.setText(model.shop_phone);

        BigDecimal total = new BigDecimal(0.00f + "");
        //算总价
        total = total.add(new BigDecimal(model.freight)
                .multiply(new BigDecimal(1 + "")));
        total = total.add(new BigDecimal(model.total_price)
                .multiply(new BigDecimal(1 + "")));

        goodsTotalprice.setText("￥" + model.total_price);
        yunfei.setText("￥" + model.freight);
        totalTopay.setText("合计：￥" + total.toString());


        goodlist.setAdapter(new OrderGoodsAdapter(model.product_list));

    }

    MyOrderDeatilModel mShopBeen;

    public void getData(String id) {

        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        orderDetail(id, AndroidUtils.getAndroidId(this)),
                new HttpResultCall<HttpResult<MyOrderDeatilModel>, MyOrderDeatilModel>() {


                    @Override
                    public void onResponse(MyOrderDeatilModel shopBeen, String msg) {
                        mShopBeen = shopBeen;
                        setData(shopBeen);
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        dismissDialog();
                    }
                });


    }

    /**
     * android 授权
     * */
    private  final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 888;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                call(Intent.ACTION_CALL,"4001782998");
            } else
            {
                // Permission Denied
                Toast.makeText(OrderDetailActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void call(String action,String phoneNum){
        if(phoneNum!=null&&phoneNum.trim().length()>0){
            //这里"tel:"+电话号码 是固定格式，系统一看是以"tel:"开头的，就知道后面应该是电话号码。
            Intent intent = new Intent(action, Uri.parse("tel:" + phoneNum.trim()));
            startActivity(intent);//调用上面这个intent实现拨号
        }else{
            Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3,R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_right:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_CONTACTS)) {
                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    }//申请权限
                } else {
                    call(Intent.ACTION_CALL,"4001782998");
                }

                break;
            case R.id.button1:




                if (status == 0) {

                    if (mShopBeen.detail.pay_type == 1) {
                        WXGoodspayment(mShopBeen.detail.order_sn, 1);
                    } else {
                        Alipaypayment(mShopBeen.detail.order_sn, 1);
                    }
                } else if (status == 1) {
                    //提醒收货
                    mackToastLONG("商家正快速为你出货请耐心等待", getApplicationContext());
                } else if (status == 2) {
                    // 确认收货
                    cancleOrder(mShopBeen.detail.id, "complete");
                } else if (status == 3) {


                    MyOrderItem item = new MyOrderItem();
                    item.product_list = mShopBeen.detail.product_list;
                    item.id = mShopBeen.detail.id;

                    CommonUtils.startActWithData(OrderDetailActivity.this, EvaluateActivity.class, "id", item);
                }

                break;
            case R.id.button2:
                if (status == 0) {

                    cancleOrder(mShopBeen.detail.id, "cancel");
                } else if (status == 1) {
                    mackToastLONG("商家正快速为你出货请耐心等待", getApplicationContext());
                } else if (status == 2) {
                    //查看物流
                    CommonUtils.startActWithData(OrderDetailActivity.this, LogisticsActivity.class, "id", mShopBeen.detail.id);
                } else if (status == 3) {


                    //申请售后

                    MyOrderItem item = new MyOrderItem();
                    item.product_list = mShopBeen.detail.product_list;
                    item.total_price = mShopBeen.detail.total_price;

                    item.id = mShopBeen.detail.id;
                    int count = 0;
                    for (int i = 0; i < item.product_list.size(); i++) {
                        count = count + item.product_list.get(i).num;
                    }
                    item.product_count = count;

                    CommonUtils.startActWithData(OrderDetailActivity.this, ShenQingRefundActivity.class, "id", item);
                }
                break;
            case R.id.button3:
                if (status == 0) {

                    //删除订单
                    cancleOrder(mShopBeen.detail.id, "delete");
                }
                break;
        }
    }

    public void WXGoodspayment(String id, int type) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(this).sendRequest().wxpaymentvip(AndroidUtils.getAndroidId(this), id, type),
                new HttpResultCall<HttpResult<WXPreOrderBean>, WXPreOrderBean>() {
                    @Override
                    public void onResponse(WXPreOrderBean result, String msg) {
                        WXPayUtils wxPayUtils = new WXPayUtils(OrderDetailActivity.this, result);
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
        addMainSubscription(HttpUtil.getInstance(this).sendRequest().alipayaymentvip(AndroidUtils.getAndroidId(this), 1, id, type),
                new HttpResultCall<HttpResult<Risgter>, Risgter>() {
                    @Override
                    public void onResponse(Risgter result, String msg) {
//                        WXPayUtils wxPayUtils = new WXPayUtils(SuerOrderActivity.this, result);
                        //支付宝支付
                        AliPayUtils aliPayUtils = new AliPayUtils(OrderDetailActivity.this, mReflushListener);
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
                status = 1;
                getData(mShopBeen.detail.id);
            } else {
                //支付宝支付失败
//                paySuccess = false;
            }
        }
    };

    private void cancleOrder(String orderId, String type) {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        cancleOrder(orderId, type, AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext())),
                new HttpResultCall<HttpResult<Object>, Object>() {

                    @Override
                    public void onResponse(Object result, String msg) {
                        getData(mShopBeen.detail.id);
                        //TODO setResult
//                        status = 3;
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        dismissDialog();
                    }
                });
    }
}
