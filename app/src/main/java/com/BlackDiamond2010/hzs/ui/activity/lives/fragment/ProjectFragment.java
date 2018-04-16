package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class ProjectFragment extends BaseFragment {


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
    @BindView(R.id.re_content)
    RelativeLayout reContent;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;

    @Override
    protected void loadData() {
        setState(AppConstants.STATE_SUCCESS);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topnews;
    }

    @Override
    protected void initView() {
        jintian.setText("推荐项目");
        luyan.setText("行业分类");
        project.setText("需求分类");
        List<Fragment> list  = new ArrayList<>();

        list.add(new ProjectOneFragment());
        list.add(new ProjectTwoFragment());
        list.add(new ProjectThreeFragment());

        viewpager.setAdapter(new MainAdapter(getChildFragmentManager(),list));

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){

                    case 0:
                        line1.setVisibility(View.VISIBLE);
                        line2.setVisibility(View.GONE);
                        line3.setVisibility(View.GONE);

                        break;
                    case 1:
                        line1.setVisibility(View.GONE);
                        line2.setVisibility(View.VISIBLE);
                        line3.setVisibility(View.GONE);

                        break;
                    case 2:
                        line1.setVisibility(View.GONE);
                        line2.setVisibility(View.GONE);
                        line3.setVisibility(View.VISIBLE);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initInject() {

    }



    @OnClick({R.id.jintian, R.id.luyan, R.id.project})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jintian:
                viewpager.setCurrentItem(0);
                break;
            case R.id.luyan:
                viewpager.setCurrentItem(1);
                break;
            case R.id.project:
                viewpager.setCurrentItem(2);
        }
    }

}
