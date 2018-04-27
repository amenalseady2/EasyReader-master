package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.bean.ProductDetails.MessageUtil;
import com.BlackDiamond2010.hzs.http.service.PhoneService;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CheckStringUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.activity.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangeBindPhoneActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.getcheck)
    TextView getcheck;
    @BindView(R.id.et_checkcode)
    EditText etCheckcode;
    @BindView(R.id.tv_rigst)
    TextView tvRigst;

    String phonenum;
    String token;

    PhoneService phoneService;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_bind_phone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("修改绑定手机");

        token = SHPUtils.getParame(getApplicationContext(), SHPUtils.TOKEN);

        Log.e("token", "onCreate: " + token);
        getcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phonenum = etPhone.getText().toString();
                if ("".equals(phonenum)) {
                    mackToastLONG("请输入手机号码", ChangeBindPhoneActivity.this);
                } else if (!CheckStringUtil.checkPhone(phonenum)) {
                    mackToastLONG("手机号码格式有误", ChangeBindPhoneActivity.this);
                    etPhone.setText("");
                } else if (!flag) {
                    setUnClick();
                    getCode(token, phonenum);
                }
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.95.224.184/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        phoneService = retrofit.create(PhoneService.class);

        tvRigst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phonenum = etPhone.getText().toString();
                String code = etCheckcode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    mackToastLONG("请输入验证码", ChangeBindPhoneActivity.this);
                } else if ("".equals(phonenum)) {
                    mackToastLONG("请输入手机号码", ChangeBindPhoneActivity.this);
                } else if (!CheckStringUtil.checkPhone(phonenum)) {
                    mackToastLONG("手机号码格式有误", ChangeBindPhoneActivity.this);
                    etPhone.setText("");
                } else {
                    //重新绑定手机
                    changeBindPhone(token, phonenum, code);
                }
            }
        });
    }

    private void changeBindPhone(String token, String phonenum, String code) {
        showLoadingDialog();
        Call<MessageUtil> call = phoneService.changeBind(token, phonenum, code);
        call.enqueue(new Callback<MessageUtil>() {
            @Override
            public void onResponse(Call<MessageUtil> call, Response<MessageUtil> response) {
                dismissDialog();
                MessageUtil m = response.body();
                mackToastSHORT(m.getMsg(), ChangeBindPhoneActivity.this);
                if (m.getCode() == 200) {
                    Intent intent = new Intent(ChangeBindPhoneActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<MessageUtil> call, Throwable t) {
                dismissDialog();
                mackToastSHORT(t.getMessage(), ChangeBindPhoneActivity.this);
            }
        });
    }

    //  獲取验证码
    public void getCode(String token, String phoneNum) {
        showLoadingDialog();
        Call<MessageUtil> call = phoneService.send(token, phoneNum);
        call.enqueue(new Callback<MessageUtil>() {
            @Override
            public void onResponse(Call<MessageUtil> call, Response<MessageUtil> response) {
                dismissDialog();
                MessageUtil m = response.body();
                mackToastLONG(m.getMsg(), ChangeBindPhoneActivity.this);
            }

            @Override
            public void onFailure(Call<MessageUtil> call, Throwable t) {
                dismissDialog();
                mackToastSHORT(t.getMessage(), ChangeBindPhoneActivity.this);
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
                        getcheck.setText("获取验证码");
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                    }
                    break;
            }
        }

        ;
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
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }
}
