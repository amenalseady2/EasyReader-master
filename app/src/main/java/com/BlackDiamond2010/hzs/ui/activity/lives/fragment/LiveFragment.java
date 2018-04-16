package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.MainAdapter;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;
import com.BlackDiamond2010.hzs.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ASUS on 2017/10/10.
 */

public class LiveFragment extends BaseFragment {

    @BindView(R.id.jintian)
    TextView jintian;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.luyan)
    TextView luyan;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.project)
    TextView project;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.rl_top)
    LinearLayout rlTop;
    @BindView(R.id.live_viewpager)
    CustomViewPager liveViewpager;

    @Override
    protected void loadData() {
        setState(AppConstants.STATE_SUCCESS);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live;
    }

    @Override
    protected void initView() {

        LiveTrailerFragment trailerFragment = new LiveTrailerFragment();
        LiveingFragment liveingFragment = new LiveingFragment();
        LiveReviewFragment reviewFragment = new LiveReviewFragment();

        List<Fragment> list = new ArrayList<>();

        list.add(liveingFragment);
        list.add(trailerFragment);
        list.add(reviewFragment);
        liveViewpager.setAdapter(new MainAdapter(getChildFragmentManager(),list));
    }

    @Override
    protected void initInject() {

    }

    @OnClick({R.id.jintian, R.id.luyan, R.id.project})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jintian:
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.GONE);
                liveViewpager.setCurrentItem(0);
                break;
            case R.id.luyan:
                liveViewpager.setCurrentItem(1);
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.VISIBLE);
                line3.setVisibility(View.GONE);
                break;
            case R.id.project:
                liveViewpager.setCurrentItem(2);
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.VISIBLE);
                break;
        }

    }

}
