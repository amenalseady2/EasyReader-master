package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.OrderAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderItem;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.view.XRecycleView;

import butterknife.BindView;

public class MyServerActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.orderlist)
    XRecycleView orderlist;
    @BindView(R.id.div)
    View div;
    @BindView(R.id.no_service)
    TextView noService;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_server;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("我的售后");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置分割线
        orderlist.setLayoutManager(layoutManager);

        orderlist.setRefreshAndLoadMoreListener(new XRecycleView.OnRefreshAndLoadMoreListener() {
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

        getData();

    }

    private int page = 1;
    OrderAdapter mAdapter;

    public void getData() {

        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        serverList(page, AndroidUtils.getAndroidId(MyServerActivity.this)),
                new HttpResultCall<HttpResult<MyOrderModel>, MyOrderModel>() {


                    @Override
                    public void onResponse(MyOrderModel shopBeen, String msg) {
                        if (shopBeen == null || shopBeen.list.size() == 0) {
                            if (page == 1) {
//                                mackToastLONG("暂时售后订单", MyServerActivity.this);
                                noService.setText("暂无售后订单");
                                noService.setVisibility(View.VISIBLE);
                            }

                        } else {
                            if (mAdapter == null) {
                                mAdapter = new OrderAdapter(MyServerActivity.this, shopBeen.list);
                                mAdapter.itemListener = new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        MyOrderItem model = (MyOrderItem) v.getTag();
                                        CommonUtils.startActWithData(MyServerActivity.this, RefundActivity.class, "model", model);
                                    }
                                };
                                orderlist.setAdapter(mAdapter);
                            } else {
                                if (page == 1) {
                                    mAdapter.setData(shopBeen.list);
                                } else {
                                    mAdapter.addData(shopBeen.list);
                                }
                            }
                        }

                        if (shopBeen.next == 0) {
                            orderlist.setloadMoreDone();
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        orderlist.setReFreshComplete();
                        dismissDialog();
                        super.onCompleted();
                    }
                });


    }
}
