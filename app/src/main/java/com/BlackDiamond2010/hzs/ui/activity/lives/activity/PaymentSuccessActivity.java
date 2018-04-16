package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class PaymentSuccessActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.price)
    TextView price;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment_success;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("支付成功");
        price.setText("支付成功"+getIntent().getStringExtra("price"));

        SHPUtils.saveParame(this,SHPUtils.ISVIP,"1");


    }

    @OnClick(R.id.finsh)
    public void onViewClicked() {
//        CommonUtils.startAct(PaymentSuccessActivity.this, LiveMainActivity.class);
        finish();
    }
}
