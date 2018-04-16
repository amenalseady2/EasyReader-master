package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.ToolsAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.ToolListModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ToolsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.view.XRecycleView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 路演工具列表
 */
public class ToolsListActivity extends BaseActivity {

    @BindView(R.id.zonghe_paixu)
    TextView zonghePaixu;
    @BindView(R.id.fenlie)
    TextView fenlie;
    @BindView(R.id.recycle)
    XRecycleView recycle;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    private int page = 1;
    private ToolsAdapter mAdapter;
    private String id ;
    private int type = 0 ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tools_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("路演工具");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置分割线
        recycle.setLayoutManager(layoutManager);


        final int type1 = getIntent().getIntExtra("type", 0);
//         id =  getIntent().getStringExtra("id");
        switch (type1) {
            case 1:
                setTitle("新品推荐");
                llTop.setVisibility(View.GONE);
                type = 2;

                getData(0,page, 0+"");
                break;
            case 2:
                setTitle("路演工具");
                id = 1+"";
                getData(1,page,"1");
                break;

            case 3:
                setTitle("文创商品");
                id = 2+"";
                getData(1,page, "2");
                break;

            case 4:
                setTitle("全部商品");
                llTop.setVisibility(View.GONE);
                getData(1,page, 0+"");
                break;


        }



//        toolsAdapter.itemListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CommonUtils.startAct(getApplicationContext(), GoodsDetailActivity.class);
//            }
//        };

        recycle.setRefreshAndLoadMoreListener(new XRecycleView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData(type,page, id);

            }

            @Override
            public void onLoadMore() {
                ++page;
                getData(type,page, id);
            }
        });


    }

    private void setListener(){

        mAdapter.itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startActWithData(ToolsListActivity.this, GoodsDetailActivity.class,"id",v.getTag().toString());
            }};}

    public void getData(int type, final int page, String category_id) {

        showLoadingDialog();

        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        toolsList(SHPUtils.getParame(getApplicationContext(), SHPUtils.TOKEN),
                                AndroidUtils.getAndroidId(getApplicationContext()), page, category_id, type),
                new HttpResultCall<HttpResult<ToolListModel>, ToolListModel>() {


                    @Override
                    public void onResponse(ToolListModel ShopBeen, String msg) {
                        List<ToolsModel> shopBeen = ShopBeen.list;
                        if (shopBeen == null || shopBeen.size() == 0) {
                            if (page ==1){
                                mackToastLONG("没有商品",ToolsListActivity.this);
                            }

                        } else {
                            if (mAdapter == null){
                                mAdapter = new ToolsAdapter(ToolsListActivity.this,shopBeen);
                                setListener();
                                recycle.setAdapter(mAdapter);
                            }else{
                                if (page == 1) {
                                    mAdapter.setData(shopBeen);
                                } else {
                                    mAdapter.addData(shopBeen);
                                }
                            }




                        }

                        if (ShopBeen.next == 0 ){
                            recycle.setloadMoreDone();
                        }

                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        dismissDialog();
                    }
                });


    }


    @OnClick({R.id.zonghe_paixu, R.id.fenlie})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zonghe_paixu:
                page = 1;

                type = 1 ;
                getData(type,page, id);
                break;
            case R.id.fenlie:
                page = 1;
                type  = 0;
                getData(type,page, id);
                break;
        }
    }
}
