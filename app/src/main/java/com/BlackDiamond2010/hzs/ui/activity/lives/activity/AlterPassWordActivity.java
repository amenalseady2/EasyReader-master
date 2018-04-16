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

public class AlterPassWordActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.old)
    EditText old;
    @BindView(R.id.new_password)
    EditText newPassword;
    @BindView(R.id.sure_password)
    EditText surePassword;
    @BindView(R.id.sure)
    TextView sure;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_pass_word;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("修改密码");
    }

    private void login(String older,String newpass) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().alterPassword(
                AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext()),
                older,
               newpass),
                new HttpResultCall<HttpResult<Object>, Object>() {

                    @Override
                    public void onResponse(Object result, String msg) {
                        mackToastLONG(msg, AlterPassWordActivity.this);
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
        String oldStr = old.getText().toString();
        if (TextUtils.isEmpty(oldStr)) {
            mackToastLONG("请输入原始密码", this);
            return;
        }
        String nStr = newPassword.getText().toString();
        if (TextUtils.isEmpty(nStr)) {
            mackToastLONG("请输入原始密码", this);
            return;
        }

        String sStr = surePassword.getText().toString();
        if (TextUtils.isEmpty(sStr)) {
            mackToastLONG("请输入原始密码", this);
            return;
        }

        if (!sStr.equals(nStr)) {
            mackToastLONG("两次密码不一致", this);
            return;
        }

        login(oldStr,sStr);


    }
}
