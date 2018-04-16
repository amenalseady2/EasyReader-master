package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.MainAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.AlbumBean;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodDetailModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.constants.StaticConstant;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.PingjiaFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.XuZhiFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.goodsDetailFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.ShareUtil;
import com.BlackDiamond2010.hzs.ui.fragment.home.HomeFragment;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.AutoScrollViewPager;
import com.BlackDiamond2010.hzs.view.XImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsDetailActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.goods_viewpager)
    AutoScrollViewPager goodsViewpager;
    @BindView(R.id.ll_zhishiqi)
    LinearLayout llZhishiqi;
    @BindView(R.id.home_viewpager_rela)
    RelativeLayout homeViewpagerRela;
    @BindView(R.id.vipprice)
    TextView vipprice;
    @BindView(R.id.saleprice)
    TextView saleprice;
    @BindView(R.id.or_price)
    TextView orPrice;
    @BindView(R.id.kuaidi)
    TextView kuaidi;
    @BindView(R.id.month_sale)
    TextView monthSale;
    @BindView(R.id.kucun)
    TextView kucun;
    @BindView(R.id.rl_head)
    LinearLayout rlHead;
    @BindView(R.id.xiangqing)
    TextView xiangqing;
    @BindView(R.id.xuzhi)
    TextView xuzhi;
    @BindView(R.id.pingjia)
    TextView pingjia;
    @BindView(R.id.action_div)
    View actionDiv;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.bottom_line)
    View bottomLine;
    @BindView(R.id.bottom)
    RelativeLayout bottom;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.buy)
    TextView buy;
    @BindView(R.id.join)
    TextView join;
    @BindView(R.id.togouwuche)
    TextView togouwuche;
    String id;
    GoodsModel mGoodsModel;
    @BindView(R.id.iv_right2)
    ImageView ivRight2;
    @BindView(R.id.iv_right)
    ImageView ivRight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("商品详情");
        id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");

        ivRight2.setVisibility(View.VISIBLE);
        ivRight2.setBackgroundResource(R.drawable.fenxiang_anli);
        getData(id);


    }


    private void getData(String id) {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        goodsDetail(AndroidUtils.getAndroidId(this), 2, id),
                new HttpResultCall<HttpResult<GoodDetailModel>, GoodDetailModel>() {


                    @Override
                    public void onResponse(GoodDetailModel shopBeen, String msg) {
                        setData(shopBeen);

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

    private void addShopCart(String id) {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        addordeleteShopcart(1, id, "", AndroidUtils.getAndroidId(this)),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object shopBeen, String msg) {
                        mackToastLONG(msg, getApplicationContext());

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


    HomeFragment home;

    GoodDetailModel model;

    private void setData(GoodDetailModel model) {
        this.model = model;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(StaticConstant.sWidth,StaticConstant.sWidth);
        homeViewpagerRela.setLayoutParams(lp);

        //-------------下面三个fragment------------
        List<Fragment> mFragments = new ArrayList<Fragment>();

//        mTitleList.add("排行榜");
//        mTitleList.add("最新电影");


        mFragments.add(new goodsDetailFragment(model.detail.detail));
        List<String> xuzhiList = new ArrayList<>();
        xuzhiList.add(model.detail.info1);
        xuzhiList.add(model.detail.info2);
        xuzhiList.add(model.detail.info3);
        xuzhiList.add(model.detail.info4);
        mFragments.add(new XuZhiFragment(xuzhiList));
        mFragments.add(new PingjiaFragment(id));
        home = new HomeFragment();
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager(), mFragments);
        viewpager.setAdapter(mainAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        xiangqing.setTextColor(getResources().getColor(R.color.red));
                        xuzhi.setTextColor(getResources().getColor(R.color.black));
                        pingjia.setTextColor(getResources().getColor(R.color.black));


                        break;
                    case 1:
                        xiangqing.setTextColor(getResources().getColor(R.color.black));
                        xuzhi.setTextColor(getResources().getColor(R.color.red));
                        pingjia.setTextColor(getResources().getColor(R.color.black));

                        break;
                    case 2:
                        xiangqing.setTextColor(getResources().getColor(R.color.black));
                        xuzhi.setTextColor(getResources().getColor(R.color.black));
                        pingjia.setTextColor(getResources().getColor(R.color.red));
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //封装下单的model
        mGoodsModel = new GoodsModel();
        mGoodsModel.id = model.detail.id;
        mGoodsModel.product_id = model.detail.id;
        mGoodsModel.num = 1;
        mGoodsModel.name = model.detail.name;
        mGoodsModel.price = model.detail.price;

        //广告业
        List<String> urlList = new ArrayList<>();
        if (model.detail.slider != null && !"".equals(model.detail.slider)) {
            if (model.detail.slider.contains(",")) {
                String[] imgs = model.detail.slider.split(",");
                for (int i = 0; i < imgs.length; i++) {
                    urlList.add(imgs[i]);
                    if (i == 0) {
                        mGoodsModel.cover = imgs[0];
                    }
                }
            } else {
                urlList.add(model.detail.slider);
                mGoodsModel.cover = model.detail.slider;
            }
        }
        advertisement(urlList);

        //-------------------
        title.setText(model.detail.name);
        orPrice.setText("￥" + model.detail.local_price);
        orPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        vipprice.setText("VIP会员价：￥" + model.detail.vip_price);
        saleprice.setText("￥" + model.detail.price);
        kuaidi.setText("快递：" + model.detail.freight);
        monthSale.setText("月售" + model.detail.sale_num + "件");
        kucun.setText("库存" + model.detail.stock + "件");

        if (model.is_collection == 1) {
            ivRight.setImageResource(R.mipmap.soucang_pressed);
        } else {
            ivRight.setImageResource(R.mipmap.soucang_normal);
        }
        ivRight.setVisibility(View.VISIBLE);


    }

    @OnClick({R.id.xiangqing, R.id.xuzhi, R.id.pingjia, R.id.buy, R.id.join, R.id.togouwuche, R.id.iv_right,R.id.iv_right2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_right2:
                if (model.detail.share == null || "".equals(model.detail.share)){
                    mackToastSHORT("链接突然消失，大侠正在火速搜寻中",GoodsDetailActivity.this);
                    return;
                }

                View viewRoot = GoodsDetailActivity.this.findViewById(R.id.goodsdetail);
                ShareUtil u = new ShareUtil(GoodsDetailActivity.this, viewRoot, model.detail.name, R.drawable.luyan_logo, " ", model.detail.share, 1);
                break;

            case R.id.iv_right:
                addCollect(id, 4);
                break;

            case R.id.togouwuche:
                Intent intentT = new Intent(GoodsDetailActivity.this, ShopCartActivity.class);
                startActivity(intentT);

                break;

            case R.id.join:
                //TODO  jiaru gouwuche
                addShopCart(id);
                break;

            case R.id.buy:
                ArrayList<GoodsModel> goodsListid = new ArrayList<>();
                goodsListid.add(mGoodsModel);
                Intent intent = new Intent(GoodsDetailActivity.this, SuerOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", goodsListid);
                intent.putExtra("bundle", bundle);
                startActivity(intent);


                break;
            case R.id.xiangqing:
//                home.setCurrentP(0);
                viewpager.setCurrentItem(0);


                break;
            case R.id.xuzhi:
//                home.setCurrentP(0);
                viewpager.setCurrentItem(1);


                break;
            case R.id.pingjia:
                //                home.setCurrentP(0);
                viewpager.setCurrentItem(2);
                break;
        }
    }


    private ArrayList<AlbumBean> mAlbumList;
    private List<ImageView> dianList = new ArrayList<>();
    List<XImageView> imgViewList;

    /**
     * Advertisement
     * 商品详情头部图片轮播图
     */
    private void advertisement(List<String> imgList) {
        //测试数据
        mAlbumList = new ArrayList<>();
        imgViewList = new ArrayList<>();
        for (int i = 0; i < imgList.size(); i++) {
            AlbumBean bean = new AlbumBean();
            bean.url = imgList.get(i);
            mAlbumList.add(bean);
            if (imgList.size() > 1) {
                ImageView imgD = new ImageView(this);
                dianList.add(imgD);
            }

        }
        goodsViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position % mAlbumList.size());


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        goodsViewpager.setAdapter(new SamplePagerAdapter());
//        goodsViewpager.setInterval(3000);
//        goodsViewpager.startAutoScroll();
//        goodsViewpager.setAutoScrollDurationFactor(3f);
//        goodsViewpager.stopAutoScroll();
        goodsViewpager.setCurrentItem(0);
        if (imgList.size() > 1) {
            setIndicator(0);
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
                            ivRight.setImageResource(R.mipmap.soucang_normal);
                        } else if ("已收藏".equals(msg)) {
                            ivRight.setImageResource(R.mipmap.soucang_pressed);
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                        if (status == 405) {
                            CommonUtils.startAct(GoodsDetailActivity.this, LoginActivity.class);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        dismissDialog();
                    }
                });


    }


    private class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mAlbumList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            View view = View.inflate(container.getContext(), R.layout.scoregoods_pic_item, null);
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ImageView photoView = (ImageView) view.findViewById(R.id.pull_img);
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            GlideUtils.loadImage(3, mAlbumList.get(position).url, photoView);
            container.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAlbumList != null && mAlbumList.size() > 0) {
                    }
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    /**
     * 设置广告位下面的点
     * 每次做循环，让选中图片id等于选中线id 传入position多少点就变
     *
     * @param position 下表
     *                 广告点线性布局
     */
    public void setIndicator(int position) {
        llZhishiqi.removeAllViews();
        for (int i = 0; i < mAlbumList.size(); i++) {
            ImageView img = dianList.get(i);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = 10;
            img.setLayoutParams(lp);
            if (i == position) {
                img.setImageResource(R.mipmap.choiced_circle);
            } else {
                img.setImageResource(R.mipmap.unchioce_circle);
            }
            // 给线性加点
            llZhishiqi.addView(img);
        }
    }


}
