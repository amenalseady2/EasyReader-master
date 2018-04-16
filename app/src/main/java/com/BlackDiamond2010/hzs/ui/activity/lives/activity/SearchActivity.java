package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.LiveAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.OrderAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.ProjectAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.ToolsAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.TopNewsAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.ToolListModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LiveModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderItem;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.NewsBean;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ProModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ProjectModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ToolsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.MyDialog;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;
import com.BlackDiamond2010.hzs.view.WordWrapView;
import com.BlackDiamond2010.hzs.view.XRecycleView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.cancle)
    TextView cancle;
    @BindView(R.id.list)
    XRecycleView list;
    @BindView(R.id.tv_lishi)
    TextView tvLishi;
    @BindView(R.id.delete_img)
    ImageView deleteImg;
    @BindView(R.id.wwv_record)
    WordWrapView wwvRecord;
    @BindView(R.id.rl_record)
    RelativeLayout rlRecord;
    private int type = 0; //1 直播 5订单 3 头条 4 路演工具 2项目
    private String keyword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 0);
        String record = "";
        switch (type) {
            case 1:
                search.setHint("请输入搜索关键字");

                record = SHPUtils.getParame(this, SHPUtils.HOME_SEARCH);
                initSearchRecord(record);


                break;
            case 2:
                search.setHint("请输入搜索关键字");

                record = SHPUtils.getParame(this, SHPUtils.PROJECT_SEARCH);
                initSearchRecord(record);

                break;
            case 3:
                record = SHPUtils.getParame(this, SHPUtils.TOP_NEWS_SEARCH);
                initSearchRecord(record);

                break;

            case 4:
                record = SHPUtils.getParame(this, SHPUtils.GOODS_SEARCH);
                initSearchRecord(record);
                break;

            case 5:
                search.setHint("请输入商品信息或订单号关键字");

                break;

        }

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setRefreshAndLoadMoreListener(new XRecycleView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                totalGetData();
            }

            @Override
            public void onLoadMore() {
                ++page;
                totalGetData();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keyword = s.toString();
                if (StringUtil.isEmpty(keyword)) {
                    cancle.setText("取消");
                } else {
                    cancle.setText("搜索");
                }
            }
        });

    }

    /**
     * @param record 历史记录
     *               初始化 历史数据
     */
    private void initSearchRecord(String record) {
        if (StringUtil.isEmpty(record)) {
            rlRecord.setVisibility(View.GONE);
        } else {
            //初始化 数据

            deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyDialog.getDialogAndshow(SearchActivity.this, new MyDialog.DialogCallBack() {
                        @Override
                        public void dialogSure() {
                            rlRecord.setVisibility(View.GONE);
                            switch (type){
                                case 1:
                                    SHPUtils.saveParame(SearchActivity.this, SHPUtils.HOME_SEARCH, "");
                                    break;
                                case 2:
                                    SHPUtils.saveParame(SearchActivity.this, SHPUtils.PROJECT_SEARCH, "");
                                    break;
                                case 4:
                                    SHPUtils.saveParame(SearchActivity.this, SHPUtils.GOODS_SEARCH, "");
                                    break;
                            }
                        }

                        @Override
                        public void dialogCancle() {

                        }
                    }, "确定清空搜索历史记录？", "确定", "取消", "");
                }
            });




            rlRecord.setVisibility(View.VISIBLE);
            wwvRecord.removeAllViews();
            if (record.contains("&")) {
                String[] records = record.split("&");
                for (int i = 0; i < records.length; i++) {
                    View view = LayoutInflater.from(this).inflate(R.layout.item_seach,null,false);
                    TextView textview = (TextView) view.findViewById(R.id.text);
                    textview.setBackgroundResource(R.drawable.dingyue_search);
//                    textview.setPadding(15, 10, 15, 10);
                    textview.setText(records[i]);
                    textview.setTag(records[i]);
                    textview.setOnClickListener(recordListener);
                    wwvRecord.addView(view);
                }
            } else {
                View view = LayoutInflater.from(this).inflate(R.layout.item_seach,null,false);
                TextView textview = (TextView) view.findViewById(R.id.text);
                textview.setBackgroundResource(R.drawable.dingyue_search);
//                textview.setPadding(15, 10, 15, 10);
                textview.setGravity(Gravity.CENTER);
                textview.setText(record);
                textview.setTag(record);
                textview.setOnClickListener(recordListener);
                wwvRecord.addView(view);
            }
        }
    }

    View.OnClickListener recordListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            search.setText(v.getTag().toString());
        }
    };

    @OnClick(R.id.cancle)
    public void onViewClicked() {
        if (StringUtil.isEmpty(keyword)) {
            finish();
        } else {
            totalGetData();
        }
    }

    int page = 1;

    private void totalGetData() {
        String record = "";
        switch (type) {
            case 1:
                getLiveData(keyword, page);

                record = SHPUtils.getParame(this, SHPUtils.HOME_SEARCH);
                if (StringUtil.isEmpty(record)) {
                    SHPUtils.saveParame(this, SHPUtils.HOME_SEARCH, keyword);
                } else {
                    //如果已经保存过改条记录 。就不重复保存
                    if (!record.contains(keyword)) {
                        String newsMessage = keyword + "&" + record;
                        SHPUtils.saveParame(this, SHPUtils.HOME_SEARCH, newsMessage);
                    }

                }

                break;
            case 2:

                getProjectData(keyword, page);
                record = SHPUtils.getParame(this, SHPUtils.PROJECT_SEARCH);
                if (StringUtil.isEmpty(record)) {
                    SHPUtils.saveParame(this, SHPUtils.PROJECT_SEARCH, keyword);
                } else {
                    //如果已经保存过改条记录 。就不重复保存
                    if (!record.contains(keyword)) {
                        String newsMessage = keyword + "&" + record;
                        SHPUtils.saveParame(this, SHPUtils.PROJECT_SEARCH, newsMessage);
                    }

                }
                break;
            case 3:
                getNewsData(keyword, page);

                record = SHPUtils.getParame(this, SHPUtils.TOP_NEWS_SEARCH);
                if (StringUtil.isEmpty(record)) {
                    SHPUtils.saveParame(this, SHPUtils.TOP_NEWS_SEARCH, keyword);
                } else {
                    //如果已经保存过改条记录 。就不重复保存
                    if (!record.contains(keyword)) {
                        String newsMessage = keyword + "&" + record;
                        SHPUtils.saveParame(this, SHPUtils.TOP_NEWS_SEARCH, newsMessage);
                    }

                }
                break;

            case 4:
                getGoodsData(keyword, page);
                record = SHPUtils.getParame(this, SHPUtils.GOODS_SEARCH);
                if (StringUtil.isEmpty(record)) {
                    SHPUtils.saveParame(this, SHPUtils.GOODS_SEARCH, keyword);
                } else {
                    //如果已经保存过改条记录 。就不重复保存
                    if (!record.contains(keyword)) {
                        String newsMessage = keyword + "&" + record;
                        SHPUtils.saveParame(this, SHPUtils.GOODS_SEARCH, newsMessage);
                    }

                }
                break;

            case 5:
                getOrderData(keyword, page);
                break;

        }
    }

    //头条 --------------
    TopNewsAdapter newsAdapter;

    public void getNewsData(String kyeword, final int page) {
        showLoadingDialog();
        String token = SHPUtils.getParame(getApplicationContext(), SHPUtils.TOKEN);
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        newsSearch(AndroidUtils.getAndroidId(getApplicationContext()), kyeword, page),
                new HttpResultCall<HttpResult<NewsBean>, NewsBean>() {
                    @Override
                    public void onResponse(NewsBean shopBeen, String msg) {
                        if (shopBeen == null || shopBeen.list.size() == 0) {
                            if (page == 1) {
                                mackToastLONG("没搜索到相关内容", SearchActivity.this);
                            }
                        } else {
                            if (page == 1) {
                                if (newsAdapter == null) {
                                    newsAdapter = new TopNewsAdapter(getApplicationContext(), shopBeen.list);
                                    list.setAdapter(newsAdapter);
                                    newsAdapter.mListener = new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
//                                            CommonUtils.startAct(SearchActivity.this, NewsDetailActivity.class);
                                            CommonUtils.startActWithData(SearchActivity.this, NewsDetailActivity.class,"id",v.getTag().toString());
                                        }
                                    };
                                } else {
                                    newsAdapter.setData(shopBeen.list);
                                }
                            } else {
                                newsAdapter.addData(shopBeen.list);
                            }
                        }
                        if (shopBeen.next == 0) {
                            list.setloadMoreDone();
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
    //头条结束 --------------

    //直播--------------
    LiveAdapter liveAdapter;

    public void getLiveData(String kyeword, final int page) {
        showLoadingDialog();
        String token = SHPUtils.getParame(getApplicationContext(), SHPUtils.TOKEN);
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        homeSearch(AndroidUtils.getAndroidId(getApplicationContext()), kyeword, page),
                new HttpResultCall<HttpResult<LiveModel>, LiveModel>() {
                    @Override
                    public void onResponse(LiveModel shopBeen, String msg) {
                        if (shopBeen == null || shopBeen.list.size() == 0) {
                            if (page == 1) {
                                mackToastLONG("没搜索到相关内容", SearchActivity.this);
                            }

                        } else {
                            if (liveAdapter == null) {
                                liveAdapter = new LiveAdapter(SearchActivity.this, shopBeen.list);
                                liveAdapter.mListener = new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String[] str = v.getTag().toString().split("_");
                                        if ("0".equals(str[1])) {
                                            CommonUtils.startActWithData(SearchActivity.this, LiveTrailerDetailActivity.class, "id", str[0]);
                                        } else if ("1".equals(str[1])) {
                                            CommonUtils.startActWithData(SearchActivity.this, LiveDetailActivity.class, "id", str[0]);
                                        } else if ("2".equals(str[1])) {
                                            CommonUtils.startActWithData(SearchActivity.this, LiveReviewDetailActivity.class, "id", str[0]);
                                        }

                                    }
                                };
                                list.setAdapter(liveAdapter);
                            } else {
                                if (page == 1) {
                                    liveAdapter.setData(shopBeen.list);
                                } else {
                                    liveAdapter.addData(shopBeen.list);
                                }
                            }
                        }
                        if (shopBeen.next == 0) {
                            list.setloadMoreDone();
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        list.setReFreshComplete();
                        dismissDialog();
                        super.onCompleted();
                    }
                });
    }
    //直播结束 --------------

    //项目-------------
    private ProjectAdapter projectAdapter;

    public void getProjectData(String kyeword, final int page) {
        showLoadingDialog();
        String token = SHPUtils.getParame(getApplicationContext(), SHPUtils.TOKEN);
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        projectSearch(AndroidUtils.getAndroidId(getApplicationContext()), kyeword, page),
                new HttpResultCall<HttpResult<ProjectModel>, ProjectModel>() {
                    @Override
                    public void onResponse(ProjectModel shopBeen, String msg) {
                        if (shopBeen == null || shopBeen.list.size() == 0) {
                            if (page == 1) {
                                mackToastLONG("没搜索到相关内容", SearchActivity.this);
                            }
                        } else {
                            if (page == 1) {
                                if (projectAdapter == null) {
                                    projectAdapter = new ProjectAdapter(SearchActivity.this, shopBeen.list);
                                    projectAdapter.itemListener = new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(SearchActivity.this, ProjectDetailActivity.class);
                                            ProModel mode = projectAdapter.getitem(Integer.valueOf(v.getTag().toString()));
                                            intent.putExtra("name", mode.name);
                                            intent.putExtra("id", mode.id + "");
                                            startActivity(intent);

                                        }
                                    };
                                    list.setAdapter(projectAdapter);


                                } else {
                                    projectAdapter.setData(shopBeen.list);
                                }

                            } else {
                                projectAdapter.addData(shopBeen.list);
                            }

                        }

                        if (shopBeen.next == 0) {
                            list.setloadMoreDone();
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        list.setReFreshComplete();
                        super.onCompleted();
                        dismissDialog();
                    }
                });
    }
    //项目结束 --------------

    //商品------------
    private ToolsAdapter goodsAdapter;

    public void getGoodsData(String kyeword, final int page) {
        showLoadingDialog();
        String token = SHPUtils.getParame(getApplicationContext(), SHPUtils.TOKEN);
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        goodsSearch(AndroidUtils.getAndroidId(getApplicationContext()), kyeword, page),
                new HttpResultCall<HttpResult<ToolListModel>, ToolListModel>() {
                    @Override
                    public void onResponse(ToolListModel shopBeen, String msg) {
                        List<ToolsModel> ShopBeen = shopBeen.list;
                        if (ShopBeen == null || ShopBeen.size() == 0) {
                            if (page == 1) {
                                mackToastLONG("没搜索到相关内容", SearchActivity.this);
                            }

                        } else {
                            if (goodsAdapter == null) {
                                goodsAdapter = new ToolsAdapter(SearchActivity.this, ShopBeen);
                                goodsAdapter.itemListener = new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        CommonUtils.startActWithData(SearchActivity.this, GoodsDetailActivity.class, "id", v.getTag().toString());
                                    }
                                };
                                list.setAdapter(goodsAdapter);
                            } else {
                                if (page == 1) {
                                    goodsAdapter.setData(ShopBeen);
                                } else {
                                    goodsAdapter.addData(ShopBeen);
                                }
                            }
                        }
                        if (shopBeen.next == 0) {
                            list.setloadMoreDone();
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        list.setReFreshComplete();
                        super.onCompleted();
                        dismissDialog();
                    }
                });
    }
    //商品结束 --------------

    //订单------------
    private OrderAdapter orderAdapter;

    public void getOrderData(String kyeword, final int page) {
        showLoadingDialog();
        String token = SHPUtils.getParame(getApplicationContext(), SHPUtils.TOKEN);
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        orderSearch(AndroidUtils.getAndroidId(getApplicationContext()), kyeword, page),
                new HttpResultCall<HttpResult<MyOrderModel>, MyOrderModel>() {
                    @Override
                    public void onResponse(MyOrderModel shopBeen, String msg) {
                        if (shopBeen == null || shopBeen.list.size() == 0) {
                            if (page == 1) {
                                mackToastLONG("没搜索到相关内容", SearchActivity.this);
                            }

                        } else {
                            if (orderAdapter == null) {
                                orderAdapter = new OrderAdapter(SearchActivity.this, shopBeen.list);
                                setListener();
                                list.setAdapter(orderAdapter);
                            } else {
                                if (page == 1) {
                                    orderAdapter.setData(shopBeen.list);
                                } else {
                                    orderAdapter.addData(shopBeen.list);
                                }
                            }


                        }

//                        if (shopBeen.next == 0) {
//                            list.setloadMoreDone();
//                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        list.setReFreshComplete();
                        super.onCompleted();
                        dismissDialog();
                    }
                });
    }
    //我的订单 listener --------------

    private void setListener() {

        orderAdapter.itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOrderItem model = (MyOrderItem) v.getTag();
                CommonUtils.startActWithData(SearchActivity.this, OrderDetailActivity.class, "bean", model);
            }
        };
        orderAdapter.btnListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] tag = v.getTag().toString().split(",");
                int status = Integer.valueOf(tag[2]);
                if (status == 0) {

                    if ("1".equals(tag[1])) {
                        // 支付
                        Intent intent = new Intent(SearchActivity.this, PaymentActivity.class);
                        intent.putExtra("type", 4);
                        intent.putExtra("order_sn", tag[3]);
                        intent.putExtra("price", tag[4]);
                        startActivityForResult(intent, 888);
                    } else {
                        //取消订单
                        cancleOrder(tag[0], "cancel");
                    }
                } else if (status == 1) {
                    //提醒收货
                } else if (status == 2) {
                    if ("1".equals(tag[1])) {
                        // 确认收货
                        cancleOrder(tag[0], "complete");
                    } else {
                        //查看物流
                        CommonUtils.startActWithData(SearchActivity.this, LogisticsActivity.class, "id", tag[0]);
                    }
                } else if (status == 3) {
                    if ("1".equals(tag[1])) {
                        MyOrderItem m = orderAdapter.getItems(Integer.valueOf(tag[5]));
                        //去评价
                        CommonUtils.startActWithData(SearchActivity.this, EvaluateActivity.class, "id", m);
                    } else if ("2".equals(tag[1])) {
                        //申请售后
                        MyOrderItem m = orderAdapter.getItems(Integer.valueOf(tag[3]));
                        CommonUtils.startActWithData(SearchActivity.this, ShenQingRefundActivity.class, "id", m);
                    } else if ("3".equals(tag[1])) {
                        //删除订单
                        cancleOrder(tag[0], "delete");
                    }
                }

            }
        };
    }

    private void cancleOrder(String orderId, String type) {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        cancleOrder(orderId, type, AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext())),
                new HttpResultCall<HttpResult<Object>, Object>() {

                    @Override
                    public void onResponse(Object result, String msg) {
                        mackToastLONG(msg, SearchActivity.this);
                        page = 1;

                        getOrderData(search.getText().toString(), page);
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

}
