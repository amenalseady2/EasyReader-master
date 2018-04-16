package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.ImageViewAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LiveDetailModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//活动详情
public class ActionDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.jigou)
    TextView jigou;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.goods_recycle)
    ScrollView goodsRecycle;
    @BindView(R.id.baoming)
    TextView baoming;
    @BindView(R.id.headimg)
    ImageView headimg;
    @BindView(R.id.yuyue)
    TextView yuyue;
    @BindView(R.id.huodong_jianjie)
    TextView huodongJianjie;
    @BindView(R.id.jiangsi)
    TextView jiangsi;
    @BindView(R.id.hezuo_jigou)
    TextView hezuoJigou;
    @BindView(R.id.yicheng_img)
    ImageView yichengImg;
    @BindView(R.id.list_xiangqing)
    ListViewForScrollView listXiangqing;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_action_detail;
    }

    LiveDetailModel model;
    private String liveId; //zhibojiandi
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("活动详情");
        model = (LiveDetailModel) getIntent().getSerializableExtra("bean");

        if (model.is_appt == 0){
            yuyue.setVisibility(View.VISIBLE);
        }

        liveId = model.id;
        getdata();
//        if (model != null){
//            setData(model);
//        }else{
//            liveId =    getIntent().getStringExtra("id");
//        }

    }

    private void getdata(){
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        actionDetail(liveId, AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<LiveDetailModel>, LiveDetailModel>() {


                    @Override
                    public void onResponse(LiveDetailModel ShopModel, String msg) {
                        setData(ShopModel);
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
    //	加预约
    public void addOrder() {

        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        addyuyue(liveId, AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<LiveDetailModel>, LiveDetailModel>() {


                    @Override
                    public void onResponse(LiveDetailModel ShopModel, String msg) {
                        mackToastLONG(msg,getApplicationContext());
                        yuyue.setVisibility(View.GONE);
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

    boolean issign = false;
    private void setData(LiveDetailModel model) {
        GlideUtils.loadImage(2, model.cover, headimg);
        time.setText("直播时间："+model.start_at);
        name.setText(model.title);
        address.setText("地址："+model.address);
        jigou.setText("主办机构："+model.organ);


        huodongJianjie.setText(model.info);
        jiangsi.setText(model.speaker);

        hezuoJigou.setText(model.sponsor);
        GlideUtils.loadImage(2,model.agenda,yichengImg);

        List<String> imgList = new ArrayList<>();
        if (model.detail != null) {
            if (model.detail.contains(",")) {
                String[] imgs = model.detail.split(",");
                for (int i = 0; i < imgs.length; i++) {
                    imgList.add(imgs[i]);

                }
            } else {
                imgList.add(model.detail);
            }
        }

        listXiangqing.setAdapter(new ImageViewAdapter(imgList));

        if (model.is_sign ==1){
//            baoming.setVisibility(View.GONE);
            baoming.setText("已报名");
            issign = true;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (requestCode == 888){
                baoming.setText("已报名");
                issign = true;
            }
        }
    }

    @OnClick({R.id.yuyue, R.id.baoming})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yuyue:
                addOrder();
                break;
            case R.id.baoming:
                if (!issign){
                    CommonUtils.startActWithDataForResult(ActionDetailActivity.this, SignUpActivity.class, "id", model,888);
                }

                break;
        }
    }


//    @OnClick(R.id.baoming)
//    public void onViewClicked() {
//
//        CommonUtils.startAct(ActionDetailActivity.this, SignUpActivity.class);
//    }
}
