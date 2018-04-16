package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.MainAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.CollectionFourFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.CollectionOneFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.CollectionThreeFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.CollectionTwoFragment;
import com.BlackDiamond2010.hzs.view.LazyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCollectionActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.line4)
    View line4;
    @BindView(R.id.rl_top)
    LinearLayout rlTop;
    @BindView(R.id.recyclerView_topnews)
    LazyViewPager viewPager;
    @BindView(R.id.re_content)
    RelativeLayout reContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mycollection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的收藏");

        List<Fragment> list = new ArrayList<>();
        list.add(new CollectionOneFragment());
        list.add(new CollectionTwoFragment());
        list.add(new CollectionThreeFragment());
        list.add(new CollectionFourFragment());

        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), list));


        viewPager.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {

                    case 0:
                        line1.setVisibility(View.VISIBLE);
                        line2.setVisibility(View.GONE);
                        line3.setVisibility(View.GONE);
                        line4.setVisibility(View.GONE);

                        break;
                    case 1:

                        line1.setVisibility(View.GONE);
                        line2.setVisibility(View.VISIBLE);
                        line3.setVisibility(View.GONE);
                        line4.setVisibility(View.GONE);
                        break;
                    case 2:
                        line1.setVisibility(View.GONE);
                        line2.setVisibility(View.GONE);
                        line3.setVisibility(View.VISIBLE);
                        line4.setVisibility(View.GONE);
                        break;

                    case 3:
                        line1.setVisibility(View.GONE);
                        line2.setVisibility(View.GONE);
                        line3.setVisibility(View.GONE);
                        line4.setVisibility(View.VISIBLE);
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick({R.id.tv_1, R.id.tv2, R.id.tv3, R.id.tv4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv2:
                viewPager.setCurrentItem(1);

                break;
            case R.id.tv3:
                viewPager.setCurrentItem(2);
                break;
            case R.id.tv4:
                viewPager.setCurrentItem(3);
                break;
        }
    }
}
