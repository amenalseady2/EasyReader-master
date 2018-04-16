package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Risgter;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CheckStringUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class FindPassword1Activity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.getcheck)
    TextView getcheck;
    @BindView(R.id.et_checkcode)
    EditText etCheckcode;
    @BindView(R.id.next)
    TextView next;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_password1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("找回密码");
    }

    @OnClick({R.id.getcheck, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getcheck:

                String phonenum = checkPhoneNum();
                if (!flag) {
                    setUnClick();
                    getCode(AndroidUtils.getAndroidId(this), phonenum);
                }

                break;
            case R.id.next:
                String phonenums = checkPhoneNum();
//                find(AndroidUtils.getAndroidId(this),phonenums,etCheckcode.getText().toString());
                if (StringUtil.isEmpty(etCheckcode.getText().toString())){
                    mackToastLONG("请输入验证码",FindPassword1Activity.this);
                    return;
                }

                Intent intent = new Intent(FindPassword1Activity.this,FindPassWord2Activity.class);
                intent.putExtra("phone",phonenums);
                intent.putExtra("code",etCheckcode.getText().toString());
                startActivity(intent);
                finish();

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

    //  獲取验证码
    public void getCode(String devieId, String phoneNum) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                RegisterMsm(devieId, phoneNum, "forget"), new HttpResultCall<HttpResult, Object>() {
            @Override
            public void onResponse(Object result, String msg) {
                mackToastLONG(msg, FindPassword1Activity.this);


            }

            @Override
            public void onErr(String err, int status) {
                mackToastLONG(err, FindPassword1Activity.this);
            }

            @Override
            public void onCompleted() {
                dismissDialog();
            }

        });
    }



    //  獲取验证码
    public void find(String devieId, final  String phoneNum, final String code) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                findpass(devieId, phoneNum, code), new HttpResultCall<HttpResult<Risgter>, Risgter>() {
            @Override
            public void onResponse(Risgter result, String msg) {

                Intent intent = new Intent(FindPassword1Activity.this,FindPassWord2Activity.class);
                intent.putExtra("phone",phoneNum);
                intent.putExtra("code",code);
                startActivity(intent);
            }

            @Override
            public void onErr(String err, int status) {
                mackToastLONG(err, FindPassword1Activity.this);
            }

            @Override
            public void onCompleted() {
                dismissDialog();
            }

        });
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
