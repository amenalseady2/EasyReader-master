package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.EvaluateActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.LogisticsActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.OrderDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.RefundActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.OrderAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MessageEvent;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderItem;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderModel;
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
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;
import com.BlackDiamond2010.hzs.view.XRecycleView;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ASUS on 2017/10/10.   预告直播
 */

public class BePaymentOrderFragment extends BaseFragment {

    @BindView(R.id.recycle)
    XRecycleView recycle;
    private int page = 1;

    private OrderAdapter mAdapter;
    private Subscription rxSbscription;

    @Override
    protected void loadData() {
        setState(AppConstants.STATE_SUCCESS);
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));


        getData();

        recycle.setRefreshAndLoadMoreListener(new XRecycleView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData();
            }

            @Override
            public void onLoadMore() {
                ++page;
                getData();
            }
        });

        rxSbscription= RxBus.getInstance().toObserverable(MessageEvent.class)
                .subscribe(new Action1<MessageEvent>() {
                    @Override
                    public void call(MessageEvent message) {
                        getData();
                    }
                });


    }

    private void  setListener(){

        mAdapter.itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOrderItem model = (MyOrderItem) v.getTag();
                CommonUtils.startActWithData(getActivity(), OrderDetailActivity.class,"bean",model);
            }
        };
        mAdapter.btnListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] tag = v.getTag().toString().split(",");
                int status = Integer.valueOf(tag[2]);
                if (status == 0){

                    if ("1".equals(tag[1])){
                        // 支付
                        // 支付

                        String snNum = tag[3];
                        if (Integer.valueOf(tag[4]) == 1){
                            //微信支付
                            WXGoodspayment(snNum, 1);
                        }else {
                            //支付宝支付
                            Alipaypayment(snNum, 1);
                        }
                    }else {
                        //取消订单
                        cancleOrder(tag[0],"cancel");
                    }
                }else if(status == 1){
                    //提醒收货
                }else if(status == 2){
                    if ("1".equals(tag[1])){
                        // 确认收货
                        cancleOrder(tag[0],"complete");
                    }else {
                        //查看物流
                        CommonUtils.startActWithData(getActivity(), LogisticsActivity.class,"id",tag[0]);
                    }
                }else if(status == 3){
                    if ("1".equals(tag[1])){
                        //去评价
                        CommonUtils.startActWithData(getActivity(), EvaluateActivity.class,"id",tag[0]);
                    }else  if ("2".equals(tag[1])){
                        //申请售后
                        CommonUtils.startActWithData(getActivity(), RefundActivity.class,"id",tag[0]);
                    }else  if ("3".equals(tag[1])){
                        //删除订单
                    }
                }

            }
        };}


    public void WXGoodspayment(String id, int type) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(getContext()).sendRequest().wxpaymentvip(AndroidUtils.getAndroidId(getContext()), id, type),
                new HttpResultCall<HttpResult<WXPreOrderBean>, WXPreOrderBean>() {
                    @Override
                    public void onResponse(WXPreOrderBean result, String msg) {
                        WXPayUtils wxPayUtils = new WXPayUtils(getActivity(), result);
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
        addMainSubscription(HttpUtil.getInstance(getContext()).sendRequest().alipayaymentvip(AndroidUtils.getAndroidId(getContext()), 1, id, type),
                new HttpResultCall<HttpResult<Risgter>, Risgter>() {
                    @Override
                    public void onResponse(Risgter result, String msg) {
//                        WXPayUtils wxPayUtils = new WXPayUtils(SuerOrderActivity.this, result);
                        //支付宝支付
                        AliPayUtils aliPayUtils = new AliPayUtils(getActivity(), mReflushListener);
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
                getData();
            } else {
                //支付宝支付失败
//                paySuccess = false;
            }
        }
    };





    public void getData() {

//        showLoadingDialog();
        String token = SHPUtils.getParame(getContext(), SHPUtils.TOKEN);
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        myOrderList( AndroidUtils.getAndroidId(getContext()), page,0),
                new HttpResultCall<HttpResult<MyOrderModel>, MyOrderModel>() {


                    @Override
                    public void onResponse(MyOrderModel shopBeen, String msg) {
                        if (shopBeen == null || shopBeen.list.size() == 0) {
                            if (page ==1){
                                mackToastLONG("暂时没有订单",getContext());

                                if (mAdapter!= null){
                                    mAdapter.clearAll();
                                }
                            }

                        } else {
                            if (mAdapter == null){
                                mAdapter = new OrderAdapter(getContext(),shopBeen.list);
                                setListener();
                                recycle.setAdapter(mAdapter);
                            }else{
                                if (page == 1) {
                                    mAdapter.setData(shopBeen.list);
                                } else {
                                    mAdapter.addData(shopBeen.list);
                                }
                            }
                        }

                        if (shopBeen.next == 0) {
                            recycle.setloadMoreDone();
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        recycle.setReFreshComplete();
                        super.onCompleted();
                    }
                });


    }


    @Override
    protected int getLayoutId() {
        return R.layout.live_re;
    }

    @Override
    protected void initView() {



    }

    @Override
    protected void initInject() {

    }

    private void cancleOrder(String orderId,String type){
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        cancleOrder(orderId,type,AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext())),
                new HttpResultCall<HttpResult<Object>, Object>() {

                    @Override
                    public void onResponse(Object result, String msg) {
                        mackToastLONG(msg,getContext());
                        page = 1;
                        getData();

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
