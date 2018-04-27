package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.bean.chat.Chat;
import com.BlackDiamond2010.hzs.http.service.ChatService;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
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
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.CircleImageView;
import com.BlackDiamond2010.hzs.view.gsy_videoplay.LandLayoutVideo;
import com.BlackDiamond2010.hzs.view.gsy_videoplay.SampleListener;
import com.gyf.barlibrary.ImmersionBar;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//正在直播详情
public class LiveDetailActivity extends BaseActivity {


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
    @BindView(R.id.pubishname)
    TextView pubishname;
    @BindView(R.id.fans)
    TextView fans;
    @BindView(R.id.dingyue_btn)
    RelativeLayout dingyueBtn;
    @BindView(R.id.rl_dingyue)
    RelativeLayout rlDingyue;
    @BindView(R.id.div2)
    View div2;
    @BindView(R.id.erweima)
    ImageView erweima;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.rl_pingjia)
    RelativeLayout rlPingjia;
    @BindView(R.id.guangbo_content)
    TextView guangboContent;
    @BindView(R.id.cha)
    ImageView cha;
    @BindView(R.id.message_list)
    ListView messageList;
    @BindView(R.id.jiadingyue)
    TextView jiadingyue;
    @BindView(R.id.yidingyue)
    TextView yidingyue;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;

    protected ImmersionBar mImmersionBar;
    @BindView(R.id.rl_hudong)
    RelativeLayout rlHudong;

    String name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_detail;
    }


    private String id;
    List<LiveDetailMessage> messagesList;
    LiveMessageDatailAdapter messageDatailAdapter;


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
        messageList.setAdapter(messageDatailAdapter);

        name = SHPUtils.getParame(getApplicationContext(), SHPUtils.NICKNAME);

        sendMessage(1);

        getChatList(id);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sendMessage(1);
        getChatList(id);
    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
