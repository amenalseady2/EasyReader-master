package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.RecordAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.VIPModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.view.XRecycleView;

import butterknife.BindView;

//购买记录 vip
public class RecordActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.record_recycle)
    XRecycleView recordRecycle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("购买记录");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置分割线
        recordRecycle.setLayoutManager(layoutManager);


        recordRecycle.setRefreshAndLoadMoreListener(new XRecycleView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                page =1;
                getData();
            }

            @Override
            public void onLoadMore() {
//                recyclerViewTopnews.setloadMoreDone();
                ++page;
                getData();
            }
        });
        getData();
    }

    int page = 1;
    RecordAdapter mAdapter;

    public void getData() {
showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        viprecord(page,AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<VIPModel>, VIPModel>() {


                    @Override
                    public void onResponse(VIPModel shopBeen, String msg) {

                        if (shopBeen == null || shopBeen.list.size() == 0) {
                            if (page ==1){
                                mackToastLONG("暂时没有购买记录",RecordActivity.this);
                            }

                        } else {
                            if (mAdapter == null){
                                mAdapter = new RecordAdapter(RecordActivity.this,shopBeen.list);

                                recordRecycle.setAdapter(mAdapter);
                            }else{
                                if (page == 1) {
                                    mAdapter.setData(shopBeen.list);
                                } else {
                                    mAdapter.addData(shopBeen.list);
                                }
                            }




                        }

                        if (shopBeen.next == 0) {
                            recordRecycle.setloadMoreDone();
                        }



                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        recordRecycle.setReFreshComplete();
                        dismissDialog();
                    }
                });

    }
}
