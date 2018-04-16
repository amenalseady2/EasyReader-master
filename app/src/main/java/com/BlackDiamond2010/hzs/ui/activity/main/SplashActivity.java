package com.BlackDiamond2010.hzs.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.LiveMainActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.BindPhoneActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.LoginActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.pushService.MyIntentService;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;
import com.igexin.sdk.PushManager;

import butterknife.BindView;


public class SplashActivity extends BaseActivity {


    @BindView(R.id.iv_pic)
    ImageView ivPic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ivPic.setImageResource(R.mipmap.haden);
        //初始化个推SDK
        PushManager.getInstance().initialize(this.getApplicationContext(), com.BlackDiamond2010.hzs.ui.activity.lives.pushService.MyPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), MyIntentService.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, 1500);
    }

    private void toMainActivity() {
//        Intent intent = new Intent(this, MainActivity.class);
        if (StringUtil.isEmpty(SHPUtils.getParame(getApplicationContext(),SHPUtils.TOKEN))){

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
        }else {
            if (StringUtil.isEmpty(SHPUtils.getParame(getApplicationContext(),SHPUtils.PHONE))){
                mackToastLONG("您还未绑定你的手机号请绑定",getApplicationContext());
                Intent intent = new Intent(this, BindPhoneActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(this, LiveMainActivity.class);

                if(getIntent().getBundleExtra("message") != null){
                    intent.putExtra("message",
                            getIntent().getBundleExtra("message"));
                }


                startActivity(intent);
            }

        }

        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
    }

}
