package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.MainAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.HomeLiveFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.LiveFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.ProjectFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.ShopFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.TopNewFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoordinatorLayoutTestActivity extends FragmentActivity {

    @BindView(R.id.header)
    ImageView header;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapseToolbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_test);
        ButterKnife.bind(this);
        List<Fragment> list = new ArrayList<Fragment>();
        HomeLiveFragment homeFragment = new HomeLiveFragment();
        LiveFragment liveFragment = new LiveFragment();
        ProjectFragment projectFragment = new ProjectFragment();
        TopNewFragment topNewFragment = new TopNewFragment();

        ShopFragment shopFragment = new ShopFragment();
        // moreFragment = new MoreFragment();
        list.add(homeFragment);
        list.add(liveFragment);
        list.add(projectFragment);
        list.add(topNewFragment);
        list.add(shopFragment);
        // list.add(moreFragment);

            MainAdapter  mainAdapter = new MainAdapter(getSupportFragmentManager(), list);
        viewpager.setAdapter(mainAdapter);
        viewpager.setCurrentItem(0);
    }
}
