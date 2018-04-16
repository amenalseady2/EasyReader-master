package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.lives.LiveMainActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.GoodsDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.LiveDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.LiveReviewDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.LiveTrailerDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.NewsDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.ProjectDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.WebActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.HomeCaseAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.HomeHotSaleAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.HomeILikeAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.HomeLiveAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.HomeTopNewsAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.HomeWenZhangAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HomeModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bezierViewPager.BezierViewPager;
import com.BlackDiamond2010.hzs.ui.activity.lives.bezierViewPager.CardPagerAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.constants.StaticConstant;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.DisplayUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;
import com.BlackDiamond2010.hzs.view.GridViewForScrollView;
import com.BlackDiamond2010.hzs.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 */
public class HomeLiveFragment extends BaseFragment {


    @BindView(R.id.homeviewpager)
    BezierViewPager homeviewpager;
    @BindView(R.id.rl_card)
    RelativeLayout rlCard;
    @BindView(R.id.tv_live_more)
    TextView tvLiveMore;
    @BindView(R.id.gv_live)
    GridViewForScrollView gvLive;
    @BindView(R.id.tv_live_luyan)
    TextView tvLiveLuyan;
    @BindView(R.id.lv_luyan)
    ListViewForScrollView lvLuyan;
    @BindView(R.id.tv_anlimore)

    TextView tvAnlimore;
    @BindView(R.id.lv_anli)
    ListViewForScrollView lvAnli;
    @BindView(R.id.tv_change)
    TextView tvChange;
    @BindView(R.id.gv_youlike)
    GridViewForScrollView gvYoulike;
    @BindView(R.id.tv_hotsale_all)
    TextView tvHotsaleAll;
    @BindView(R.id.gv_hotsale)
    GridViewForScrollView gvHotsale;
    @BindView(R.id.gv_wenzhang)
    GridViewForScrollView gvWenzhang;
    @BindView(R.id.scroll)
    ScrollView scroll;

    @Override
    protected void loadData() {
        setState(AppConstants.STATE_SUCCESS);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_livie;
    }

    @Override
    protected void initView() {
        getData();
    }


    private void setData(final HomeModel model) {
//-------------新闻头条开始
        lvLuyan.setAdapter(new HomeTopNewsAdapter(model.headline));
        lvLuyan.setFocusable(false);
        lvLuyan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("id", model.headline.get(position).id);
                startActivity(intent);
            }
        });
        //---------------------新闻结束

        lvAnli.setAdapter(new HomeCaseAdapter(model.project));
        lvAnli.setFocusable(false);
        lvAnli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ProjectDetailActivity.class);
                intent.putExtra("id", model.project.get(position).id);
                startActivity(intent);
            }
        });
//案例结束
        //------------------- 猜你喜欢开始
        int w = (StaticConstant.sWidth - dp2px(40)) / 2;
        gvYoulike.setAdapter(new HomeILikeAdapter(w, model.like));
        gvYoulike.setFocusable(false);
        gvYoulike.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
                intent.putExtra("id", model.like.get(position).id);
                startActivity(intent);

            }
        });
        //----------------喜欢结束

        int w1 = (StaticConstant.sWidth - dp2px(60)) / 3;
        gvHotsale.setAdapter(new HomeHotSaleAdapter(w1, model.hot));
        gvHotsale.setFocusable(false);
        gvHotsale.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
                intent.putExtra("id", model.hot.get(position).id);
                startActivity(intent);
            }
        });
        gvWenzhang.setAdapter(new HomeWenZhangAdapter(model.article));
        gvWenzhang.setFocusable(false);
        gvWenzhang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), WebActivity.class);

                String title = intent.getStringExtra("title");
                String url = intent.getStringExtra("url");
                intent.putExtra("url", model.article.get(position).url);
                intent.putExtra("title", "精选文章");
                startActivity(intent);
            }
        });

        //-------------直播开始
        gvLive.setAdapter(new HomeLiveAdapter(model.live));
        gvLive.setFocusable(false);
        gvLive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                if (model.live.get(position).status == 0) {
                    CommonUtils.startActWithData(getContext(), LiveTrailerDetailActivity.class, "id", model.live.get(position).id + "");
                } else if (model.live.get(position).status == 1) {
                    CommonUtils.startActWithData(getContext(), LiveDetailActivity.class, "id", model.live.get(position).id + "");
                } else if (model.live.get(position).status == 2) {
                    CommonUtils.startActWithData(getContext(), LiveReviewDetailActivity.class, "id", model.live.get(position).id + "");
                }


            }
        });
        //-----------------------直播结束

        //------------------------
        initImgList(model.slider);
        int mWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
