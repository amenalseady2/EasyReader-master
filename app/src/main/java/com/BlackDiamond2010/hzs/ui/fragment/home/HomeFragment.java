package com.BlackDiamond2010.hzs.ui.fragment.home;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.adapter.HomeFragmentPageAdapter;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.PingjiaFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.XuZhiFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.goodsDetailFragment;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by quantan.liu on 2017/3/22.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.tab_gank)
    TabLayout tabGank;
    @BindView(R.id.vp_gank)
    ViewPager vpGank;

    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
    private HomeFragmentPageAdapter myAdapter;

    @Override
    protected void loadData() {
        setState(AppConstants.STATE_SUCCESS);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initView() {
        initFragmentList();
        myAdapter = new HomeFragmentPageAdapter(getChildFragmentManager(), mFragments, mTitleList);
        vpGank.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        tabGank.setTabMode(TabLayout.MODE_FIXED);
        tabGank.setupWithViewPager(vpGank);
    }


    public void setCurrentP(int position){
        vpGank.setCurrentItem(position);
    }

    @Override
    protected void initInject() {
    }

    private void initFragmentList() {
        if (mTitleList.size() != 0) {
            return;
        }
        mTitleList.add("测试");
        mTitleList.add("知乎日报");
        mTitleList.add("头条新闻");
//        mTitleList.add("排行榜");
//        mTitleList.add("最新电影");


        mFragments.add(new goodsDetailFragment(""));
        mFragments.add(new XuZhiFragment(null));
//        mFragments.add(new PingjiaFragment());


//        mFragments.add(new ZhiHuHomeFragment());
//        mFragments.add(new TopNewsFragment());
//        mFragments.add(new DouBanMovieTopFragment());
//        mFragments.add(new DouBanMovieLatestFragment());
    }



}
