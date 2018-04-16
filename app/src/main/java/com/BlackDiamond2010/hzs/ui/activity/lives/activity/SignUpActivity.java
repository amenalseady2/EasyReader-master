package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LiveDetailModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;

import butterknife.BindView;
import butterknife.OnClick;

//报名
public class SignUpActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.phonenum)
    EditText phonenum;
    @BindView(R.id.xingming)
    EditText xingming;
    @BindView(R.id.companyname)
    EditText companyname;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.zhiwu)
    EditText zhiwu;
    @BindView(R.id.sure)
    TextView sure;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_up;
    }
    LiveDetailModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("报名");
        model = (LiveDetailModel) getIntent().getSerializableExtra("id");
        title.setText(model.title);


        username.setText(SHPUtils.getParame(getApplicationContext(),SHPUtils.NICKNAME));
        phonenum.setText(SHPUtils.getParame(getApplicationContext(),SHPUtils.PHONE));

        if (SHPUtils.getParame(getApplicationContext(),SHPUtils.JOB) != null){
            zhiwu.setText(SHPUtils.getParame(getApplicationContext(),SHPUtils.JOB));
        }

        if (SHPUtils.getParame(getApplicationContext(),SHPUtils.CONPANY) != null){
            companyname.setText(SHPUtils.getParame(getApplicationContext(),SHPUtils.CONPANY));
        }

        if (SHPUtils.getParame(getApplicationContext(),SHPUtils.REAL_NAME) != null){
            xingming.setText(SHPUtils.getParame(getApplicationContext(),SHPUtils.REAL_NAME));
        }

    }

    //获取信息

    public void alterUserInfo() {

        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().signup(zhiwu.getText().toString(),

                model.id, username.getText().toString(), phonenum.getText().toString(), xingming.getText().toString(), companyname.getText().toString(), AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext())),
                new HttpResultCall<HttpResult<Object>, Object>() {

                    @Override
                    public void onResponse(Object result, String msg) {

                     mackToastLONG(msg,getApplicationContext());
                        setResult(999,new Intent());
                        finish();

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

    @OnClick(R.id.sure)
    public void onViewClicked() {
        alterUserInfo();
    }
}
