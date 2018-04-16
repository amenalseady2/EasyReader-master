package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.PingJiaAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.EvaluateModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsEva;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.ImageScaleUtil.ImagePagerActivity;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PingjiaFragment extends BaseFragment {

    @BindView(R.id.rcv_activity)
    RecyclerView rcvActivity;
    private String id;

    public PingjiaFragment(
            String id
    ) {
        this.id = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void loadData() {
        setState(AppConstants.STATE_SUCCESS);

        rcvActivity.setLayoutManager(new LinearLayoutManager(getContext()));


//        List<String> urlList = new ArrayList<>();
//        urlList.add("http://api.kanjian2020.com/template?type=3&id=1");
//        rcvActivity.setAdapter(new WebViewAdapter(getContext(),urlList));
        getUserInfo();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pingjia;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initInject() {

    }

    public void getUserInfo() {

        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().goodspingjia(
                AndroidUtils.getAndroidId(getContext()),id)
                ,
                new HttpResultCall<HttpResult<GoodsEva>, GoodsEva>() {

                    @Override
                    public void onResponse(final GoodsEva result, String msg) {

                        PingJiaAdapter mAdapter = new PingJiaAdapter(getContext(), result.list);
                        rcvActivity.setAdapter(mAdapter);

                        final List<EvaluateModel> list = result.list;


                        mAdapter.mListener = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String[] tags = v.getTag().toString().split("-");
                                ArrayList<String> imgList = new ArrayList<String>();
                                if (list.get(Integer.valueOf(tags[0])).image.contains(",")) {
                                    String[] paths = list.get(Integer.valueOf(tags[0])).image.split(",");
                                    for (int i = 0; i < paths.length; i++) {
                                        imgList.add(paths[i]);
                                    }
                                }else {
                                    imgList.add(list.get(Integer.valueOf(tags[0])).image);
                                }
                                //路径
                                Intent intentToImgPage = new Intent(getActivity(),
                                        ImagePagerActivity.class);
                                // 图片url
                                intentToImgPage.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS,
                                        imgList);
                                intentToImgPage.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX,
                                        Integer.valueOf(tags[1]));
                                getActivity().startActivity(intentToImgPage);

                            }
                        };
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
