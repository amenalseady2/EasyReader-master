package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.OrderGoodsAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderItem;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Risgter;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.ImagePickerDisplay;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.ListViewForScrollView;
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

//申请退款
public class ShenQingRefundActivity extends BaseActivity {


    @BindView(R.id.myorder_child_listview)
    ListViewForScrollView myorderChildListview;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.orderid)
    TextView orderid;
    @BindView(R.id.info)
    TextView info;
    @BindView(R.id.myorder_child_root)
    LinearLayout myorderChildRoot;
    @BindView(R.id.server_content)
    TextView serverContent;
    @BindView(R.id.iv_go)
    ImageView ivGo;
    @BindView(R.id.miaos)
    TextView miaos;
    @BindView(R.id.orderroot)
    LinearLayout orderroot;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.img)
    ImageView img;
    MyOrderItem item;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shen_qing_refund;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("申请退款");
//        myorderChildListview.setAdapter(new OrderGoodsAdapter());
      item = (MyOrderItem) getIntent().getSerializableExtra("id");
        orderid.setText("订单号：" + item.order_sn);
        info.setText("共" + item.product_count + "件商品  合计：￥" + item.total_price + "(含运费)");
        myorderChildListview.setAdapter(new OrderGoodsAdapter(item.product_list));
        login();
    }

    private PopupWindow mPopupWindow;
    private View popView;

    private void initPopupWindow() {
        if (popView == null) {
            popView = LayoutInflater.from(this).inflate(R.layout.camera_or_img,
                    null, false);
        }
        mPopupWindow = new PopupWindow(popView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.style.AnimBottom);
        // popView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        popView.findViewById(R.id.funtions).setOnClickListener(click);
        popView.findViewById(R.id.img).setOnClickListener(click);
        popView.findViewById(R.id.camera).setOnClickListener(click);
        popView.findViewById(R.id.choise_cancel).setOnClickListener(click);
    }

int type  = 0 ;
    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.img:
                    mPopupWindow.dismiss();
                    type = 1;
                    serverContent.setText("申请退款");
                    break;
                case R.id.camera:
                    mPopupWindow.dismiss();
                    serverContent.setText("退货退款");
                    type =2;
                    break;
                case R.id.choise_cancel:
                    mPopupWindow.dismiss();


                    break;
                case R.id.funtions:
                    mPopupWindow.dismiss();
                    break;

            }

        }
    };

    @OnClick({R.id.server_content, R.id.img, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.server_content:
                if (mPopupWindow == null){
                    initPopupWindow();
                }
                mPopupWindow.showAtLocation(findViewById(R.id.setroot),
                    Gravity.BOTTOM, 0, 0);

                break;
            case R.id.img:

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_CONTACTS)) {
                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    }//申请权限
                } else {
                    getImg();
                }
                break;
            case R.id.submit:
                if (type == 0 ){

                    mackToastLONG("请选择服务类型",getApplicationContext());
                    return;
                }
                servercreat();
                break;
        }
    }



    private void getImg(){
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
        ImageSelector.open(ShenQingRefundActivity.this, imageConfig);
    }

    /**
     * android 授权
     */
    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 888;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImg();
            } else {
                // Permission Denied
                Toast.makeText(ShenQingRefundActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    String imgpath;
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
                            imgpath = "http://cdn.kanjian2020.com//" + vule;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 888) {
                List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                GlideUtils.loadImage(3, pathList.get(0), img);
                setheadImg(pathList.get(0));
            }
        }
    }



    private void servercreat() {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        servercreat(item.id,AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext()),type+"",content.getText().toString(),imgpath),
                new HttpResultCall<HttpResult<Object>, Object>() {

                    @Override
                    public void onResponse(Object result, String msg) {

                        setResult(888,new Intent());
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
