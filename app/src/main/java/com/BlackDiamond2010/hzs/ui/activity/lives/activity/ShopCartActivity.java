package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.ShopCartAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ShopCartModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.MyDialog;
import com.BlackDiamond2010.hzs.view.XRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//购物车
public class ShopCartActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.shopcart_price)
    TextView shopcartPrice;
    @BindView(R.id.shopcart_topay)
    TextView shopcartTopay;
    @BindView(R.id.shopcart_price_relat)
    RelativeLayout shopcartPriceRelat;
    @BindView(R.id.shopcart_delect)
    TextView shopcartDelect;
    @BindView(R.id.info_rela)
    RelativeLayout infoRela;
    @BindView(R.id.to_pay_relative)
    RelativeLayout toPayRelative;
    @BindView(R.id.goods_recycle)
    XRecycleView goodsRecycle;
    ShopCartAdapter mAdapter;
    @BindView(R.id.quanxuan_img)
    ImageView quanxuanImg;
    @BindView(R.id.quanxuan_tv)
    TextView quanxuanTv;
    private int page = 1;


    private boolean isall; //是否全选；

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_cart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("购物车");
        tvRight.setText("删除");
        tvRight.setVisibility(View.VISIBLE);
        goodsRecycle.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        goodsRecycle.setRefreshAndLoadMoreListener(new XRecycleView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                myShopCart();
            }

            @Override
            public void onLoadMore() {
//                recyclerViewTopnews.setloadMoreDone();
                ++page;
                myShopCart();
            }
        });

        myShopCart();
    }

    private void deleteShopCart(String id) {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        addordeleteShopcart(2, null, id, AndroidUtils.getAndroidId(this)),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object shopBeen, String msg) {
                        page = 1;
                        quanxuanImg.setImageResource(R.mipmap.choice_normal);
                        myShopCart();

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

    private void goodsNumChang(GoodsModel goodsModel) {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        numChange(goodsModel.num, goodsModel.id, AndroidUtils.getAndroidId(this)),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object shopBeen, String msg) {

                    }

                    @Override
                    public void onErr(String err, int status) {
//                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }
                });

    }


    private void myShopCart() {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        Shopcart(page, AndroidUtils.getAndroidId(this)),
                new HttpResultCall<HttpResult<ShopCartModel>, ShopCartModel>() {


                    @Override
                    public void onResponse(ShopCartModel shopBeen, String msg) {
                        if (shopBeen == null || shopBeen.list.size() == 0) {
                            if (page == 1) {
                                mackToastLONG("购物车没有添加商品", getApplicationContext());
                                if (mAdapter != null) {
                                    mAdapter.setData(shopBeen.list);
                                }
                            }


                        } else {
                            if (mAdapter == null) {
                                mAdapter = new ShopCartAdapter(ShopCartActivity.this, shopBeen.list, new ShopCartAdapter.PriceAddGoodsNumCall() {
                                    @Override
                                    public void priceCallback(String price) {
                                        shopcartPrice.setText(price);
                                    }

                                    @Override
                                    public void goodsNumCallback(String price) {

                                    }

                                    @Override
                                    public void setcheck(boolean flag) {

                                        isall = flag;
                                        if (isall) {
                                            quanxuanImg.setImageResource(R.mipmap.choice_normal_checked);
                                        } else {
                                            quanxuanImg.setImageResource(R.mipmap.choice_normal);
                                        }
                                    }

                                    @Override
                                    public void delectGoods(final List<String> goodscartIdList) {


                                        MyDialog.getDialogAndshow(ShopCartActivity.this, new MyDialog.DialogCallBack() {
                                            @Override
                                            public void dialogSure() {
                                                StringBuffer bf = new StringBuffer();

                                                for (int i = 0; i < goodscartIdList.size(); i++) {
                                                    if (i == goodscartIdList.size() - 1) {
                                                        bf.append(goodscartIdList.get(i));
                                                    } else {
                                                        bf.append(goodscartIdList.get(i) + ",");
                                                    }
                                                }
                                                deleteShopCart(bf.toString());
                                            }

                                            @Override
                                            public void dialogCancle() {

                                            }
                                        }, "确定将商品移除购物车？", "确定", "取消", "");

                                    }

                                    @Override
                                    public void wantToBuyGoodsList(ArrayList<GoodsModel> goodsListid) {
                                        Intent intent = new Intent(ShopCartActivity.this, SuerOrderActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("list", goodsListid);
                                        intent.putExtra("bundle", bundle);
                                        startActivity(intent);
//                                        CommonUtils.startAct(getApplicationContext(), SuerOrderActivity.class);
                                    }

                                    @Override
                                    public void goodsNumChange(GoodsModel goodsModel) {

                                        //TODO 调用修改 数量接口

                                        goodsNumChang(goodsModel);

                                    }
                                });
                                goodsRecycle.setAdapter(mAdapter);
                                mAdapter.itemListener = new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        CommonUtils.startActWithData(ShopCartActivity.this, GoodsDetailActivity.class, "id", v.getTag().toString());
                                    }
                                };

                                setListener();
                            } else {
                                if (page == 1) {
                                    mAdapter.setData(shopBeen.list);
                                } else {
                                    mAdapter.addData(shopBeen.list);
                                }
                            }
                        }
                        if (shopBeen.next == 0 || (shopBeen.list.size() > 0 && shopBeen.list.get(0).next == 0)) {
//                            goodsRecycle.setloadMoreDone();
                            goodsRecycle.setLoadMoreEnabled(false);
                        }

                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                        goodsRecycle.setReFreshComplete();
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        goodsRecycle.setReFreshComplete();
                    }
                });

    }


    private void setListener() {
//        mAdapter.add_jian_Listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v.getId() == R.id.iv_add){
//                    // 加
//                }else{
//                    //jian
//                }
//            }
//        };
    }

    @OnClick({R.id.quanxuan_img, R.id.quanxuan_tv, R.id.shopcart_topay, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                mAdapter.isCheckGoods(2);
                break;
            case R.id.quanxuan_img:
            case R.id.quanxuan_tv:
                isall = !isall;
                mAdapter.all(isall);

                if (isall) {
                    quanxuanImg.setImageResource(R.mipmap.choice_normal_checked);
                } else {
                    quanxuanImg.setImageResource(R.mipmap.choice_normal);
                }
                break;
            case R.id.shopcart_topay:

                mAdapter.isCheckGoods(1);

                break;
        }
    }
}
