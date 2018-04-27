package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.bean.ProductDetails.Product;
import com.BlackDiamond2010.hzs.http.service.ProductDetailService;
import com.BlackDiamond2010.hzs.http.service.ShopCartService;
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
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.ShareUtil;
import com.BlackDiamond2010.hzs.ui.fragment.home.HomeFragment;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.AutoScrollViewPager;
import com.BlackDiamond2010.hzs.view.XImageView;
import com.rey.material.app.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    Product product = null;

    //选中的颜色
    String select_color_type = null;

    //选中的尺寸
    String select_size_type = null;

    //总价
    double total = 0;

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

        getProductData(id);

    }

    private void getProductData(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.95.224.184/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductDetailService productDetailService = retrofit.create(ProductDetailService.class);
        Call<Product> call = productDetailService.product(id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.95.224.184/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String spec = "大小," + select_size_type + " " + "颜色," + select_color_type;
        ShopCartService shopCartService = retrofit.create(ShopCartService.class);
        Call<ResponseBody> call = shopCartService.addCart(SHPUtils.getParame(getApplicationContext(), SHPUtils.TOKEN),
                Integer.parseInt(id), "1", spec, defalut_num);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    if (code == 200) {
                        Intent intent = new Intent(GoodsDetailActivity.this, ShopCartActivity.class);
                        startActivity(intent);
                    }
                    mackToastLONG(msg, GoodsDetailActivity.this);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mackToastSHORT(t.getMessage(), GoodsDetailActivity.this);
            }
        });
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

    BottomSheetDialog bottomInterDialog;

    String imgFirst;

    int defalut_num = 1;

    private void setData(GoodDetailModel model) {
        this.model = model;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(StaticConstant.sWidth, StaticConstant.sWidth);
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
                imgFirst = model.detail.slider;
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

    @OnClick({R.id.xiangqing, R.id.xuzhi, R.id.pingjia, R.id.buy, R.id.join, R.id.togouwuche, R.id.iv_right, R.id.iv_right2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_right2:
                if (model.detail.share == null || "".equals(model.detail.share)) {
                    mackToastSHORT("链接突然消失，大侠正在火速搜寻中", GoodsDetailActivity.this);
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
                //弹出选择商品
                showProduct(mGoodsModel, 0);
                //addShopCart(id);
                break;

            case R.id.buy:
                //弹出选择商品
                showProduct(mGoodsModel, 1);
                /*ArrayList<GoodsModel> goodsListid = new ArrayList<>();
                goodsListid.add(mGoodsModel);
                Intent intent = new Intent(GoodsDetailActivity.this, SuerOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", goodsListid);
                intent.putExtra("bundle", bundle);
                startActivity(intent);*/


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

    private void showProduct(final GoodsModel mGoodsModel, final Integer type) {
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_product_choose, null);
        bottomInterDialog = new BottomSheetDialog(GoodsDetailActivity.this);
        bottomInterDialog
                .contentView(view)
                .inDuration(500)
                .outDuration(500)
                .inInterpolator(new BounceInterpolator())
                .outInterpolator(new AnticipateInterpolator())
                .cancelable(true)
                .show();

        ImageView bottom_cha = view.findViewById(R.id.bottom_cha);
        ImageView bottom_product_img = view.findViewById(R.id.bottom_product_img);
        Picasso.with(this).load(imgFirst).error(R.mipmap.luyan_logo).placeholder(R.mipmap.luyan_logo).into(bottom_product_img);

        TextView title = view.findViewById(R.id.textView5);
        if (model.detail.name.length() > 15) {
            model.detail.name = model.detail.name.substring(0, 15) + "...";
        }
        title.setText(model.detail.name);
        TextView bottom_price = view.findViewById(R.id.bottom_price);
        bottom_price.setText("￥" + model.detail.price);
        TextView bottom_num = view.findViewById(R.id.bottom_num);
        bottom_num.setText("库存" + model.detail.stock + "件");


        //颜色
        final LinearLayout color_lin = view.findViewById(R.id.color_lin);
        String color_str = product.getData().getDetail().getSpec().get(1).getItem();
        if (!color_str.equals("")) {
            final String[] color_types_str = color_str.split(",");
            final Button color_types[] = new Button[color_types_str.length];
            for (int i = 0; i < color_types_str.length; i++) {
                final LinearLayout ll = (LinearLayout) LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.bottom_color_button_style, null);
                ll.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));//此处设置权重
                final Button btn = ll.findViewById(R.id.color_btn);
                btn.setText(color_types_str[i]);
                btn.setTextColor(Color.rgb(51, 51, 51));
                color_types[i] = btn;
                color_lin.addView(ll);
            }
            for (int j = 0; j < color_lin.getChildCount(); j++) {
                final Button bt = color_lin.getChildAt(j).findViewById(R.id.color_btn);
                final int temp = j;
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setColorType(temp, color_types, color_types_str);
                    }
                });
            }
        } else {
            TextView textView = new TextView(this);
            textView.setText("暂无");
            textView.setTextSize(16);
            color_lin.addView(textView);
        }


        //尺寸
        final LinearLayout size_lin = view.findViewById(R.id.size_lin);
        String size_str = product.getData().getDetail().getSpec().get(0).getItem();
        if (!size_str.equals("")) {
            final String[] size_type_str = size_str.split(",");
            final Button size_types[] = new Button[size_type_str.length];
            for (int i = 0; i < size_type_str.length; i++) {
                final LinearLayout ll = (LinearLayout) LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.bottom_size_button_style, null);
                ll.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));//此处设置权重
                final Button btn = ll.findViewById(R.id.size_btn);
                btn.setText(size_type_str[i]);
                btn.setTextColor(Color.rgb(51, 51, 51));
                size_types[i] = btn;
                size_lin.addView(ll);
            }
            for (int j = 0; j < size_lin.getChildCount(); j++) {
                final Button bt = size_lin.getChildAt(j).findViewById(R.id.size_btn);
                final int temp = j;
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setSizeType(temp, size_types, size_type_str);
                    }
                });
            }
        } else {
            TextView textView = new TextView(this);
            textView.setText("暂无");
            textView.setTextSize(16);
            size_lin.addView(textView);
        }

        Button reduce = view.findViewById(R.id.reduce);
        final Button buy_num = view.findViewById(R.id.buy_num);
        Button add = view.findViewById(R.id.add_btn);

        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defalut_num == 1) {
                    Toast.makeText(GoodsDetailActivity.this, "最低买1份", Toast.LENGTH_SHORT).show();
                } else {
                    defalut_num -= 1;
                    buy_num.setText(defalut_num + "");
                    total = Double.parseDouble(model.detail.price) * defalut_num;
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defalut_num += 1;
                buy_num.setText(defalut_num + "");
                total = Double.parseDouble(model.detail.price) * defalut_num;
            }
        });

        Button bottom_ok = view.findViewById(R.id.bottom_ok);
        bottom_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                 * 根据0,1判断是加入购物车还是立即购买
                 * */
                if (type == 0) {
                    addShopCart(id);
                } else if (type == 1) {
                    ArrayList<GoodsModel> goodsListid = new ArrayList<>();
                    goodsListid.add(mGoodsModel);
                    Intent intent = new Intent(GoodsDetailActivity.this, SuerOrderActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", goodsListid);
                    intent.putExtra("bundle", bundle);
                    intent.putExtra("total", total);
                    intent.putExtra("select_color", select_color_type);
                    intent.putExtra("select_size", select_size_type);
                    startActivity(intent);
                }
                if (bottomInterDialog != null && bottomInterDialog.isShowing()) {
                    bottomInterDialog.dismiss();
                }
            }
        });
        bottom_cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomInterDialog != null && bottomInterDialog.isShowing()) {
                    bottomInterDialog.dismiss();
                }
            }
        });
    }

    private void setSizeType(int temp, Button[] size_types, String type[]) {
        for (int i = 0; i < size_types.length; i++) {
            if (i == temp) {
                size_types[i].setBackgroundResource(R.drawable.size_pressed);
                select_size_type = type[i];
                size_types[i].setTextColor(Color.WHITE);
            } else {
                size_types[i].setBackgroundResource(R.drawable.size);
                size_types[i].setTextColor(Color.rgb(51, 51, 51));
            }
        }
    }

    private void setColorType(int temp, Button color_types[], String types[]) {
        for (int i = 0; i < color_types.length; i++) {
            if (i == temp) {
                color_types[i].setBackgroundResource(R.drawable.first);
                select_color_type = types[i];
                color_types[i].setTextColor(Color.WHITE);
            } else {
                color_types[i].setBackgroundResource(R.drawable.second);
                color_types[i].setTextColor(Color.rgb(51, 51, 51));
            }
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
