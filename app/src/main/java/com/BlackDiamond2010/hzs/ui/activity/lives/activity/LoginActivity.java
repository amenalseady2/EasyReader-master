package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.LiveMainActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LoginModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Risgter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.UserInfo;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.forgetpassword)
    TextView forgetpassword;
    @BindView(R.id.rl_mima)
    RelativeLayout rlMima;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.text)
    RelativeLayout text;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.weichat)
    ImageView weichat;
    @BindView(R.id.qq)
    ImageView qq;
    @BindView(R.id.wb)
    ImageView wb;
    @BindView(R.id.tv_agree)
    CheckBox tvAgree;
    @BindView(R.id.fangshi)
    TextView fangshi;

    private UMShareAPI umShareAPI;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        umShareAPI = UMShareAPI.get(this);
        //设置第三方的id 和key
        ivBack.setVisibility(View.GONE);

        setTitle("登录");
        tvRight.setText("去注册");
        tvRight.setVisibility(View.VISIBLE);

        if (!StringUtil.isEmpty(SHPUtils.getParame(getApplicationContext(), SHPUtils.PASSWORD))) {
            etName.setText(SHPUtils.getParame(getApplicationContext(), SHPUtils.PHONE_LOGIN));
            etPassword.setText(SHPUtils.getParame(getApplicationContext(), SHPUtils.PASSWORD));
            tvAgree.setChecked(true);

            etName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    tvAgree.setChecked(false);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @OnClick(R.id.tv_right)
    public void onViewClicked() {
        Intent intent = new Intent(this, RegistActivity.class);
        startActivity(intent);
    }

    private void login() {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().login(AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext()),
                etName.getText().toString(),
                etPassword.getText().toString()),
                new HttpResultCall<HttpResult<Object>, Object>() {

                    @Override
                    public void onResponse(Object result, String msg) {
//                        mackToastLONG(msg, LoginActivity.this);
                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString("password", etPassword.getText().toString());
                        edit.apply();

                        String token = result.toString().split("=")[1];
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.TOKEN, token);
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.PHONE, etName.getText().toString());

                        if (tvAgree.isChecked()) {
                            SHPUtils.saveParame(getApplicationContext(), SHPUtils.PASSWORD, etPassword.getText().toString());
                            SHPUtils.saveParame(getApplicationContext(), SHPUtils.PHONE_LOGIN, etName.getText().toString());

                        }

                        getUserInfo();


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


    @OnClick({R.id.forgetpassword, R.id.login, R.id.weichat, R.id.qq, R.id.wb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forgetpassword:
                CommonUtils.startAct(this, FindPassword1Activity.class);
                break;
            case R.id.login:

                String phoneNum = etName.getText().toString();
                if (TextUtils.isEmpty(phoneNum)) {
                    mackToastLONG("请输入登录账", this);
                    return;
                }


                String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    mackToastLONG("请输入密码", this);
                    return;
                }
                login();
                break;
            case R.id.weichat:
                SHARE_MEDIA platform = SHARE_MEDIA.WEIXIN;// 微信授权


                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, platform,
                        new UMAuthListener() {
                            private String userHead;
                            private String name;
                            private String openid;

                            @Override
                            public void onError(SHARE_MEDIA arg0, int arg1,
                                                Throwable arg2) {
                                Toast.makeText(getApplicationContext(), arg0.toString(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            // 成功
                            @Override
                            public void onComplete(SHARE_MEDIA arg0, int arg1,
                                                   Map<String, String> data) {
                                Set<String> set = data.keySet();


                                for (String string : set) {
                                    String str = data.get(string);
                                    // 设置头像
                                    if (str != null) {
                                        if (string.equals("profile_image_url")) {
                                            userHead = str;
                                        }
                                        // 设置昵称
                                        if (string.equals("screen_name")) {
                                            name = str;
                                        }
                                        if (string.equals("openid")) {
                                            openid = str;
                                        }
                                    }
                                }
                                judgeIsBind(openid, name, userHead);
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA arg0, int arg1) {
                            }
                        });


                break;
            case R.id.qq:
                SHARE_MEDIA qq_Platform = SHARE_MEDIA.QQ;// 此处是QQ授权

                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, qq_Platform,
                        new UMAuthListener() {
                            private String userHead;
                            private String name;
                            private String openid;

                            @Override
                            public void onError(SHARE_MEDIA arg0, int arg1,
                                                Throwable arg2) {
                            }

                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            // 成功
                            @Override
                            public void onComplete(SHARE_MEDIA arg0, int arg1,
                                                   Map<String, String> data) {


                                Set<String> set = data.keySet();

                                for (String string : set) {
                                    String str = data.get(string);
                                    // 设置头像
                                    if (str != null) {
                                        if (string.equals("profile_image_url")) {
                                            userHead = str;
                                        }
                                        // 设置昵称
                                        if (string.equals("screen_name")) {
                                            name = str;
                                        }
                                        if (string.equals("openid")) {
                                            openid = str;
                                        }
                                    }
                                }
                                judgeIsBind(openid, name, userHead);
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA arg0, int arg1) {
                            }
                        });


                break;
            case R.id.wb:
                SHARE_MEDIA wb_Platform = SHARE_MEDIA.SINA;// 此处是sina wb

                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, wb_Platform,
                        new UMAuthListener() {
                            private String userHead;
                            private String name;
                            private String openid;

                            @Override
                            public void onError(SHARE_MEDIA arg0, int arg1,
                                                Throwable arg2) {

                            }

                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                            }

                            // 成功
                            @Override
                            public void onComplete(SHARE_MEDIA arg0, int arg1,
                                                   Map<String, String> data) {
                                Set<String> set = data.keySet();

                                for (String string : set) {
                                    String str = data.get(string);
                                    // 设置头像
                                    if (str != null) {
                                        if (string.equals("profile_image_url")) {
                                            userHead = str;
                                        }
                                        // 设置昵称
                                        if (string.equals("screen_name")) {
                                            name = str;
                                        }
                                        if (string.equals("uid")) {
                                            openid = str;
                                        }
                                    }
                                }

                                judgeIsBind(openid, name, userHead);
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA arg0, int arg1) {
                            }
                        });

                break;
        }
    }


    private void isBinded(String token, String openid, String name, String hPath, int isbind) {

        Intent intent = new Intent();
        if (isbind == 0) {
            intent.setClass(LoginActivity.this, BindPhoneActivity.class);
            intent.putExtra("token", token);
            intent.putExtra("openid", openid);
            intent.putExtra("name", name);
            intent.putExtra("headPath", hPath);
        } else {
            intent.setClass(LoginActivity.this, LiveMainActivity.class);
            getUserInfo();
        }
        startActivity(intent);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        umShareAPI.release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        umShareAPI.onSaveInstanceState(outState);
    }

    public void judgeIsBind(final String openid, final String name, final String hPath) {

        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().wxIsBind(AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext()),
                openid,
                name, hPath),
                new HttpResultCall<HttpResult<LoginModel>, LoginModel>() {

                    @Override
                    public void onResponse(LoginModel result, String msg) {
                       //mackToastLONG(msg, LoginActivity.this);
                        String token = result.token;
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.TOKEN, token);
                        int isbind = result.is_bind;
                        isBinded(token, openid, name, hPath, isbind);
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

    public void getUserInfo() {

        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().getUserdetailInfo(AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext())),
                new HttpResultCall<HttpResult<UserInfo>, UserInfo>() {

                    @Override
                    public void onResponse(UserInfo result, String msg) {
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.PHONE, result.phone);
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.REAL_NAME, result.realname);
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.CONPANY, result.company);
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.JOB, result.job);
//                            SHPUtils.saveParame(getApplicationContext(),SHPUtils.ISVIP,result.is_vip);
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

//
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().getUserInfo(AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext())
                ),
                new HttpResultCall<HttpResult<Risgter>, Risgter>() {

                    @Override
                    public void onResponse(Risgter result, String msg) {

                        if (!StringUtil.isEmpty(result.avatar)) {
                            SHPUtils.saveParame(getApplicationContext(), SHPUtils.HEAD, result.avatar);
                        }
                        if (!StringUtil.isEmpty(result.nickname)) {
                            SHPUtils.saveParame(getApplicationContext(), SHPUtils.NICKNAME, result.nickname);
                        }


                        if (!StringUtil.isEmpty(result.invatation)) {
                            SHPUtils.saveParame(getApplicationContext(), SHPUtils.INVATATION, result.invatation);
                        }
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.PERCENT, result.percent + "");
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.ISVIP, result.is_vip);

                        setResult(999, new Intent());
                        CommonUtils.startAct(LoginActivity.this, LiveMainActivity.class);
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
}
