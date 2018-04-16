package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;

import butterknife.BindView;

/**
 * Created by ASUS on 2017/10/23.
 */

public class ProjectDatailtwoFragment extends BaseFragment {
    @BindView(R.id.tv_project_detail)
    TextView tvProjectDetail;
    private String content;


    public ProjectDatailtwoFragment(String content) {
        this.content = content;
    }


    @Override
    protected void loadData() {
        setState(AppConstants.STATE_SUCCESS);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.text_item;
    }

    @Override
    protected void initView() {
        tvProjectDetail.setText(content);
    }


    @Override
    protected void initInject() {

    }

}
