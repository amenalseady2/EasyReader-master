package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Risgter;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.utils.DateUtil;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class BuyVipActivity extends BaseActivity {

    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.userhead)
    CircleImageView userhead;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.year_icon)
    ImageView yearIcon;
    @BindView(R.id.liu_icon)
    ImageView liuIcon;
    @BindView(R.id.jidu_icon)
    ImageView jiduIcon;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.rl_year)
    RelativeLayout rlYear;
    @BindView(R.id.isvip)
    TextView isvip;
    private int payType = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_vip;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的VIP");
        tvRight.setText("购买记录");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startAct(BuyVipActivity.this, RecordActivity.class);
            }
        });

        GlideUtils.loadImage(3, SHPUtils.getParame(getApplicationContext(), SHPUtils.HEAD), userhead);
        username.setText(SHPUtils.getParame(getApplicationContext(), SHPUtils.NICKNAME));
//        getData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        vipDetail(AndroidUtils.getAndroidId(this)),
                new HttpResultCall<HttpResult<Risgter>, Risgter>() {


                    @Override
                    public void onResponse(Risgter shopBeen, String msg) {
                        long endtime = Long.parseLong(shopBeen.vip_end);
                        long now = System.currentTimeMillis();
                        if (endtime > now / 1000) {
//                            isvip.setText("您是vip会员");
                            isvip.setText("有效期："+DateUtil.longToDateTime(endtime,"yyyy-MM-dd"));
                        } else {
                            isvip.setText("您还不是vip会员");
                        }

                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }
                });


    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (payType == 0) {
            mackToastLONG("请选择会员类型", BuyVipActivity.this);
            return;
        }

        CommonUtils.startActWithData(BuyVipActivity.this, PaymentActivity.class, "type", payType);
    }

    @OnClick({R.id.rl_year, R.id.rl_liu, R.id.rl_jidu, R.id.vip_shuom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vip_shuom:

                Intent intent = new Intent(getApplicationContext(), XieyiActivity.class);
                intent.putExtra("des", "钻石VIP会员可以观看钻石路演APP内所有付费及免费直播及录播项目内容，包含路演兵法、路演英雄、钻石沙龙以及钻石大咔在线直播内容。\n" +
                        "且在购买文创商城商品时享受一定的折扣。");
                intent.putExtra("type", 1);

                startActivity(intent);
                break;
            case R.id.rl_year:
                payType = 1;
                yearIcon.setImageResource(R.mipmap.choice_normal_checked);
                liuIcon.setImageResource(R.mipmap.choice_normal);
                jiduIcon.setImageResource(R.mipmap.choice_normal);
                break;
            case R.id.rl_liu:
                payType = 2;
                yearIcon.setImageResource(R.mipmap.choice_normal);
                liuIcon.setImageResource(R.mipmap.choice_normal_checked);
                jiduIcon.setImageResource(R.mipmap.choice_normal);
                break;
            case R.id.rl_jidu:
                payType = 3;
                yearIcon.setImageResource(R.mipmap.choice_normal);
                liuIcon.setImageResource(R.mipmap.choice_normal);
                jiduIcon.setImageResource(R.mipmap.choice_normal_checked);
                break;
        }
    }
}
