package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.DataCleanManagerUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.MyDialog;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_phonenum)
    TextView tvPhonenum;
    @BindView(R.id.info_tongzhi)
    ImageView infoTongzhi;
    @BindView(R.id.re_help)
    RelativeLayout reHelp;
    @BindView(R.id.re_alterpassword)
    RelativeLayout reAlterpassword;
    @BindView(R.id.tv_clear)
    TextView tvClear;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("设置");

        try {
          String lajiData =   DataCleanManagerUtil.getTotalCacheSize(this);
            tvClear.setText(lajiData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.info_tongzhi, R.id.re_help, R.id.re_alterpassword, R.id.clear_rela, R.id.about_luyan, R.id.login_out,R.id.rl_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_call:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_CONTACTS)) {
                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    }//申请权限
                } else {
                    call(Intent.ACTION_CALL,"4001782998");
                }
                break;
            case R.id.info_tongzhi:
                break;
            case R.id.re_help:
                CommonUtils.startAct(SettingActivity.this,HelpCenterActivity.class);
                break;
            case R.id.re_alterpassword:
                CommonUtils.startAct(SettingActivity.this,AlterPassWordActivity.class);
                break;
            case R.id.clear_rela:
                DataCleanManagerUtil.clearAllCache(this);
                try {
                    String lajiData =   DataCleanManagerUtil.getTotalCacheSize(SettingActivity.this);
                    tvClear.setText(lajiData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.about_luyan:
                Intent intent = new Intent(this,AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.login_out:
                MyDialog.getDialogAndshow(this, new MyDialog.DialogCallBack() {
                    @Override
                    public void dialogSure() {

                        SHPUtils.saveParame(getApplicationContext(),SHPUtils.HEAD,null);
                        SHPUtils.saveParame(getApplicationContext(),SHPUtils.NICKNAME,null);

                        SHPUtils.saveParame(getApplicationContext(),SHPUtils.TOKEN,null);
                        SHPUtils.saveParame(getApplicationContext(),SHPUtils.PERCENT,null);

                        SHPUtils.saveParame(getApplicationContext(),SHPUtils.CONPANY,null);
                        SHPUtils.saveParame(getApplicationContext(),SHPUtils.ADDRESSID,null);

                        SHPUtils.saveParame(getApplicationContext(),SHPUtils.ADDRESS,null);
                        SHPUtils.saveParame(getApplicationContext(),SHPUtils.REAL_NAME,null);

                        SHPUtils.saveParame(getApplicationContext(),SHPUtils.MESSAGE,null);
                        SHPUtils.saveParame(getApplicationContext(),SHPUtils.ORDER_MESSAGE,null);
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.ISVIP, null);
                        CommonUtils.startAct(SettingActivity.this, LoginActivity.class);
                        finish();
                    }

                    @Override
                    public void dialogCancle() {

                    }
                },"确定退出当前账号？","确定","取消",null);


                break;
        }
    }

    /**
     * android 授权
     * */
    private  final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 888;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                call(Intent.ACTION_CALL,"4001782998");
            } else
            {
                // Permission Denied
                Toast.makeText(SettingActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void call(String action,String phoneNum){
        if(phoneNum!=null&&phoneNum.trim().length()>0){
            //这里"tel:"+电话号码 是固定格式，系统一看是以"tel:"开头的，就知道后面应该是电话号码。
            Intent intent = new Intent(action, Uri.parse("tel:" + phoneNum.trim()));
            startActivity(intent);//调用上面这个intent实现拨号
        }else{
            Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_LONG).show();
        }
    }
}
