package com.BlackDiamond2010.hzs.ui.activity.lives;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.BuyVipActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.LoginActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.MyAddressActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.MyCollectionActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.MyMessageActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.MyOrderActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.MyServerActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.MySubscribeActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.SearchActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.SettingActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.ShopCartActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.UserInfoActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.MainAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Risgter;
import com.BlackDiamond2010.hzs.ui.activity.lives.constants.StaticConstant;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.HomeLiveFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.LiveFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.ProjectFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.ShopFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.TopNewFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.pushService.MyIntentService;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.DisplayUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.MyDialog;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.ShareUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.CircleImageView;
import com.BlackDiamond2010.hzs.view.CustomViewPager;
import com.igexin.sdk.PushManager;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LiveMainActivity extends BaseActivity {
    @BindView(R.id.dl_layout)
    DrawerLayout dlLayout;
    @BindView(R.id.tv_home)
    RadioButton tvHome;
    @BindView(R.id.tv_live)
    RadioButton tvLive;
    @BindView(R.id.tv_project)
    RadioButton tvProject;
    @BindView(R.id.tv_toutiao)
    RadioButton tvToutiao;
    @BindView(R.id.tv_shop)
    RadioButton tvShop;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_wanshang)
    TextView tvWanshang;
    @BindView(R.id.buy_vip)
    TextView buyVip;
    @BindView(R.id.my_dingyue)
    TextView myDingyue;
    @BindView(R.id.my_collect)
    TextView myCollect;
    @BindView(R.id.my_order)
    TextView myOrder;
    @BindView(R.id.addrsss)
    TextView addrsss;
    @BindView(R.id.server)
    TextView server;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.yaoqing)
    TextView yaoqing;
    @BindView(R.id.about_us)
    TextView aboutUs;
    @BindView(R.id.setting)
    TextView setting;
    @BindView(R.id.loginout)
    TextView loginout;
    @BindView(R.id.rl_personal_center)
    RelativeLayout rlPersonalCenter;
    @BindView(R.id.rigth_icon)
    ImageView rigthIcon;
    @BindView(R.id.rigth_icon2)
    ImageView rigthIcon2;
    @BindView(R.id.vp_content)
    CustomViewPager viewPager;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_search)
    TextView tvSearch;

    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.tv_maintitle)
    TextView tvMaintitle;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.wanzhengdu)
    View wanzhengdu;
    @BindView(R.id.bottom)
    LinearLayout bottom;
    @BindView(R.id.ppp)
    TextView ppp;
    @BindView(R.id.iv_vip)
    ImageView ivVip;
    @BindView(R.id.userinfo_rl)
    RelativeLayout userinfoRl;
    @BindView(R.id.dian_hua)
    TextView dianHua;
    @BindView(R.id.rl_lianxiwomen)
    RelativeLayout rlLianxiwomen;
    private MainAdapter mainAdapter;
    private int mPosition, type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getScreenWH();

        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), MyIntentService.class);
        initView();
        initClientId();
        getShareUrl();

        Bundle bundle = getIntent().getBundleExtra("message");
        if (bundle != null) {
            //如果bundle存在，取出其中的参数，启动DetailActivity
            CommonUtils.startAct(this, MyMessageActivity.class);
        }
    }


    private void getShareUrl() {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().share(AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<Risgter>, Risgter>() {

                    @Override
                    public void onResponse(Risgter result, String msg) {

                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.SHAREURL, result.url);
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

    private void initClientId() {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().uploadClientId(1,

                AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext()), SHPUtils.getParame(getApplicationContext(), SHPUtils.CLIENTID)),
                new HttpResultCall<HttpResult<Object>, Object>() {

                    @Override
                    public void onResponse(Object result, String msg) {
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

    private void getScreenWH() {
        if (StaticConstant.sWidth <= 0 && StaticConstant.sHeight <= 0) {
            DisplayMetrics metric = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metric);
            StaticConstant.sWidth = metric.widthPixels; // 屏幕宽度（像素）
            StaticConstant.sHeight = metric.heightPixels; // 屏幕高度（像素）
        }
    }

    private void initView() {
        List<Fragment> list = new ArrayList<Fragment>();

        viewPager.setOffscreenPageLimit(0);// 预加载
        HomeLiveFragment homeFragment = new HomeLiveFragment();
        LiveFragment liveFragment = new LiveFragment();
        ProjectFragment projectFragment = new ProjectFragment();
        TopNewFragment topNewFragment = new TopNewFragment();

        ShopFragment shopFragment = new ShopFragment();
        // moreFragment = new MoreFragment();
        list.add(homeFragment);
        list.add(liveFragment);
        list.add(projectFragment);
        list.add(topNewFragment);
        list.add(shopFragment);
        // list.add(moreFragment);

        if (mainAdapter == null) {
            mainAdapter = new MainAdapter(getSupportFragmentManager(), list);
        }
        viewPager.setAdapter(mainAdapter);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setStatusBar(R.color.red, false, R.color.colorWhite);
        //侧边栏消息
        if (!StringUtil.isEmpty(SHPUtils.getParame(getApplicationContext(), SHPUtils.HEAD))) {
            GlideUtils.loadImage(3, SHPUtils.getParame(getApplicationContext(), SHPUtils.HEAD), ivHead);
        }
        if (!StringUtil.isEmpty(SHPUtils.getParame(getApplicationContext(), SHPUtils.NICKNAME))) {
            tvUsername.setText(SHPUtils.getParame(getApplicationContext(), SHPUtils.NICKNAME));
        }
        if (StringUtil.isEmpty(SHPUtils.getParame(getApplicationContext(), SHPUtils.TOKEN))) {
            tvUsername.setText("请登录");
        }
        String p = SHPUtils.getParame(this, SHPUtils.PERCENT);

        if (p != null) {
            ppp.setText(p + "%");
            int w = DisplayUtil.dip2px(getApplicationContext(), (Integer.valueOf(p) * 40 / 100));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT);
            wanzhengdu.setLayoutParams(lp);
        }

        String isVip = SHPUtils.getParame(this, SHPUtils.ISVIP);


        if (isVip != null && "1".equals(isVip)) {
            // 是会员
            ivVip.setVisibility(View.VISIBLE);
            buyVip.setText("钻石VIP");
        } else {
            //不是会员
            ivVip.setVisibility(View.VISIBLE);
            buyVip.setText("购买VIP");
        }


    }




    @OnClick({R.id.rigth_icon, R.id.rigth_icon2, R.id.rl_search, R.id.tv_username, R.id.rl_personal_center, R.id.tv_home, R.id.tv_live, R.id.tv_project, R.id.tv_toutiao, R.id.tv_shop, R.id.iv_head, R.id.userinfo_rl, R.id.buy_vip, R.id.my_dingyue, R.id.my_collect, R.id.my_order, R.id.addrsss, R.id.server, R.id.message, R.id.yaoqing, R.id.rl_lianxiwomen, R.id.setting, R.id.loginout})
    public void onViewClicked(View view) {

//        dlLayout.closeDrawers();
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rigth_icon:
                intent.setClass(this, SearchActivity.class);

                if (mPosition == 1) {
                    type = 1;
                } else if (mPosition == 4) {
                    type = 4;
                }
                intent.putExtra("type", type);
                startActivity(intent);
                break;
            case R.id.rigth_icon2:
                intent.setClass(this, ShopCartActivity.class);
                startActivity(intent);
                break;

            case R.id.rl_search:
                intent.setClass(this, SearchActivity.class);

                if (mPosition == 0) {
                    type = 1;
                } else if (mPosition == 3) {
                    type = 3;
                } else if (mPosition == 4) {
                    type = 4;
                } else if (mPosition == 2) {
                    type = 2;
                }
                intent.putExtra("type", type);
                startActivity(intent);
                break;
            case R.id.tv_username:
//                intent.setClass(this, LoginActivity.class);
//                startActivity(intent);
//                startActivityForResult(new Intent(this,UserInfoActivity.class),888);
                break;
            case R.id.rl_personal_center:
                dlLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.tv_home:

                rlSearch.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_live:
                viewPager.setCurrentItem(1);
                rlSearch.setVisibility(View.GONE);
                tvMaintitle.setText("路演直播");
                rigthIcon2.setVisibility(View.GONE);
                break;
            case R.id.tv_project:
                rlSearch.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(2);

                break;
            case R.id.tv_toutiao:
                rlSearch.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(3);
                break;
            case R.id.tv_shop:
                rigthIcon2.setVisibility(View.VISIBLE);
                rlSearch.setVisibility(View.GONE);
                tvMaintitle.setText("文创商城");
                viewPager.setCurrentItem(4);
                break;
            case R.id.iv_head:
//                startActivityForResult(new Intent(this,UserInfoActivity.class),888);
                break;
            case R.id.userinfo_rl:
//                CommonUtils.startAct(this, UserInfoActivity.class);
                startActivityForResult(new Intent(this, UserInfoActivity.class), 888);
                break;
            case R.id.buy_vip:
                intent.setClass(this, BuyVipActivity.class);
                startActivity(intent);
                break;
            case R.id.my_dingyue:
                intent.setClass(this, MySubscribeActivity.class);
                startActivity(intent);
                break;
            case R.id.my_collect:
                intent.setClass(this, MyCollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.my_order:
                intent.setClass(this, MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.addrsss:
                intent.setClass(this, MyAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.server:
                intent.setClass(this, MyServerActivity.class);
                startActivity(intent);
                break;
            case R.id.message:
                intent.setClass(this, MyMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.yaoqing:
//                intent.setClass(this, GoodsDetailActivity.class);
//                startActivity(intent);
                getDialogAndshow();

                break;
            case R.id.rl_lianxiwomen:

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
                    call(Intent.ACTION_CALL, "4001782998");
                }


                break;
            case R.id.setting:
                intent.setClass(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.loginout:
                MyDialog.getDialogAndshow(LiveMainActivity.this, new MyDialog.DialogCallBack() {
                    @Override
                    public void dialogSure() {

                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.HEAD, null);
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.NICKNAME, null);

                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.TOKEN, null);
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.PERCENT, null);

                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.CONPANY, null);
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.ADDRESSID, null);

                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.ADDRESS, null);

                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.ISVIP, null);
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.REAL_NAME, null);

                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.MESSAGE, null);
                        SHPUtils.saveParame(getApplicationContext(), SHPUtils.ORDER_MESSAGE, null);
                        CommonUtils.startAct(LiveMainActivity.this, LoginActivity.class);
                        finish();
                    }

                    @Override
                    public void dialogCancle() {

                    }
                }, "确定退出当前账号？", "确定", "取消", null);
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
                call(Intent.ACTION_CALL, "4001782998");
            } else {
                // Permission Denied
                Toast.makeText(LiveMainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
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

    public void getDialogAndshow() {
        final Dialog dialog1 = new Dialog(activity, R.style.MyDialogDelect);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View v = mInflater.inflate(R.layout.yaoqing_dialog, null);

        LinearLayout lin = (LinearLayout) v.findViewById(R.id.content_cont);
        dialog1.setContentView(lin, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ImageView img = (ImageView) v.findViewById(R.id.erwema);
        if (!StringUtil.isEmpty(SHPUtils.getParame(getApplicationContext(), SHPUtils.INVATATION))) {
//            GlideUtils.loadImage(3, SHPUtils.getParame(getApplicationContext(), "http://cdn.kanjian2020.com/981521534328.jpg"), img);
//            img.setImageResource(R.mipmap.erweima_ss);
        }

        v.findViewById(R.id.weichat).setOnClickListener(myListener);
        v.findViewById(R.id.qq).setOnClickListener(myListener);
        v.findViewById(R.id.weibo).setOnClickListener(myListener);
        dialog1.show();

    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View view = LiveMainActivity.this.findViewById(R.id.dl_layout);
            ShareUtil u = new ShareUtil(LiveMainActivity.this, view, "一起加入路演大侠吧", R.drawable.luyan_logo, "路演大侠Android端App下载链接", SHPUtils.getParame(getApplicationContext(), SHPUtils.SHAREURL), 0);
            if (v.getId() == R.id.weichat) {
                u.ShareOne(SHARE_MEDIA.WEIXIN);
            } else if (v.getId() == R.id.qq) {
                u.ShareOne(SHARE_MEDIA.QQ);
            } else if (v.getId() == R.id.weibo) {
                u.ShareOne(SHARE_MEDIA.SINA);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtil.isEmpty(SHPUtils.getParame(getApplicationContext(), SHPUtils.HEAD))) {
            GlideUtils.loadImage(3, SHPUtils.getParame(getApplicationContext(), SHPUtils.HEAD), ivHead);
        }
        if (!StringUtil.isEmpty(SHPUtils.getParame(getApplicationContext(), SHPUtils.NICKNAME))) {
            tvUsername.setText(SHPUtils.getParame(getApplicationContext(), SHPUtils.NICKNAME));
        }

        String isVip = SHPUtils.getParame(this, SHPUtils.ISVIP);


        if (isVip != null && "1".equals(isVip)) {
            // 是会员
            ivVip.setVisibility(View.VISIBLE);
            buyVip.setText("钻石VIP");
        } else {
            //不是会员
            ivVip.setVisibility(View.GONE);
            buyVip.setText("购买VIP");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

        if (requestCode == 888) {
            if (!StringUtil.isEmpty(SHPUtils.getParame(getApplicationContext(), SHPUtils.HEAD))) {
                GlideUtils.loadImage(3, SHPUtils.getParame(getApplicationContext(), SHPUtils.HEAD), ivHead);
            }
            if (!StringUtil.isEmpty(SHPUtils.getParame(getApplicationContext(), SHPUtils.NICKNAME))) {
                tvUsername.setText(SHPUtils.getParame(getApplicationContext(), SHPUtils.NICKNAME));
            }

            String p = SHPUtils.getParame(this, SHPUtils.PERCENT);

            if (p != null) {
                ppp.setText(p + "%");
                int w = DisplayUtil.dip2px(getApplicationContext(), (Integer.valueOf(p) * 40 / 100));
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT);
                wanzhengdu.setLayoutParams(lp);
            }
        }
    }

    @OnClick(R.id.rigth_icon)
    public void onViewClicked() {
    }


    public void setCurrent(int mPosition) {

        viewPager.setCurrentItem(mPosition);

        switch (mPosition) {
            case 1:
                tvLive.setChecked(true);
                break;
            case 2:
                tvProject.setChecked(true);
                break;
            case 3:
                tvToutiao.setChecked(true);
                break;
            case 4:
                tvShop.setChecked(true);
                break;
        }

    }
}
