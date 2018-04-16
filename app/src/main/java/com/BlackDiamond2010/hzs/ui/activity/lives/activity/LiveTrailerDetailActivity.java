package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.ImageViewAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.LiveMessageDatailAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LiveDetailMessage;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LiveDetailModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.SubscribeModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.ShareUtil;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.CircleImageView;
import com.BlackDiamond2010.hzs.view.ListViewForScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

// 直播yuga
public class LiveTrailerDetailActivity extends BaseActivity {

    @BindView(R.id.detail_player)
    ImageView detailPlayer;
    @BindView(R.id.detail_back)
    ImageView detailBack;
    @BindView(R.id.share_icon)
    ImageView shareIcon;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.yuyue)
    TextView yuyue;
    @BindView(R.id.rl_live)
    RelativeLayout rlLive;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.action_btn)
    TextView actionBtn;
    @BindView(R.id.shoucang)
    ImageView shoucang;
    @BindView(R.id.live_info_rl)
    RelativeLayout liveInfoRl;
    @BindView(R.id.div1)
    View div1;
    @BindView(R.id.img)
    CircleImageView img;
    @BindView(R.id.rl_dingyue)
    RelativeLayout rlDingyue;
    @BindView(R.id.div2)
    View div2;
    @BindView(R.id.pubishname)
    TextView pubishname;
    @BindView(R.id.fans)
    TextView fans;
    List<LiveDetailMessage> messagesList;
    LiveMessageDatailAdapter messageDatailAdapter;
    @BindView(R.id.jiadingyue)
    TextView jiadingyue;
    @BindView(R.id.yidingyue)
    TextView yidingyue;
    @BindView(R.id.baoming)
    TextView baoming;
    @BindView(R.id.time1)
    TextView time1;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.jigou)
    TextView jigou;
    @BindView(R.id.div3)
    View div3;
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
    @BindView(R.id.review_root)
    ScrollView reviewRoot;
    @BindView(R.id.trailer_root)
    RelativeLayout trailerRoot;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_trailer;
    }

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");//直播间id
        getData();

        registLive();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("chat");
        registerReceiver(new Receiver(), intentFilter);
        messagesList = new ArrayList<>();
        messageDatailAdapter = new LiveMessageDatailAdapter(messagesList);
    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                mackToastLONG(intent.getStringExtra("data"), context);
                JSONObject json = new JSONObject(intent.getStringExtra("data"));

                JSONObject dataObj = json.optJSONObject("data");
                LiveDetailMessage mdoel = new LiveDetailMessage();
                mdoel.avatar = dataObj.optString("avatar");
                mdoel.nickname = dataObj.optString("nickname");
                mdoel.content = dataObj.optString("content");
                mdoel.time = System.currentTimeMillis() + "";

                messagesList.add(0, mdoel);
                messageDatailAdapter.setData(messagesList);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void onBackPressed() {
        unregistLive();
        super.onBackPressed();
    }

    private void registLive() {

        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        regist_chat(id + "", AndroidUtils.getAndroidId(getApplicationContext()), SHPUtils.getParame(getApplicationContext(), SHPUtils.CLIENTID)),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object ShopModel, String msg) {

                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);

                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }
                });


    }

    private void unregistLive() {

        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        unregist_chat(id + "", AndroidUtils.getAndroidId(getApplicationContext()), SHPUtils.getParame(getApplicationContext(), SHPUtils.CLIENTID)),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object ShopModel, String msg) {

                    }

                    @Override
                    public void onErr(String err, int status) {


                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }
                });


    }


    //	type	integer	是	1直播 2项目 3文章 4商品  收藏操作
    public void addCollect(String id, int type) {

        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        addCollect(type, id, AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object ShopModel, String msg) {
                        mackToastLONG(msg, getApplicationContext());
                        if ("已取消".equals(msg)) {
                            shoucang.setImageResource(R.mipmap.soucang_normal);
                        } else if ("已收藏".equals(msg)) {
                            shoucang.setImageResource(R.mipmap.soucang_pressed);
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                        if (status == 405) {
                            CommonUtils.startAct(LiveTrailerDetailActivity.this, LoginActivity.class);
                        }
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


                model.is_subscribe = data.getIntExtra("type", 0);
                if (model.is_subscribe == 1) {

                    jiadingyue.setVisibility(View.GONE);
                    yidingyue.setVisibility(View.VISIBLE);
                } else {

                    jiadingyue.setVisibility(View.VISIBLE);
                    yidingyue.setVisibility(View.GONE);
                }
            }

                if (requestCode == 999){
                    baoming.setText("已报名");
                    issign = true;
                }

        }

    }

    @OnClick({ R.id.baoming,R.id.yidingyue, R.id.jiadingyue, R.id.rl_dingyue, R.id.detail_back, R.id.yuyue, R.id.action_btn, R.id.shoucang, R.id.live_info_rl, R.id.share_icon})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.baoming:
                if (!issign){
                    CommonUtils.startActWithDataForResult(LiveTrailerDetailActivity.this, SignUpActivity.class, "id", model,999);
                }

                break;

            case R.id.yidingyue:
            case R.id.jiadingyue:
                addSub();
                break;
            case R.id.rl_dingyue:
                SubscribeModel mo = new SubscribeModel();
                mo.name = model.publisher_name;
                mo.avatar = model.publisher_avatar;
                mo.id = model.publisher_id;
                mo.fans = model.publisher_fans;

                Intent intent = new Intent(LiveTrailerDetailActivity.this, SubscribeDetailActivity.class);
                intent.putExtra("isSu", model.is_subscribe);
                intent.putExtra("model", mo);

                startActivityForResult(intent, 888);
                break;
