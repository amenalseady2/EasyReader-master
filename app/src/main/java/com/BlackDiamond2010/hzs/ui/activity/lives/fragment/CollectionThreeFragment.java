package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.NewsDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.TopNewsAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.NewsBean;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;
import com.BlackDiamond2010.hzs.view.XRecycleView;

import butterknife.BindView;

/**
 * Created by ASUS on 2017/10/10.   预告直播
 */

public class CollectionThreeFragment extends BaseFragment {

    @BindView(R.id.recycle)
    XRecycleView recycle;
    private int page = 1;
    @BindView(R.id.nocolltion)
    TextView nocolltion;

    private TopNewsAdapter mAdapter;

    @Override
    protected void loadData() {
        setState(AppConstants.STATE_SUCCESS);
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));



        recycle.setRefreshAndLoadMoreListener(new XRecycleView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData();


            }

            @Override
            public void onLoadMore() {
                ++page;
                getData();

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void setListener(){
        mAdapter.mListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startActWithData(getContext(), NewsDetailActivity.class,"id",v.getTag().toString());
            }};}

    public void getData() {
//        showLoadingDialog();
        String token = SHPUtils.getParame(getContext(), SHPUtils.TOKEN);
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        myCollect3( AndroidUtils.getAndroidId(getContext()), page, 3),
                new HttpResultCall<HttpResult<NewsBean>, NewsBean>() {


                    @Override
                    public void onResponse(NewsBean shopBeen, String msg) {
                        if (shopBeen == null || shopBeen.list.size() == 0) {
                            if (page ==1){
//                                mackToastLONG("暂时没有收藏直播",getContext());
                                nocolltion.setVisibility(View.VISIBLE);
                                nocolltion.setText("您还未收藏资讯头条");

                                if (mAdapter!=null){
                                    mAdapter.removeAll();
                                    recycle.setVisibility(View.GONE);
                                }
                            }


                        } else {
                            if (mAdapter == null){
                                mAdapter = new TopNewsAdapter(getContext(),shopBeen.list);
                                setListener();
                                recycle.setAdapter(mAdapter);
                            }else{
                                if (page == 1) {
                                    mAdapter.setData(shopBeen.list);
                                } else {
                                    mAdapter.addData(shopBeen.list);
                                }
                            }




                        }

                        if (shopBeen.next == 0) {
                            recycle.setloadMoreDone();
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        recycle.setReFreshComplete();
                        super.onCompleted();
                    }
                });


    }


    @Override
    protected int getLayoutId() {
        return R.layout.live_re;
    }

    @Override
    protected void initView() {



    }

    @Override
    protected void initInject() {

    }
}
