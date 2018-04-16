package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.GoodsDetailXuzhiAdapter;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;

import java.util.List;

import butterknife.BindView;

public class XuZhiFragment extends BaseFragment {

    @BindView(R.id.rcv_activity)
    RecyclerView rcvActivity;
    private   List<String>  xuzhiList ;
    public XuZhiFragment(   List<String>  xuzhiList ){
        this.xuzhiList = xuzhiList ;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void loadData() {
        setState(AppConstants.STATE_SUCCESS);
        rcvActivity.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvActivity.setAdapter(new GoodsDetailXuzhiAdapter(getContext(),xuzhiList));


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

}