//            case R.id.cha:
//                rlHudong.setVisibility(View.GONE);
//                break;
            case R.id.detail_back:
                unregistLive();
                finish();
                break;
            case R.id.share_icon:
                View viewRoot = LiveTrailerDetailActivity.this.findViewById(R.id.trailer_root);
                ShareUtil u = new ShareUtil(LiveTrailerDetailActivity.this, viewRoot, liveModel.title, R.drawable.luyan_logo, liveModel.info, liveModel.share, 1);
                break;
            case R.id.yuyue:

                if ("已预约".equals(yuyue.getText().toString())) {
                    mackToastLONG("已预约不可重复预约", LiveTrailerDetailActivity.this);
                } else {
                    addOrder();
                }


                break;
            case R.id.action_btn:
                CommonUtils.startActWithData(LiveTrailerDetailActivity.this, ActionDetailActivity.class, "bean", liveModel);
                break;
            case R.id.shoucang:
                addCollect(id, 1);
                break;
            case R.id.live_info_rl:
                break;

        }
    }

    //	加预约
    public void addOrder() {

        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        addyuyue(id, AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<LiveDetailModel>, LiveDetailModel>() {


                    @Override
                    public void onResponse(LiveDetailModel ShopModel, String msg) {
//                        yuyue.setVisibility(View.GONE);
                        yuyue.setText("已预约");
                        liveModel.is_appt = 1;
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

    public void sendChat(String content) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        send_chat(content, id, AndroidUtils.getAndroidId(getApplicationContext()), SHPUtils.getParame(getApplicationContext(), SHPUtils.CLIENTID)),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object ShopModel, String msg) {

                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                        if (status == 405) {
                            CommonUtils.startAct(LiveTrailerDetailActivity.this, LoginActivity.class);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        dismissDialog();
                    }
                });
    }

    private LiveDetailModel liveModel;

    public void getData() {

        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        livePrevae(id, AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<LiveDetailModel>, LiveDetailModel>() {


                    @Override
                    public void onResponse(LiveDetailModel shopBeen, String msg) {

                        liveModel = shopBeen;
                        setData(shopBeen);

                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                        super.onCompleted();
                    }
                });
    }

    String pub_id;
    LiveDetailModel model;
    boolean issign;

    private void setData(LiveDetailModel model) {
        GlideUtils.loadImage(2, model.cover, detailPlayer);
//        time1.setText(model.start_at);
        seconds = Integer.valueOf(model.time);
        //  开始倒计时
        setUnClick();
        tvTitle.setText(model.title);
        if (model.is_collection == 1) {
            shoucang.setImageResource(R.mipmap.soucang_pressed);
        } else {
            shoucang.setImageResource(R.mipmap.soucang_normal);
        }

        if (model.is_subscribe == 1) {
            jiadingyue.setVisibility(View.GONE);
            yidingyue.setVisibility(View.VISIBLE);
        } else {
            jiadingyue.setVisibility(View.VISIBLE);
            yidingyue.setVisibility(View.GONE);
        }

        if (model.is_sign ==1){
//            baoming.setVisibility(View.GONE);
            baoming.setText("已报名");
            issign = true;
        }
        this.model = model;
        GlideUtils.loadImage(3, model.publisher_avatar, img);
        pubishname.setText(model.publisher_name);
        fans.setText(model.publisher_fans + "个粉丝");
        pub_id = model.publisher_id;
        if (model.is_appt == 1) {
            yuyue.setText("已预约");
        }
        time.setText("直播时间：" + model.start_at);
        name.setText(model.title);
        address.setText("地址：" + model.address);
        jigou.setText("主办机构：" + model.organ);
        huodongJianjie.setText(model.info);
        jiangsi.setText(model.speaker);
        hezuoJigou.setText(model.sponsor);
        GlideUtils.loadImage(2, model.agenda, yichengImg);
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
        rlLive.setFocusable(true);
        rlLive.setFocusableInTouchMode(true);
        rlLive.requestFocus();
    }


    public void addSub() {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        addSubscribe(pub_id, AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<Object>, Object>() {
                    @Override
                    public void onResponse(Object shopBeen, String msg) {
                        mackToastLONG(msg, getApplicationContext());
                        if ("已订阅".equals(msg)) {
                            model.is_subscribe = 1;
                            jiadingyue.setVisibility(View.GONE);
                            yidingyue.setVisibility(View.VISIBLE);
                        } else {
                            model.is_subscribe = 0;
                            jiadingyue.setVisibility(View.VISIBLE);
                            yidingyue.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                        super.onCompleted();
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
                        formtData(seconds);
                    } else {
                        formtData(0);
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
     * @param seconds 多少秒
     * */
    private void  formtData(int seconds){

        int day =  0;
        int h = 0;
        int m = 0 ;
        int s = 0;

        day = seconds/60/60/24;
        h = (seconds - day*60*60*24)/60/60;
        m = (seconds -  day*60*60*24  -   h*60*60 )/60;
        s = (seconds -  day*60*60*24  -   h*60*60  - m*60);

        time1.setText(day+"天 "+h+"时 " +m+"分 "+s+"秒");
    }

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
