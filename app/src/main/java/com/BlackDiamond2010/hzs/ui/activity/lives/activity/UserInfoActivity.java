package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Risgter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.UserInfo;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.ImagePickerDisplay;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.CircleImageView;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.iv_changehead_ion)
    ImageView ivChangeheadIon;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.user_n)
    TextView userN;
    @BindView(R.id.tv_username)
    EditText tvUsername;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.tv_phonenum)
    TextView tvPhonenum;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.companyname)
    TextView companyname;
    @BindView(R.id.et_companyname)
    EditText etCompanyname;
    @BindView(R.id.zhiwu)
    TextView zhiwu;
    @BindView(R.id.et_zhiwu)
    EditText etZhiwu;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("账号信息");
        getUserInfo();
        login();
    }

    private void setheadImg(String path) {

        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .build();
// 重用uploadManager。一般地，只需要创建一个uploadManager对象
        UploadManager uploadManager = new UploadManager(config);
        File file = new File(path);
        uploadManager.put(path, null, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            String vule = res.optString("key");
                            userHeadPath = "http://oya1nfh0k.bkt.clouddn.com/" + vule;
                        } else {

                        }

                    }
                }, null);

    }


    //获取上传图片token
    private String token;

    private void login() {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        getToken(AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext())),
                new HttpResultCall<HttpResult<Risgter>, Risgter>() {

                    @Override
                    public void onResponse(Risgter result, String msg) {
                        token = result.token;
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

    //获取信息
    private String userHeadPath;

    public void getUserInfo() {

        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().getUserdetailInfo(AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext())),
                new HttpResultCall<HttpResult<UserInfo>, UserInfo>() {

                    @Override
                    public void onResponse(UserInfo result, String msg) {

                        if (!StringUtil.isEmpty(result.avatar)) {
                            GlideUtils.loadImage(3, result.avatar, ivHead);
                            userHeadPath = result.avatar;
                            SHPUtils.saveParame(getApplicationContext(), SHPUtils.HEAD, userHeadPath);
                        }
                        if (!StringUtil.isEmpty(result.nickname)) {
                            tvUsername.setText(result.nickname);
                            SHPUtils.saveParame(getApplicationContext(), SHPUtils.NICKNAME, result.nickname);
                        }

                        etUsername.setText(result.realname);
                        tvPhonenum.setText(result.phone);
                        etZhiwu.setText(result.job);
                        etCompanyname.setText(result.company);

                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.PHONE, result.phone);
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.REAL_NAME, result.realname);
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.CONPANY, result.company);
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.JOB, result.job);

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


    //修改

    public void alterUserInfo() {

        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().alterUserInfo(etZhiwu.getText().toString(),

                userHeadPath, tvUsername.getText().toString(), tvPhonenum.getText().toString(), etUsername.getText().toString(), etCompanyname.getText().toString(), AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext())),
                new HttpResultCall<HttpResult<Object>, Object>() {

                    @Override
                    public void onResponse(Object result, String msg) {

                        mackToastLONG(msg, getApplicationContext());

                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.HEAD, userHeadPath);
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.NICKNAME, tvUsername.getText().toString());


                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.REAL_NAME, etUsername.getText().toString());
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.CONPANY, etCompanyname.getText().toString());
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.JOB, etZhiwu.getText().toString());

                        getUserInfoss();

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

    public void getUserInfoss() {

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

                        setResult(999, new Intent());
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


    String fileWebPath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 888) {
                List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                GlideUtils.loadImage(3, pathList.get(0), ivHead);
                setheadImg(pathList.get(0));
            }
        }
    }


    /**
     * android 授权
     */
    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 888;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getImg();
            } else {
                // Permission Denied
                Toast.makeText(UserInfoActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @OnClick({R.id.rl_head, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_head:


                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_CONTACTS)) {
                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    }//申请权限
                } else {
                    getImg();
                }
                break;
            case R.id.submit:

                if (StringUtil.isEmpty(userHeadPath)) {
                    mackToastLONG("请选择头像", getApplicationContext());
                    return;
                }
                if (StringUtil.isEmpty(tvUsername.getText().toString())) {
                    mackToastLONG("用户名不能为空", getApplicationContext());
                    return;
                }
                if (StringUtil.isEmpty(etUsername.getText().toString())) {
                    mackToastLONG("请填写真实姓名", getApplicationContext());
                    return;
                }

                if (StringUtil.isEmpty(etCompanyname.getText().toString())) {
                    mackToastLONG("请填写公司名称", getApplicationContext());
                    return;
                }
                if (StringUtil.isEmpty(etZhiwu.getText().toString())) {
                    mackToastLONG("请填写职位名称", getApplicationContext());
                    return;
                }

                alterUserInfo();
                break;
        }
    }

    private void getImg() {
        ImageConfig imageConfig = new ImageConfig.Builder(
                new ImagePickerDisplay())
                .steepToolBarColor(getResources().getColor(R.color.titleBlue))
                .titleBgColor(getResources().getColor(R.color.titleBlue))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）
                .singleSelect()
                // 裁剪 (只有单选可裁剪)
                //.crop()
                // 开启拍照功能 （默认关闭）
//                .showCamera()
                //设置显示容器

                .requestCode(888)
                .build();
        ImageSelector.open(UserInfoActivity.this, imageConfig);
    }
}
