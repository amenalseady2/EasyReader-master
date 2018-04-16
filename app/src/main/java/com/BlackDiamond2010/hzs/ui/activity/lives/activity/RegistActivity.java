package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.LiveMainActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Risgter;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CheckStringUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.getcheck)
    TextView getcheck;
    @BindView(R.id.et_checkcode)
    EditText etCheckcode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_agree)
    CheckBox tvAgree;
    @BindView(R.id.xieyi)
    TextView xieyi;
    @BindView(R.id.rl_mima)
    RelativeLayout rlMima;
    @BindView(R.id.tv_rigst)
    TextView tvRigst;

    private boolean isAgree = true;//

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("注册");

        xieyi.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
    }

    /**
     * 注册
     */
    public void regist(String deviceId,final String nickname, final String phoneNum, String code, String password) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(null).sendRequest().Register(deviceId,
                nickname, phoneNum, code, password),
                new HttpResultCall<HttpResult<Risgter>, Risgter>() {
                    @Override
                    public void onResponse(Risgter result, String msg) {
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.TOKEN, result.token);
                        SHPUtils.saveParame(getApplicationContext(),SHPUtils.NICKNAME,nickname);
                        SHPUtils.saveParame(getApplicationContext(),SHPUtils.PHONE,phoneNum);

                        CommonUtils.startAct(RegistActivity.this, LiveMainActivity.class);
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }
                });
    }

    //  獲取验证码
    public void getCode(String devieId, String phoneNum, String type) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                RegisterMsm(devieId, phoneNum, type), new HttpResultCall<HttpResult, Object>() {
            @Override
            public void onResponse(Object result, String msg) {
                mackToastLONG(msg, RegistActivity.this);

            }

            @Override
            public void onErr(String err, int status) {
                mackToastLONG(err, RegistActivity.this);
            }

            @Override
            public void onCompleted() {
                dismissDialog();
            }

        });
    }

    @OnClick({R.id.getcheck, R.id.tv_agree, R.id.xieyi, R.id.tv_rigst})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getcheck:
                String phoneString = etPhone.getText().toString();
                if ("".equals(phoneString)) {
                    mackToastLONG("请输入手机号码", this);
                    return ;
                }
                if (!CheckStringUtil.checkPhone(phoneString)) {
                    mackToastLONG("手机号码格式有误", this);
                    etPhone.setText("");
                    return ;
                }

                if (!flag) {
                    setUnClick();
                    getCode(AndroidUtils.getAndroidId(this), phoneString, "register");
                }

                break;
            case R.id.tv_agree:
                isAgree = !isAgree;
                break;
            case R.id.xieyi:
                Intent mIntent = new Intent(this,XieyiActivity.class);

                startActivity(mIntent);
                break;
            case R.id.tv_rigst:



                String nickName = etUsername.getText().toString();
                if (TextUtils.isEmpty(nickName)) {
                    mackToastLONG("请输入昵称", this);
                    return;
                }

                String phoneNum = etPhone.getText().toString();
                if (TextUtils.isEmpty(phoneNum)) {
                    mackToastLONG("请输入电话号码", this);
                    return;
                }

                String code = etCheckcode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    mackToastLONG("请输入验证码", this);
                    return;
                }

                String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    mackToastLONG("请输入密码", this);
                    return;
                }

                if (!isAgree){
                    mackToastLONG("请阅读并同意用户协议",this);
                    return;
                }
                regist(AndroidUtils.getAndroidId(getApplicationContext()), nickName, phoneNum, code, password);
                break;
        }
    }

    private String checkPhoneNum() {
        String phoneString = etPhone.getText().toString();
        if ("".equals(phoneString)) {
            mackToastLONG("请输入手机号码", this);
            return null;
        }
        if (!CheckStringUtil.checkPhone(phoneString)) {
            mackToastLONG("手机号码格式有误", this);
            etPhone.setText("");
            return null;
        }
        return phoneString;
    }

    private int seconds;
    private Timer timer;
    private MyTimerTask mTimerTask;
    private boolean flag;


    /**
     * 设置获取验证码按钮不能点击并开始倒计时
     */
    private void setUnClick() {
        getcheck.setTag("0");
        flag = true;
         getcheck.setTextColor(getResources().getColor(R.color.text_999));
        // getCheckBtn.setBackgroundResource(R.drawable.getkeycode);
        seconds = 60;
        getcheck.setText("重新获取(" + seconds + ")");
        timer = new Timer(true);
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }
        mTimerTask = new MyTimerTask();
        timer.schedule(mTimerTask, 1000, 1000);

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (seconds > 1) {
                        seconds--;
                        getcheck.setText("重新获取(" + seconds + ")");
                    } else {
                        flag = false;
                        getcheck.setTag("1");
                        getcheck.setTextColor(getResources().getColor(R.color.red));
                        // getCheckBtn
                        // .setBackgroundResource(R.drawable.getkeycodegree);
                        getcheck.setText("获取验证码");
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                    }
                    break;
            }
        };
    };

    /**
     * 自定义任务定时执行器
     *
     * @author Liuj
     */
    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimerTask != null){
            mTimerTask.cancel();
            mTimerTask =null;
        }
    }
}