//                mackToastLONG(intent.getStringExtra("data"), context);
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
                    detailPlayer.startWindowFullscreen(LiveDetailActivity.this, true, true);
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
        unregistLive();
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
        }

    }

    @OnClick({R.id.jiadingyue, R.id.yidingyue, R.id.rl_dingyue, R.id.cha, R.id.erweima, R.id.submit, R.id.shoucang, R.id.detail_back, R.id.tv_personalnum, R.id.action_btn, R.id.share_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_dingyue:
                SubscribeModel mo = new SubscribeModel();
                mo.name = model.publisher_name;
                mo.avatar = model.publisher_avatar;
                mo.id = model.publisher_id;
                mo.fans = model.publisher_fans;
                Intent intent = new Intent(LiveDetailActivity.this, SubscribeDetailActivity.class);
                intent.putExtra("isSu", model.is_subscribe);
                intent.putExtra("model", mo);
                startActivityForResult(intent, 888);
                break;
            case R.id.jiadingyue:
            case R.id.yidingyue:

                addSub();
                break;

            case R.id.cha:
                rlHudong.setVisibility(View.GONE);
                break;
            case R.id.submit:
                /*if (!StringUtil.isEmpty(content.getText().toString())) {
                    sendChat(content.getText().toString());

                    LiveDetailMessage message = new LiveDetailMessage();
                    message.avatar = SHPUtils.getParame(getApplicationContext(), SHPUtils.HEAD);
                    message.nickname = SHPUtils.getParame(getApplicationContext(), SHPUtils.NICKNAME);
                    message.content = content.getText().toString();
                    messagesList.add(0, message);
                    messageDatailAdapter.setData(messagesList);
                    content.setText("");
                }*/

                sendMessage(0);

                break;
            case R.id.share_icon:
                View viewRoot = LiveDetailActivity.this.findViewById(R.id.live_detail_root);
                ShareUtil u = new ShareUtil(LiveDetailActivity.this, viewRoot, liveModel.title, R.drawable.luyan_logo, liveModel.info, liveModel.share, 1);

                break;
            case R.id.shoucang:
                addCollect(id, 1);
                break;
            case R.id.detail_back:
                unregistLive();
                finish();
                break;
            case R.id.tv_personalnum:
                break;
            case R.id.action_btn:
                CommonUtils.startActWithData(LiveDetailActivity.this, ActionDetailActivity.class, "bean", liveModel);
                break;
            case R.id.erweima:
                getDialogAndshow();
                break;
        }
    }

    private void getChatList(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.95.224.184/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ChatService chatService = retrofit.create(ChatService.class);
        Call<Chat> call = chatService.getChatList(id);
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                Chat chat = response.body();
                Log.e("result", "onResponse: " + chat.toString());
                if (chat.getCode() == 200) {
                    List<Chat.DataBean.ListBean> list = chat.getData().getList();
                    for (int i = 0; i < list.size(); i++) {
                        Chat.DataBean.ListBean listBean = list.get(i);
                        sendChat(listBean.getContent());

                        LiveDetailMessage message = new LiveDetailMessage();
                        message.avatar = listBean.getAvatar();
                        message.nickname = listBean.getNickname();
                        message.content = listBean.getContent();
                        messagesList.add(0, message);
                        messageDatailAdapter.setData(messagesList);
                        content.setText("");
                    }
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                mackToastSHORT(t.getMessage(), LiveDetailActivity.this);
            }
        });
    }

    private void sendMessage(int type) {
        SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (type == 0) {
            if (!StringUtil.isEmpty(content.getText().toString())) {
                sendChat(content.getText().toString());

                LiveDetailMessage message = new LiveDetailMessage();
                message.avatar = SHPUtils.getParame(getApplicationContext(), SHPUtils.HEAD);
                message.nickname = SHPUtils.getParame(getApplicationContext(), SHPUtils.NICKNAME);
                message.content = content.getText().toString();
                messagesList.add(0, message);
                messageDatailAdapter.setData(messagesList);
                content.setText("");

                edit.putString("content", content.getText().toString());
                edit.apply();
            }
        } else if (type == 1) {
            String contentStr = sharedPreferences.getString("content", "");
            Log.e("result", "sendMessage: " + contentStr);
            if (!contentStr.equals("")) {
                sendChat(contentStr);

                LiveDetailMessage message = new LiveDetailMessage();
                message.avatar = SHPUtils.getParame(getApplicationContext(), SHPUtils.HEAD);
                message.nickname = SHPUtils.getParame(getApplicationContext(), SHPUtils.NICKNAME);
                message.content = contentStr;
                messagesList.add(0, message);
                messageDatailAdapter.setData(messagesList);
                content.setText("");
            }
        }
    }

    public void getDialogAndshow() {
        final Dialog dialog1 = new Dialog(activity, R.style.MyDialogDelect);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View v = mInflater.inflate(R.layout.live_erweima, null);
        ImageView img = (ImageView) v.findViewById(R.id.img);
        GlideUtils.loadImage(2, liveModel.qr_code, img);

        LinearLayout lin = (LinearLayout) v.findViewById(R.id.content_cont);
        dialog1.setContentView(lin, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        dialog1.show();
//		bbs_report.setContentView(dialog_report, new LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.WRAP_CONTENT,
//				LinearLayout.LayoutParams.WRAP_CONTENT));


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
                            CommonUtils.startAct(LiveDetailActivity.this, LoginActivity.class);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        dismissDialog();
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
                            CommonUtils.startAct(LiveDetailActivity.this, LoginActivity.class);
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
    private String bofangURL;

    public void getData() {

        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        live(id, AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<LiveDetailModel>, LiveDetailModel>() {


                    @Override
                    public void onResponse(LiveDetailModel shopBeen, String msg) {

                        liveModel = shopBeen;
                        tvPersonalnum.setText(shopBeen.pv + "人观看");
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

    String pub_id;

    LiveDetailModel model;

    private void setData(LiveDetailModel model) {
        tvTitle.setText(model.title);
        guangboContent.setText(model.title + ",欢迎互动");
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
        GlideUtils.loadImage(3, model.publisher_avatar, img);
        pubishname.setText(model.publisher_name);
        fans.setText(model.publisher_fans + "个粉丝");
        pub_id = model.publisher_id;

        this.model = model;
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
                detailPlayer.startWindowFullscreen(LiveDetailActivity.this, true, true);
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
}
