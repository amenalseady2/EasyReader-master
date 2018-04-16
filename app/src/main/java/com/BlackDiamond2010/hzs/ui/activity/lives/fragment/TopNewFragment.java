package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.NewsDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.TopNewsAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.NewsBean;
import com.BlackDiamond2010.hzs.ui.activity.lives.constants.StaticConstant;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;
import com.BlackDiamond2010.hzs.view.XRecycleView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ASUS on 2017/10/10.
 */

public class TopNewFragment extends BaseFragment {


    @BindView(R.id.rl_top)
    LinearLayout rlTop;
    @BindView(R.id.recyclerView_topnews)
    XRecycleView recyclerViewTopnews;
    @BindView(R.id.re_content)
    RelativeLayout reContent;
    @BindView(R.id.jintian)
    TextView jintian;
    @BindView(R.id.luyan)
    TextView luyan;
    @BindView(R.id.project)
    TextView project;

    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.line3)
    View line3;

    private int page = 1;
    private int type = 0;

    TopNewsAdapter mAdapter ;

    @Override
    protected void loadData() {

        setState(AppConstants.STATE_SUCCESS);

        getData(type,page);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topnews;
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置分割线
        recyclerViewTopnews.setLayoutManager(layoutManager);
//        recyclerViewTopnews.addHeadView(headView());


        recyclerViewTopnews.setRefreshAndLoadMoreListener(new XRecycleView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData(type, page);
            }

            @Override
            public void onLoadMore() {
                ++page;
                getData(type, page);
            }
        });


    }

    private View headView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.head_topnews, null);
        ImageView img1 = (ImageView) view.findViewById(R.id.iv_img1);
        ImageView img2 = (ImageView) view.findViewById(R.id.iv_img2);
        ImageView img3 = (ImageView) view.findViewById(R.id.iv_img3);
        int w = (StaticConstant.sWidth - 84) / 3;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.rightMargin = 12;
        img1.setLayoutParams(lp);
        img2.setLayoutParams(lp);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT);
        img3.setLayoutParams(lp1);

        return view;
    }

    @Override
    protected void initInject() {

    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//       if (isVisibleToUser){
//           getData(type,type);
//       }
//    }

    public void getData(int type, final int page) {

//        showLoadingDialog();
       String token =  SHPUtils.getParame(getContext(), SHPUtils.TOKEN);
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        topNewsList(token, AndroidUtils.getAndroidId(getContext()), page, type),
                new HttpResultCall<HttpResult<NewsBean>, NewsBean>() {


                    @Override
                    public void onResponse(NewsBean shopBeen, String msg) {
                        if (shopBeen == null || shopBeen.list.size() == 0) {

                        } else {
                            if (page == 1){
                                if(mAdapter == null){
                                    mAdapter =  new TopNewsAdapter(getContext(), shopBeen.list);
                                    recyclerViewTopnews.setAdapter(mAdapter);
                                    mAdapter.mListener = new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            CommonUtils.startActWithData(getContext(), NewsDetailActivity.class,"id",v.getTag().toString());
                                        }
                                    };
                                }else {
                                    mAdapter.setData(shopBeen.list);
                                }

                            }else{
                                mAdapter.addData(shopBeen.list);
                            }

                        }

                        if (shopBeen.next == 0) {
                            recyclerViewTopnews.setloadMoreDone();
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        recyclerViewTopnews.setReFreshComplete();
                        super.onCompleted();
                    }
                });


    }



    @OnClick({R.id.jintian, R.id.luyan, R.id.project})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jintian:
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.GONE);
                page = 1;
                type = 0;
                getData(type, page);
                break;
            case R.id.luyan:
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.VISIBLE);
                line3.setVisibility(View.GONE);
                page = 1;
                type = 1;
                getData(type, page);
                break;
            case R.id.project:
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.VISIBLE);
                page = 1;
                type = 2;
                getData(type, page);
                break;
        }
    }
}
