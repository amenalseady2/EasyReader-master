package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class FindPassWord2Activity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.et_newpassword)
    EditText etNewpassword;
    @BindView(R.id.et_surepassword)
    EditText etSurepassword;
    @BindView(R.id.submit)
    TextView submit;
    private String phoneStr, codeStr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_words;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("找回密码");
        phoneStr = getIntent().getStringExtra("phone");
        codeStr = getIntent().getStringExtra("code");

    }

    @OnClick(R.id.submit)
    public void onViewClicked() {

        String nStr = etNewpassword.getText().toString();
        if (TextUtils.isEmpty(nStr)) {
            mackToastLONG("正确的密码格式", this);
            return;
        }

        String sStr = etSurepassword.getText().toString();
        if (TextUtils.isEmpty(sStr)) {
            mackToastLONG("正确的密码格式", this);
            return;
        }

        if (!sStr.equals(nStr)) {
            mackToastLONG("两次密码不一致", this);
            return;
        }

        find(AndroidUtils.getAndroidId(getApplicationContext()),sStr);
    }

    //  獲取验证码
    public void find(String devieId, String password) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                findPassword(devieId, phoneStr,password, codeStr), new HttpResultCall<HttpResult<Object>, Object>() {
            @Override
            public void onResponse(Object result, String msg) {
                mackToastLONG(msg, FindPassWord2Activity.this);
                finish();
            }

            @Override
            public void onErr(String err, int status) {
                mackToastLONG(err, FindPassWord2Activity.this);
            }

            @Override
            public void onCompleted() {
                dismissDialog();
            }

        });
    }
}
