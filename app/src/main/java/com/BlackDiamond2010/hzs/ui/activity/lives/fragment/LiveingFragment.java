package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.LiveDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.LiveReviewDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.LiveTrailerDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.LiveAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LiveModel;
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

public class LiveingFragment extends BaseFragment {

    @BindView(R.id.recycle)
    XRecycleView recycle;
    private int page = 1;

    private LiveAdapter mAdapter;

    @Override
    protected void loadData() {
        setState(AppConstants.STATE_SUCCESS);
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));


        getData();

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

    private void setListener() {

        mAdapter.mListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] str = v.getTag().toString().split("_");
                if ("0".equals(str[1])) {
                    CommonUtils.startActWithData(getContext(), LiveTrailerDetailActivity.class, "id", str[0]);
                } else if ("1".equals(str[1])) {
                    CommonUtils.startActWithData(getContext(), LiveDetailActivity.class, "id", str[0]);
                } else if ("2".equals(str[1])) {
                    CommonUtils.startActWithData(getContext(), LiveReviewDetailActivity.class, "id", str[0]);
                }
            }
        };
        mAdapter.setUnClick();
    }


    public void getData() {

//        showLoadingDialog();
        String token = SHPUtils.getParame(getContext(), SHPUtils.TOKEN);
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        liveList(AndroidUtils.getAndroidId(getContext()), page, 0),
                new HttpResultCall<HttpResult<LiveModel>, LiveModel>() {


                    @Override
                    public void onResponse(LiveModel shopBeen, String msg) {
                        if (shopBeen == null || shopBeen.list.size() == 0) {
                            if (page == 1) {
                                mackToastLONG("暂时没有直播", getContext());
                            }

                        } else {
                            if (mAdapter == null) {
                                mAdapter = new LiveAdapter(getContext(), shopBeen.list);
                                setListener();
                                recycle.setAdapter(mAdapter);
                            } else {
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
