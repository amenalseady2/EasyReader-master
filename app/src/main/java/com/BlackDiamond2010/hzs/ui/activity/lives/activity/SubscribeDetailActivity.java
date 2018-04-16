package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.SubscirbeLiveAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LiveModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.SubscribeModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.CircleImageView;
import com.BlackDiamond2010.hzs.view.XRecycleView;

import butterknife.BindView;
import butterknife.OnClick;

public class SubscribeDetailActivity extends BaseActivity {

    @BindView(R.id.back_icon)
    ImageView backIcon;
    @BindView(R.id.img)
    CircleImageView img;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    @BindView(R.id.rl_dingyue)
    RelativeLayout rlDingyue;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.live_recycle)
    XRecycleView liveRecycle;
    SubscribeModel model;
    SubscirbeLiveAdapter mAdapter;
    @BindView(R.id.jiadingyue)
    TextView jiadingyue;
    @BindView(R.id.yidingyue)
    TextView yidingyue;
    @BindView(R.id.dingyue_btn)
    RelativeLayout dingyueBtn;

    private int isSu;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_subscribe_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStatusBar(R.color.red, false, R.color.colorWhite);
        model = (SubscribeModel) getIntent().getSerializableExtra("model");
        isSu = getIntent().getIntExtra("isSu", 1);
        if (isSu == 0) {
            //没订阅
            jiadingyue.setVisibility(View.VISIBLE);
            yidingyue.setVisibility(View.GONE);
        }else {
            jiadingyue.setVisibility(View.GONE);
            yidingyue.setVisibility(View.VISIBLE);
        }

        GlideUtils.loadImage(3, model.avatar, img);
        name.setText(model.name);
        tvFans.setText(model.fans + "个粉丝");
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type",isSu);

                setResult(999,intent);
                finish();
            }
        });

        getData("%");


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                page = 1;
                getData(s.toString());
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置分割线
        liveRecycle.setLayoutManager(layoutManager);

    }

    private int page = 1;

    public void getData(String keyword) {

        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        pubshSubscribe(model.id + "", keyword, page, AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<LiveModel>, LiveModel>() {


                    @Override
                    public void onResponse(LiveModel shopBeen, String msg) {
                        if (shopBeen == null || shopBeen.list.size() == 0) {
                            if (page == 1) {
                                mackToastLONG("没有直播信息", SubscribeDetailActivity.this);
                            }

                        } else {
                            if (mAdapter == null) {
                                mAdapter = new SubscirbeLiveAdapter(getApplicationContext(), shopBeen.list);
                                ;
                                mAdapter.mListener = new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String[] str = v.getTag().toString().split(",");
                                        int status = Integer.valueOf(str[0]);
                                        if (status == 0) {
                                            CommonUtils.startActWithData(SubscribeDetailActivity.this, LiveTrailerDetailActivity.class, "id", str[1]);
                                        } else if (status == 1) {
                                            CommonUtils.startActWithData(SubscribeDetailActivity.this, LiveDetailActivity.class, "id", str[1]);
                                        } else {
                                            CommonUtils.startActWithData(SubscribeDetailActivity.this, LiveReviewDetailActivity.class, "id", str[1]);
                                        }


                                    }
                                };
                                liveRecycle.setAdapter(mAdapter);
                            } else {
                                if (page == 1) {
                                    mAdapter.setData(shopBeen.list);
                                } else {
                                    mAdapter.addData(shopBeen.list);
                                }
                            }
                        }

                        if (shopBeen.next == 0) {
                            liveRecycle.setloadMoreDone();
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                        liveRecycle.setReFreshComplete();
                        super.onCompleted();
                    }
                });


    }

    @OnClick(R.id.dingyue_btn)
    public void onViewClicked() {
        addSub();
    }


    public void addSub() {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        addSubscribe(model.id, AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<Object>, Object>() {
                    @Override
                    public void onResponse(Object shopBeen, String msg) {
                        mackToastLONG(msg, getApplicationContext());
                        if ("已订阅".equals(msg)) {
                            jiadingyue.setVisibility(View.GONE);
                            yidingyue.setVisibility(View.VISIBLE);
                            isSu = 1;
                        } else {isSu = 0 ;
                            jiadingyue.setVisibility(View.VISIBLE);
                            yidingyue.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                        super.onCompleted();
                    }
                });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("type",isSu);

        setResult(999,intent);
        finish();

        super.onBackPressed();
    }
}
