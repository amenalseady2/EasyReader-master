package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.AddressAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.AddressModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.MyDialog;
import com.BlackDiamond2010.hzs.view.XRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyAddressActivity extends BaseActivity {

    @BindView(R.id.address_recycle)
    XRecycleView recycle;
    @BindView(R.id.add_newsaddress)
    TextView addNewsaddress;
    AddressAdapter mAdapter;
    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_address;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("地址管理");
        type = getIntent().getStringExtra("type");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置分割线
        recycle.setLayoutManager(layoutManager);

        recycle.setRefreshAndLoadMoreListener(new XRecycleView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
            }
        });

        recycle.setRefreshAndLoadMoreListener(new XRecycleView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                recycle.setReFreshComplete();
            }

            @Override
            public void onLoadMore() {
//                recyclerViewTopnews.setloadMoreDone();
                recycle.setReFreshComplete();
            }
        });
//        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void setListener() {

        mAdapter.mListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("onlinestore".equals(type)) {
                    Intent intent = new Intent();
                    intent.putExtra("addaddress", addressList.get(Integer.valueOf(v.getTag().toString())));
                    setResult(10005, intent);
                    finish();
                } else {
                    CommonUtils.startActWithData(MyAddressActivity.this, AddressEditActivity.class, "model", addressList.get(Integer.valueOf(v.getTag().toString())));
                }


            }
        };
        mAdapter.deleteListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = addressList.get(Integer.valueOf(v.getTag().toString())).id;
                MyDialog.getDialogAndshow(MyAddressActivity.this, new MyDialog.DialogCallBack() {
                    @Override
                    public void dialogSure() {
                        deleteAlter(id);
                    }

                    @Override
                    public void dialogCancle() {

                    }
                }, "确定删除该地址？", "确定", "取消", null);
            }
        };

        mAdapter.morenListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAlter(addressList.get(Integer.valueOf(v.getTag().toString())).id);

            }
        };


    }

    private void toAlter(String id) {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        dAddress(id, AndroidUtils.getAndroidId(this)),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object shopBeen, String msg) {
                        getData();

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

    private void deleteAlter(String id) {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        deleteAddress(id, AndroidUtils.getAndroidId(this)),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object shopBeen, String msg) {
                        getData();

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

    @OnClick(R.id.add_newsaddress)
    public void onViewClicked() {

        CommonUtils.startAct(MyAddressActivity.this, AddressEditActivity.class);
    }

    int page = 1;
    private List<AddressModel> addressList = new ArrayList<>();

    private void getData() {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        myAddress(AndroidUtils.getAndroidId(this)),
                new HttpResultCall<HttpResult<List<AddressModel>>, List<AddressModel>>() {


                    @Override
                    public void onResponse(List<AddressModel> shopBeen, String msg) {

                        addressList = shopBeen;
                        if (shopBeen == null || shopBeen.size() == 0) {
                            if (page == 1) {
                                mackToastLONG("您还未添加收货地址", MyAddressActivity.this);
                            }

                        } else {
                            if (mAdapter == null) {
                                mAdapter = new AddressAdapter(getApplicationContext(), shopBeen);
                                ;
                                setListener();
                                recycle.setAdapter(mAdapter);
                            } else {
                                if (page == 1) {
                                    mAdapter.setData(shopBeen);
                                } else {
                                    mAdapter.addData(shopBeen);
                                }
                            }
                        }
                        recycle.setloadMoreDone();
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
}
