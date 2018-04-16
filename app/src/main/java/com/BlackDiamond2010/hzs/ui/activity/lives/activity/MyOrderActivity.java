package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.MainAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.BePaymentOrderFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.BeReviceOrderFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.BeSendOrderFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.MyAllOrderFragment;
import com.BlackDiamond2010.hzs.ui.activity.lives.fragment.OverOrderFragment;
import com.BlackDiamond2010.hzs.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyOrderActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.bepay)
    TextView bepay;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.befahuo)
    TextView befahuo;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.be_receve)
    TextView beReceve;
    @BindView(R.id.line4)
    View line4;
    @BindView(R.id.over)
    TextView over;
    @BindView(R.id.line5)
    View line5;
    @BindView(R.id.rl_top)
    LinearLayout rlTop;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的订单");

        tvRight.setBackgroundResource(R.mipmap.sousuo_wodedingdan);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyOrderActivity.this, SearchActivity.class);
                intent.putExtra("type", 5);
                startActivity(intent);
            }
        });

        List<Fragment> list = new ArrayList<>();
        list.add(new MyAllOrderFragment());
        list.add(new BePaymentOrderFragment());
        list.add(new BeSendOrderFragment());
        list.add(new BeReviceOrderFragment());
        list.add(new OverOrderFragment());
        viewpager.setAdapter(new MainAdapter(getSupportFragmentManager(), list));

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        all.setTextColor(getResources().getColor(R.color.red));
                        bepay.setTextColor(getResources().getColor(R.color.text_333));
                        befahuo.setTextColor(getResources().getColor(R.color.text_333));
                        beReceve.setTextColor(getResources().getColor(R.color.text_333));
                        over.setTextColor(getResources().getColor(R.color.text_333));

                        line1.setVisibility(View.VISIBLE);
                        line2.setVisibility(View.GONE);
                        line3.setVisibility(View.GONE);
                        line4.setVisibility(View.GONE);
                        line5.setVisibility(View.GONE);
                        break;
                    case 1:
                        all.setTextColor(getResources().getColor(R.color.text_333));
                        bepay.setTextColor(getResources().getColor(R.color.red));
                        befahuo.setTextColor(getResources().getColor(R.color.text_333));
                        beReceve.setTextColor(getResources().getColor(R.color.text_333));
                        over.setTextColor(getResources().getColor(R.color.text_333));
                        line1.setVisibility(View.GONE);
                        line2.setVisibility(View.VISIBLE);
                        line3.setVisibility(View.GONE);
                        line4.setVisibility(View.GONE);
                        line5.setVisibility(View.GONE);
                        break;
                    case 2:
                        all.setTextColor(getResources().getColor(R.color.text_333));
                        bepay.setTextColor(getResources().getColor(R.color.text_333));
                        befahuo.setTextColor(getResources().getColor(R.color.red));
                        beReceve.setTextColor(getResources().getColor(R.color.text_333));
                        over.setTextColor(getResources().getColor(R.color.text_333));
                        line1.setVisibility(View.GONE);
                        line2.setVisibility(View.GONE);
                        line3.setVisibility(View.VISIBLE);
                        line4.setVisibility(View.GONE);
                        line5.setVisibility(View.GONE);
                        break;
                    case 3:
                        all.setTextColor(getResources().getColor(R.color.text_333));
                        bepay.setTextColor(getResources().getColor(R.color.text_333));
                        befahuo.setTextColor(getResources().getColor(R.color.text_333));
                        beReceve.setTextColor(getResources().getColor(R.color.red));
                        over.setTextColor(getResources().getColor(R.color.text_333));
                        line1.setVisibility(View.GONE);
                        line2.setVisibility(View.GONE);
                        line3.setVisibility(View.GONE);
                        line4.setVisibility(View.VISIBLE);
                        line5.setVisibility(View.GONE);
                        break;
                    case 4:
                        all.setTextColor(getResources().getColor(R.color.text_333));
                        bepay.setTextColor(getResources().getColor(R.color.text_333));
                        befahuo.setTextColor(getResources().getColor(R.color.text_333));
                        beReceve.setTextColor(getResources().getColor(R.color.text_333));
                        over.setTextColor(getResources().getColor(R.color.red));
                        line1.setVisibility(View.GONE);
                        line2.setVisibility(View.GONE);
                        line3.setVisibility(View.GONE);
                        line4.setVisibility(View.GONE);
                        line5.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick({R.id.all, R.id.bepay, R.id.befahuo, R.id.be_receve, R.id.over})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all:
                viewpager.setCurrentItem(0);
                break;
            case R.id.bepay:
                viewpager.setCurrentItem(1);
                break;
            case R.id.befahuo:
                viewpager.setCurrentItem(2);
                break;
            case R.id.be_receve:
                viewpager.setCurrentItem(3);
                break;
            case R.id.over:
                viewpager.setCurrentItem(4);
                break;
        }
    }
}
