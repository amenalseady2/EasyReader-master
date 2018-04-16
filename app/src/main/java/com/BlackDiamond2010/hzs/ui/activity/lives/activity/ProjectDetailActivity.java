package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.MainAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ProjectDetailModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ProtInfoModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.ProjectDatailtwoFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.ProjectDetailFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.ShareUtil;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.CustomViewPager;
import com.BlackDiamond2010.hzs.view.gsy_videoplay.JumpUtils;
import com.BlackDiamond2010.hzs.view.gsy_videoplay.LandLayoutVideo;
import com.BlackDiamond2010.hzs.view.gsy_videoplay.SampleListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProjectDetailActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    CustomViewPager viewpager;
    //    @BindView(R.id.detail_player)
//    LandLayoutVideo detailPlayer;
    @BindView(R.id.tv_personalnum)
    TextView tvPersonalnum;
    @BindView(R.id.hangye)
    TextView hangye;
    @BindView(R.id.lianxiren)
    TextView lianxiren;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.ziliao)
    TextView ziliao;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.info)
    TextView info;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.youshi)
    TextView youshi;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.shiping)
    TextView shiping;
    @BindView(R.id.line4)
    View line4;
    @BindView(R.id.action_div)
    View actionDiv;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.bottom)
    TextView bottom;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.iv_bofang)
    ImageView ivBofang;
    @BindView(R.id.company_name)
    TextView companyName;
    String id;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_right2)
    ImageView ivRight2;
    @BindView(R.id.detail_player)
    LandLayoutVideo detailPlayer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_deatil;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_project_deatil);
        ButterKnife.bind(this);

        ivRight.setVisibility(View.VISIBLE);
        ivRight2.setVisibility(View.VISIBLE);

        id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    setResult(999, new Intent());
                    finish();
                }
            }
        });

        getData(AndroidUtils.getAndroidId(getApplicationContext()), id);
        ivRight.setBackgroundResource(R.drawable.fenxiang_anli);
    }

    @Override
    public void onBackPressed() {
        setResult(999, new Intent());
        finish();
        super.onBackPressed();
    }

    ProjectDetailModel projectDetailModel;

    //  獲取验证码
    public void getData(String devieId, String p_id) {
//        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().projectDetail(devieId, p_id), new HttpResultCall<HttpResult<ProjectDetailModel>, ProjectDetailModel>() {
            @Override
            public void onResponse(ProjectDetailModel result, String msg) {

                if (result.is_collection == 1) {
                    ivRight2.setImageResource(R.mipmap.soucang_pressed);
                } else {
                    ivRight2.setImageResource(R.mipmap.soucang_normal);
                }
                projectDetailModel = result;
                setData(result);
                toPlay("");
            }


            @Override
            public void onErr(String err, int status) {
            }
            @Override
            public void onCompleted() {
//                dismissDialog();
            }

        });
    }

    private String phoneNumString;

    private void setData(final ProjectDetailModel result) {
        ProtInfoModel model = result.info;
        companyName.setText(model.name);
        setTitle(model.name);
        hangye.setText("所属行业：" + model.trade_name);
        lianxiren.setText("项目顾问：" + model.contact);
        phone.setText("电话：" + model.phone);
        tvPersonalnum.setText(model.pv + "");
        time.setText("上线时间：" + model.online_time);
        phoneNumString = model.phone;
        ivBofang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 下一步用這個
                JumpUtils.goToVideoPlayer(ProjectDetailActivity.this, ivBofang, result.info.cover_video);
            }
        });
        //TODO  封面设置
        GlideUtils.loadImage(2, model.cover_video_img, ivCover);

        //-------------------------
        List<Fragment> list = new ArrayList<Fragment>();
        ProjectDetailFragment fragment1 = new ProjectDetailFragment(model.company_info, null);
//        ProjectDetailFragment fragment2 = new ProjectDetailFragment(null, model.industry_info);
        ProjectDetailFragment fragment3 = new ProjectDetailFragment(model.core_info, null);
        list.add(fragment1);
        list.add(new ProjectDatailtwoFragment(model.industry_info));
        list.add(fragment3);
//        list.add(videoFragment);