//        float heightRatio = 0.565f;  //高是宽的 0.565 ,根据图片比例
        float heightRatio = 0.499f;  //高是宽的 0.565 ,根据图片比例
        CardPagerAdapter cardAdapter = new CardPagerAdapter(getActivity(), model.slider);
        cardAdapter.addImgUrlList(imgList);
        //设置阴影大小，即vPage  左右两个图片相距边框  maxFactor + 0.3*CornerRadius   *2
        //设置阴影大小，即vPage 上下图片相距边框  maxFactor*1.5f + 0.3*CornerRadius
        int maxFactor = mWidth / 20;//控制两个pager的间距
        cardAdapter.setMaxElevationFactor(maxFactor);
        int mWidthPading = mWidth / 13;//控制边上pager显示的多少
        //因为我们adapter里的cardView CornerRadius已经写死为10dp，所以0.3*CornerRadius=3
        //设置Elevation之后，控件宽度要减去 (maxFactor + dp2px(3)) * heightRatio
        //heightMore 设置Elevation之后，控件高度 比  控件宽度* heightRatio  多出的部分
        float heightMore = (1.5f * maxFactor + dp2px(3)) - (maxFactor + dp2px(3)) * heightRatio;
        int mHeightPading = (int) (mWidthPading * heightRatio - heightMore);
//        BezierViewPager homeviewpager = (BezierViewPager) getActivity().findViewById(R.id.homeviewpager);


//
        cardAdapter.setOnCardItemClickListener(new CardPagerAdapter.OnCardItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", model.slider.get(position).url);
                intent.putExtra("title", model.slider.get(position).title);
                startActivity(intent);
            }
        });

        homeviewpager.setPadding(mWidthPading, mHeightPading, mWidthPading, mHeightPading);
        homeviewpager.setClipToPadding(false);
        homeviewpager.setAdapter(cardAdapter);
        homeviewpager.showTransformer(0.2f);
        homeviewpager.setFocusable(false);
        homeviewpager.setLayoutParams(new RelativeLayout.LayoutParams(mWidth, (int) (mWidth * heightRatio)));

        RelativeLayout rl_layout = (RelativeLayout) getActivity().findViewById(R.id.rl_card);
        rl_layout.setFocusable(true);
        rl_layout.setFocusableInTouchMode(true);
        rl_layout.requestFocus();


        //---------------------------广告结束------------

        setUnClick();
    }


    private int seconds = 0;
    private Timer timer;
    private MyTimerTask mTimerTask;
    private boolean flag;


    /**
     * 设置获取验证码按钮不能点击并开始倒计时
     */
    private void setUnClick() {
        flag = true;
        // getCheckBtn.setBackgroundResource(R.drawable.getkeycode);

        timer = new Timer(true);
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }
        mTimerTask = new MyTimerTask();
        timer.schedule(mTimerTask, 2000, 2000);


    }

    //    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    ++seconds;
                    homeviewpager.setCurrentItem(seconds % homeModel.slider.size());
                    break;
            }
        }

        ;
    };

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
    public void onDestroy() {
        super.onDestroy();
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private List<String> imgList;

    //添加轮播图url 封面
    public void initImgList(List<GoodsModel> list) {
        imgList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            imgList.add(list.get(i).cover);
        }
    }

    @Override
    protected void initInject() {

    }

    HomeModel homeModel;

    public void getData() {
        String clientId = SHPUtils.getParame(getContext(), SHPUtils.CLIENTID);
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        home(1, clientId, AndroidUtils.getAndroidId(getContext())),
                new HttpResultCall<HttpResult<HomeModel>, HomeModel>() {


                    @Override
                    public void onResponse(HomeModel shopBeen, String msg) {
                        homeModel = shopBeen;
                        setData(shopBeen);

                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                    }
                });
    }


    @OnClick({R.id.tv_live_more, R.id.tv_live_luyan, R.id.tv_anlimore, R.id.tv_change, R.id.tv_hotsale_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_live_more:
                ((LiveMainActivity) getActivity()).setCurrent(1);
                break;
            case R.id.tv_live_luyan:
                ((LiveMainActivity) getActivity()).setCurrent(3);
                break;
            case R.id.tv_anlimore:

                ((LiveMainActivity) getActivity()).setCurrent(2);
                break;
            case R.id.tv_change:

                change();
                break;
            case R.id.tv_hotsale_all:
                ((LiveMainActivity) getActivity()).setCurrent(4);
                break;
        }
    }

    private void likeAdapter(final List<GoodsModel> likeList) {
        int w = (StaticConstant.sWidth - DisplayUtil.dip2px(getContext(), 40)) / 2;
        gvYoulike.setAdapter(new HomeILikeAdapter(w, likeList));
        gvYoulike.setFocusable(false);
        gvYoulike.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
                intent.putExtra("id", likeList.get(position).id);
                startActivity(intent);
            }


        });
    }

    private void change() {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        change(AndroidUtils.getAndroidId(getContext())),
                new HttpResultCall<HttpResult<List<GoodsModel>>, List<GoodsModel>>() {


                    @Override
                    public void onResponse(List<GoodsModel> shopBeen, String msg) {
                        likeAdapter(shopBeen);
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                    }
                });
    }
}
