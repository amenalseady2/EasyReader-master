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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderItem;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Risgter;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.ImagePickerDisplay;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EvaluateActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.goodsimg)
    ImageView goodsimg;
    @BindView(R.id.goodsname)
    TextView goodsname;
    @BindView(R.id.goodsprice)
    TextView goodsprice;
    @BindView(R.id.pingfen)
    TextView pingfen;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.pingjia_img)
    ImageView pingjiaImg;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.tvcontent)
    EditText tvcontent;
    @BindView(R.id.ll_image)
    LinearLayout llImage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("晒单评价");

        MyOrderItem m = (MyOrderItem) getIntent().getSerializableExtra("id");
        //去评价
        GoodsModel model = m.product_list.get(0);
        id = m.id;
        if (model != null) {
            GlideUtils.loadImage(2, model.cover, goodsimg);
            goodsname.setText(model.name);
            goodsprice.setText("￥" + model.price);
        }
        login();
    }

    String id;
    String imgpath;

    public void getData(String id) {
        StringBuffer bf = new StringBuffer();
        for (int i = 0; i < qiniuList.size(); i++) {
            if (i == qiniuList.size() - 1) {
                bf.append(qiniuList.get(i));
            } else {
                bf.append(qiniuList.get(i) + ",");
            }
        }
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        orderEva(id, AndroidUtils.getAndroidId(this), ratingbar.getNumStars() + "", tvcontent.getText().toString(), bf.toString()),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object shopBeen, String msg) {

                        setResult(888, new Intent());
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

    @OnClick({R.id.pingjia_img, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pingjia_img:

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
                showLoadingDialog();
                if (path.size() != 0) {
                    for (String pathImg :
                            path) {
                        setheadImg(pathImg);
                    }
                }
              getData(id);
                break;
        }
    }

    private ArrayList<String> path = new ArrayList<>();

    private void getImg() {
        //图片
        ImageConfig imageConfig = new ImageConfig.Builder(
                new ImagePickerDisplay())
                .steepToolBarColor(getResources().getColor(R.color.titleBlue))
                .titleBgColor(getResources().getColor(R.color.titleBlue))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // 开启多选   （默认为多选）
                .mutiSelect()
                // 多选时的最大数量   （默认 9 张）
                .mutiSelectMaxSize(3)
                //设置图片显示容器，参数：、（容器，每行显示数量，是否可删除）
                .setContainer(llImage, 3, true)
                // 已选择的图片路径
                .pathList(path)
                // 拍照后存放的图片路径（默认 /temp/picture）
                .filePath("/temp")
                // 开启拍照功能 （默认关闭）
//                .showCamera()
                .requestCode(30)
                .build();
        ImageSelector.open(EvaluateActivity.this, imageConfig);
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
                Toast.makeText(EvaluateActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private List<String> qiniuList = new ArrayList<>();

    private void setheadImg(final String photoPath) {

        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .build();
// 重用uploadManager。一般地，只需要创建一个uploadManager对象
        UploadManager uploadManager = new UploadManager(config);
        File file = new File(photoPath);
        uploadManager.put(photoPath, null, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            String vule = res.optString("key");
                            imgpath = "http://oya1nfh0k.bkt.clouddn.com/" + vule;
                            qiniuList.add(imgpath);

                            if (qiniuList.size() == path.size()) {
                                getData(id);
                            }
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
                GlideUtils.loadImage(3, pathList.get(0), pingjiaImg);
                setheadImg(pathList.get(0));
            }

            if (requestCode == 30 && resultCode == RESULT_OK && data != null) {
                List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);

//                if (pathList.size() >0){llContainer.setVisibility(View.VISIBLE);}else{llContainer.setVisibility(View.GONE);}

                path.clear();
                path.addAll(pathList);
            }
        }
    }

}
