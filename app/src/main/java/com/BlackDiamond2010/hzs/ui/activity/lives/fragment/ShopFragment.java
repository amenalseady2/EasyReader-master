package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.GoodsDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.LoginActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.ToolsListActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.WebActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.HomeHeadAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.MyLikeAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.AlbumBean;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ShopModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.constants.StaticConstant;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.AutoScrollViewPager;
import com.BlackDiamond2010.hzs.view.XImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ASUS on 2017/10/10.
 */

public class ShopFragment extends BaseFragment {
    @BindView(R.id.goods_viewpager)
    AutoScrollViewPager goodsViewpager;
    @BindView(R.id.ll_zhishiqi)
    LinearLayout llZhishiqi;
    @BindView(R.id.home_viewpager_rela)
    RelativeLayout homeViewpagerRela;
    @BindView(R.id.tv_luyan)
    TextView tvLuyan;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.head_hothome_layout)
    LinearLayout headHothomeLayout;
    @BindView(R.id.tv_goods)
    TextView tvGoods;
    @BindView(R.id.wenchuanggoods_recyclerview)
    RecyclerView wenchuanggoodsRecyclerview;
    @BindView(R.id.ll_goods)
    LinearLayout llGoods;
    @BindView(R.id.recyclerview_like)
    RecyclerView recyclerviewLike;
    @BindView(R.id.ll_ilike)
    RelativeLayout llIlike;
    @BindView(R.id.tv_tool)
    TextView tvTool;
    @BindView(R.id.newgoods)
    TextView newgoods;
    @BindView(R.id.wenchuangshangp)
    TextView wenchuangshangp;
    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.luyao_bigimg)
    ImageView luyaoBigimg;
    @BindView(R.id.wenchuang_bigimg)
    ImageView wenchuangBigimg;
    @BindView(R.id.title1)
    TextView title1;
    @BindView(R.id.price1)
    TextView price1;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.price2)
    TextView price2;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.title3)
    TextView title3;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.title4)
    TextView title4;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.frist_rl)
    RelativeLayout fristRl;
    @BindView(R.id.second_rl)
    RelativeLayout secondRl;
    @BindView(R.id.three_rl)
    RelativeLayout threeRl;
    @BindView(R.id.four_rl)
    RelativeLayout fourRl;
    @BindView(R.id.tv_luyanmore)
    TextView tvLuyanmore;
    @BindView(R.id.tv_wenchuangmore)
    TextView tvWenchuangmore;
    @BindView(R.id.tv_xihuan)
    TextView tvXihuan;
    @BindView(R.id.xihuan_change)
    TextView xihuanChange;
    @BindView(R.id.rl_like)
    RelativeLayout rlLike;


    @Override
    protected void loadData() {
        setState(AppConstants.STATE_SUCCESS);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    protected void initView() {
        getData();
    }


    public void setData(ShopModel model) {
        homeViewpagerRela.getLayoutParams().height = (int) (StaticConstant.sWidth * 0.423);
        homeViewpagerRela.getLayoutParams().width = (StaticConstant.sWidth);
        homeViewpagerRela.requestLayout();
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < model.slider.size(); i++) {
            urlList.add(model.slider.get(i).cover);
        }
        advertisement(urlList);
        //-----------------广告业结束
        //----------------四个框开始

        for (int i = 0; i < model.recommend.size(); i++) {
            GoodsModel goods = model.recommend.get(i);
            switch (i) {
                case 0:
                    GlideUtils.loadImage(2, goods.cover, img1);
                    title1.setText(goods.name);
                    price1.setText("￥" + goods.price);


                    break;
                case 1:

                    GlideUtils.loadImage(2, goods.cover, img2);
                    title2.setText(goods.name);
                    price2.setText("￥" + goods.price);


                    break;
                case 2:
                    GlideUtils.loadImage(2, goods.cover, img3);
                    title3.setText(goods.name);

                    break;


                case 3:
                    GlideUtils.loadImage(2, goods.cover, img4);
                    title4.setText(goods.name);
                    break;
            }
        }
        //------------------结束

        if ("路演工具".equals(model.product_list.get(0).title)) {
            setGoodsDatas(recyclerview, model.product_list.get(0).list.subList(1, model.product_list.get(0).list.size()));
            GlideUtils.loadImage(1, model.product_list.get(0).list.get(0).cover, luyaoBigimg);
            setGoodsDatas(wenchuanggoodsRecyclerview, model.product_list.get(1).list.subList(1, model.product_list.get(1).list.size()));
            GlideUtils.loadImage(1, model.product_list.get(1).list.get(0).cover, wenchuangBigimg);
        } else {
            setGoodsDatas(wenchuanggoodsRecyclerview, model.product_list.get(0).list.subList(1, model.product_list.get(0).list.size()));
            GlideUtils.loadImage(1, model.product_list.get(0).list.get(0).cover, wenchuangBigimg);
            setGoodsDatas(recyclerview, model.product_list.get(1).list.subList(1, model.product_list.get(1).list.size()));
            GlideUtils.loadImage(1, model.product_list.get(1).list.get(0).cover, luyaoBigimg);
        }


        recyclerviewLike.setHasFixedSize(true);
        recyclerviewLike.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerviewLike.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        likeAdapter = new MyLikeAdapter(getContext(), model.like);
        likeAdapter.itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startActWithData(getActivity(), GoodsDetailActivity.class, "id", v.getTag().toString());
            }
        };
        recyclerviewLike.setAdapter(likeAdapter);

    }

    MyLikeAdapter likeAdapter;


    public void setGoodsDatas(RecyclerView recycler, List<GoodsModel> mList) {

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler.setLayoutManager(layoutManager2);
        HomeHeadAdapter mHomeHeadAdapter = new HomeHeadAdapter(getContext(), mList);
        mHomeHeadAdapter.itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startActWithData(getActivity(), GoodsDetailActivity.class, "id", v.getTag().toString());
            }
        };
        recycler.setAdapter(mHomeHeadAdapter);

    }

    private ShopModel shopModel;

    public void getData() {

        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        shopHome(AndroidUtils.getAndroidId(getContext())),
                new HttpResultCall<HttpResult<ShopModel>, ShopModel>() {


                    @Override
                    public void onResponse(ShopModel ShopModel, String msg) {
                        shopModel = ShopModel;

                        setData(ShopModel);

                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                        if (status == 405) {
                            CommonUtils.startAct(getContext(), LoginActivity.class);
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
    protected void initInject() {

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
            ImageView imgD = new ImageView(getActivity());
            dianList.add(imgD);
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
        setIndicator(0);
    }

    private void change() {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        change(AndroidUtils.getAndroidId(getContext())),
                new HttpResultCall<HttpResult<List<GoodsModel>>, List<GoodsModel>>() {


                    @Override
                    public void onResponse(List<GoodsModel> shopBeen, String msg) {
                        likeAdapter.setData(shopBeen);
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


    @OnClick({R.id.tv_luyanmore, R.id.tv_wenchuangmore, R.id.xihuan_change, R.id.wenchuang_bigimg, R.id.luyao_bigimg, R.id.newgoods, R.id.wenchuangshangp, R.id.tv_tool, R.id.all, R.id.frist_rl, R.id.second_rl, R.id.three_rl, R.id.four_rl})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.tv_luyanmore:
                intent.setClass(getContext(), ToolsListActivity.class);
                intent.putExtra("id", shopModel.category.get(0).id);
                intent.putExtra("type", 2);
                startActivity(intent);

                break;
            case R.id.tv_wenchuangmore:
                intent.setClass(getContext(), ToolsListActivity.class);
                intent.putExtra("type", 3);
                intent.putExtra("id", shopModel.category.get(1).id);
                startActivity(intent);

                break;
            case R.id.xihuan_change:
                change();

                break;
            case R.id.wenchuang_bigimg:

                CommonUtils.startActWithData(getActivity(), GoodsDetailActivity.class, "id",
                        shopModel.product_list.get(1).list.get(0).id);


                break;
            case R.id.luyao_bigimg:
                CommonUtils.startActWithData(getActivity(), GoodsDetailActivity.class, "id", shopModel.product_list.get(0).list.get(0).id);
                break;
            case R.id.frist_rl:
                CommonUtils.startActWithData(getActivity(), GoodsDetailActivity.class, "id", shopModel.recommend.get(0).id);
                break;
            case R.id.second_rl:
                CommonUtils.startActWithData(getActivity(), GoodsDetailActivity.class, "id", shopModel.recommend.get(1).id);

                break;
            case R.id.three_rl:
                CommonUtils.startActWithData(getActivity(), GoodsDetailActivity.class, "id", shopModel.recommend.get(2).id);


                break;
            case R.id.four_rl:
                CommonUtils.startActWithData(getActivity(), GoodsDetailActivity.class, "id", shopModel.recommend.get(3).id);

                break;


            case R.id.newgoods:
                intent.setClass(getContext(), ToolsListActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;

            case R.id.wenchuangshangp:
                intent.setClass(getContext(), ToolsListActivity.class);
                intent.putExtra("type", 3);
                intent.putExtra("id", shopModel.category.get(1).id);
                startActivity(intent);
                break;

            case R.id.tv_tool:
                intent.setClass(getContext(), ToolsListActivity.class);
                intent.putExtra("id", shopModel.category.get(0).id);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.all:
                intent.setClass(getContext(), ToolsListActivity.class);
                intent.putExtra("type", 4);
                startActivity(intent);
                break;

        }

    }


    private class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mAlbumList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            View view = View.inflate(getActivity(), R.layout.scoregoods_pic_item, null);
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ImageView photoView = (ImageView) view.findViewById(R.id.pull_img);
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            GlideUtils.loadImage(3, mAlbumList.get(position).url, photoView);
            container.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAlbumList != null && mAlbumList.size() > 0) {
//                        String url = shopModel.slider.get(position).url;
//                        CommonUtils.startActWithData(getContext(), WebActivity.class, "url", url);
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("url", shopModel.slider.get(position).url);
                        intent.putExtra("title", shopModel.slider.get(position).title);
                        startActivity(intent);
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
