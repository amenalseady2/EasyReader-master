package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.SubscribeAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyDingYueModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.SubscribeModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.view.XRecycleView;

import butterknife.BindView;

//我的订阅
public class MySubscribeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.recycle)
    XRecycleView recycle;
    private SubscribeAdapter mAdapter;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_subscribe;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的订阅");
        recycle.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));


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
        getData();
    }



    public void getData() {

        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        mySubscribe(page, AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<MyDingYueModel>, MyDingYueModel>() {


                    @Override
                    public void onResponse(MyDingYueModel shopBeen, String msg) {
                        if (shopBeen == null || shopBeen.list.size() == 0) {
                            if (page == 1) {
                                mackToastLONG("暂时没有添加订阅", MySubscribeActivity.this);
                                if (mAdapter != null) {
                                    mAdapter.clearAll();
                                    recycle.setVisibility(View.GONE);
                                } else {

                                }
                            }

                        } else {
                            if (mAdapter == null) {
                                mAdapter = new SubscribeAdapter(getApplicationContext(), shopBeen.list);
                                ;
                                mAdapter.itemListener = new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SubscribeModel model = (SubscribeModel) v.getTag();
                                        CommonUtils.startActWithDataForResult(MySubscribeActivity.this, SubscribeDetailActivity.class, "model", model,1001);
                                    }
                                };
                                recycle.setAdapter(mAdapter);
                            } else {
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
                        dismissDialog();
                        recycle.setReFreshComplete();
                        super.onCompleted();
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (data.getIntExtra("type",1) == 0){
                page = 1;
                getData();
            }
        }
    }
}