//        list.add(new ProjectDetailVideoFragment(model.video, getApplicationContext(), this));
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager(), list);
        viewpager.setAdapter(mainAdapter);
    }

    boolean flag;

    //	type	integer	是	1直播 2项目 3文章 4商品  收藏操作
    public void addCollect(String id, int type) {

//        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        addCollect(type, id, AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object ShopModel, String msg) {

                        flag = true;
//                        mackToastLONG(msg, getApplicationContext());
                        if ("已取消".equals(msg)) {
                            ivRight2.setImageResource(R.mipmap.soucang_normal);
                        } else if ("已收藏".equals(msg)) {
                            ivRight2.setImageResource(R.mipmap.soucang_pressed);
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                        if (status == 405) {
                            CommonUtils.startAct(ProjectDetailActivity.this, LoginActivity.class);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
//                        dismissDialog();
                    }
                });


    }


    @OnClick({R.id.ziliao, R.id.info, R.id.youshi, R.id.shiping, R.id.bottom, R.id.iv_right, R.id.iv_right2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ziliao:
                viewpager.setCurrentItem(0);
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.GONE);
                line4.setVisibility(View.GONE);
                break;
            case R.id.info:
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.VISIBLE);
                line3.setVisibility(View.GONE);
                line4.setVisibility(View.GONE);
                viewpager.setCurrentItem(1);
                break;
            case R.id.youshi:
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.VISIBLE);
                line4.setVisibility(View.GONE);
                viewpager.setCurrentItem(2);
                break;
            case R.id.shiping:
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.GONE);
                line4.setVisibility(View.VISIBLE);
                viewpager.setCurrentItem(3);
                break;
            case R.id.bottom:

                if (mPopupWindow == null) {
                    initPopupWindow();
                }
                mPopupWindow.showAtLocation(findViewById(R.id.project_datail_root),
                        Gravity.BOTTOM, 0, 0);
                break;
            case R.id.iv_right:
                View viewRoot = ProjectDetailActivity.this.findViewById(R.id.project_datail_root);
                ShareUtil u = new ShareUtil(ProjectDetailActivity.this, viewRoot, projectDetailModel.info.name, R.drawable.luyan_logo, projectDetailModel.info.company_info, projectDetailModel.info.share, 1);
                break;
            case R.id.iv_right2:
                addCollect(id, 2);
                break;
        }
    }

    /**
     * android 授权
     */
    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 888;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                call(Intent.ACTION_CALL, phoneNumString);
            } else {
                // Permission Denied
                Toast.makeText(ProjectDetailActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void call(String action, String phoneNum) {
        if (phoneNum != null && phoneNum.trim().length() > 0) {
            //这里"tel:"+电话号码 是固定格式，系统一看是以"tel:"开头的，就知道后面应该是电话号码。
            Intent intent = new Intent(action, Uri.parse("tel:" + phoneNum.trim()));
            startActivity(intent);//调用上面这个intent实现拨号
        } else {
            Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_LONG).show();
        }
    }

    private PopupWindow mPopupWindow;
    private View popView;

    private void initPopupWindow() {
        if (popView == null) {
            popView = LayoutInflater.from(this).inflate(R.layout.project_popwindow,
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
        popView.findViewById(R.id.cancle).setOnClickListener(click);
        popView.findViewById(R.id.rl_dianhua).setOnClickListener(click);
        popView.findViewById(R.id.funtions).setOnClickListener(click);
        TextView tvT = (TextView) popView.findViewById(R.id.fuzeren_name);
        tvT.setText("负责人姓名：" + projectDetailModel.info.pm_name);

        TextView tvDinah = (TextView) popView.findViewById(R.id.tv_dianhua);
        tvDinah.setText("" + projectDetailModel.info.pm_phone);

        TextView tvL = (TextView) popView.findViewById(R.id.shangchangliyu);
        tvL.setText("擅长领域：" + projectDetailModel.info.good_at);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.funtions:
                    mPopupWindow.dismiss();
                    break;
                case R.id.cancle:
                    mPopupWindow.dismiss();
                    break;
                case R.id.rl_dianhua:
                    if (ContextCompat.checkSelfPermission(ProjectDetailActivity.this,
                            Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(ProjectDetailActivity.this,
                                Manifest.permission.READ_CONTACTS)) {
                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {
                            ActivityCompat.requestPermissions(ProjectDetailActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        }//申请权限
                    } else {
                        call(Intent.ACTION_CALL, projectDetailModel.info.pm_phone);
                    }
                    break;
            }
        }
    };





    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;

    private void toPlay(String url) {
//        String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        detailPlayer.setUp(projectDetailModel.info.cover_video, false, null, "");

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
        GlideUtils.loadImage(2, projectDetailModel.info.cover_video_img, imageView);
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
                detailPlayer.startWindowFullscreen(ProjectDetailActivity.this, true, true);
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

    private void resolveNormalVideoUI() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getTitleTextView().setText("");
        detailPlayer.getBackButton().setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }
}
