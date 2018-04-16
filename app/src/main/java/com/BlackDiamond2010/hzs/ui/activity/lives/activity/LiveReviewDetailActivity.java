package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.ImageViewAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LiveDetailModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.SubscribeModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.ShareUtil;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.CircleImageView;
import com.BlackDiamond2010.hzs.view.ListViewForScrollView;
import com.BlackDiamond2010.hzs.view.gsy_videoplay.LandLayoutVideo;
import com.BlackDiamond2010.hzs.view.gsy_videoplay.SampleListener;
import com.gyf.barlibrary.ImmersionBar;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

// 直播回顾详情
public class LiveReviewDetailActivity extends BaseActivity {


    protected ImmersionBar mImmersionBar;
    @BindView(R.id.detail_player)
    LandLayoutVideo detailPlayer;
    @BindView(R.id.detail_back)
    ImageView detailBack;
    @BindView(R.id.tv_personalnum)
    TextView tvPersonalnum;
    @BindView(R.id.share_icon)
    ImageView shareIcon;
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
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.jigou)
    TextView jigou;
    @BindView(R.id.div3)
    View div3;
    @BindView(R.id.pubishname)
    TextView pubishname;
    @BindView(R.id.fans)
    TextView fans;
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
    @BindView(R.id.jiadingyue)
    TextView jiadingyue;
    @BindView(R.id.yidingyue)
    TextView yidingyue;
    @BindView(R.id.tv_pv)
    TextView tvPv;
    @BindView(R.id.review_root)
    ScrollView reviewRoot;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_review;
    }


    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");//直播间id

        getData();
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!detailPlayer.isIfCurrentIsFullscreen()) {
                    detailPlayer.startWindowFullscreen(LiveReviewDetailActivity.this, true, true);
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (detailPlayer.isIfCurrentIsFullscreen()) {
                    StandardGSYVideoPlayer.backFromWindowFull(this);
                }
                if (orientationUtils != null) {
                    orientationUtils.setEnable(true);
                }
            }
        }
    }


    private void resolveNormalVideoUI() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getTitleTextView().setText("测试视频");
        detailPlayer.getBackButton().setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {

        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null){
            if(requestCode == 888){
                model.is_subscribe = data.getIntExtra("type",0);
                if ( model.is_subscribe == 1) {

                    jiadingyue.setVisibility(View.GONE);
                    yidingyue.setVisibility(View.VISIBLE);
                } else {

                    jiadingyue.setVisibility(View.VISIBLE);
                    yidingyue.setVisibility(View.GONE);
                }
            }
        }

    }

    @OnClick({R.id.yidingyue,R.id.jiadingyue,R.id.rl_dingyue,R.id.shoucang, R.id.detail_back, R.id.tv_personalnum, R.id.action_btn, R.id.share_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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

                Intent intent = new Intent(LiveReviewDetailActivity.this,SubscribeDetailActivity.class);
                intent.putExtra("isSu",model.is_subscribe);
                intent.putExtra("model",mo);
                startActivityForResult(intent,888);
                break;
            case R.id.share_icon:
                View viewRoot = LiveReviewDetailActivity.this.findViewById(R.id.review_root);
                ShareUtil u = new ShareUtil(LiveReviewDetailActivity.this, viewRoot, liveModel.title, R.drawable.luyan_logo, liveModel.info, liveModel.share, 1);
                break;
            case R.id.shoucang:
                addCollect(id, 1);
                break;
            case R.id.detail_back:
                finish();
                break;
            case R.id.tv_personalnum:
                break;
            case R.id.action_btn:
                CommonUtils.startActWithData(LiveReviewDetailActivity.this, ActionDetailActivity.class, "bean", liveModel);
                break;
        }
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
                            CommonUtils.startAct(LiveReviewDetailActivity.this, LoginActivity.class);
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
    String bofangURL;

    public void getData() {

        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        livereview(id, AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<LiveDetailModel>, LiveDetailModel>() {


                    @Override
                    public void onResponse(LiveDetailModel shopBeen, String msg) {

                        liveModel = shopBeen;
                        setData(shopBeen);
                        bofangURL = shopBeen.video;
                        toPlay(shopBeen.video);

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

    private void toPlay(String url) {
//        String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        detailPlayer.setUp(url, false, null, "");

        /*VideoOptionModel videoOptionModel =
                new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 5);
        List<VideoOptionModel> list = new ArrayList<>();
        list.add(videoOptionModel);
        GSYVideoManager.instance().setOptionModelList(list);*/

        GSYVideoManager.instance().setTimeOut(4000, true);

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.mipmap.test);
        GlideUtils.loadImage(2, liveModel.cover, imageView);
        detailPlayer.setThumbImageView(imageView);

        resolveNormalVideoUI();

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        detailPlayer.setIsTouchWiget(true);
        //detailPlayer.setIsTouchWigetFull(false);
        //关闭自动旋转
        detailPlayer.setRotateViewAuto(false);
        detailPlayer.setLockLand(false);
        detailPlayer.setShowFullAnimation(false);
        detailPlayer.setNeedLockFull(true);
        //detailPlayer.setOpenPreView(false);
        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(LiveReviewDetailActivity.this, true, true);
            }
        });

        detailPlayer.setStandardVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
                isPlay = true;
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }
        });

        detailPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });
    }

    String pub_id;
    LiveDetailModel model;
    private void setData(LiveDetailModel model) {

        time.setText(model.start_at);
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
        this.model = model;


        tvPv.setText("观看人数："+model.pv);
        GlideUtils.loadImage(3, model.publisher_avatar, img);
        pubishname.setText(model.publisher_name);
        fans.setText(model.publisher_fans + "个粉丝");
        pub_id = model.publisher_id;
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

                            jiadingyue.setVisibility(View.GONE);
                            yidingyue.setVisibility(View.VISIBLE);
                                                        model.is_subscribe = 1;

                        } else {
                            jiadingyue.setVisibility(View.VISIBLE);
                            yidingyue.setVisibility(View.GONE);
                            model.is_subscribe = 0;
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
}
