package com.BlackDiamond2010.hzs.ui.fragment.home.child.zhihu;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.bean.zhihu.CommentBean;
import com.BlackDiamond2010.hzs.injector.component.fragment.DaggerZhihuCommentComponent;
import com.BlackDiamond2010.hzs.injector.module.fragment.ZhihuCommentModule;
import com.BlackDiamond2010.hzs.injector.module.http.ZhihuHttpModule;
import com.BlackDiamond2010.hzs.presenter.ZhihuCommentPresenter;
import com.BlackDiamond2010.hzs.presenter.impl.ZhihuCommentPresenterImpl;
import com.BlackDiamond2010.hzs.ui.activity.zhihu.ZhiHuCommentActivity;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;

import java.util.List;

import butterknife.BindView;

/**
 * Created by quantan.liu on 2017/3/25.
 */

public class ZhiHuCommentFragment extends BaseFragment<ZhihuCommentPresenterImpl> implements ZhihuCommentPresenter.View {

    @BindView(R.id.rv_zhihu_comment)
    RecyclerView rvZhihuComment;

    private boolean isShort;

    public static ZhiHuCommentFragment getInstance(boolean isShort) {
        ZhiHuCommentFragment instance = new ZhiHuCommentFragment();
        instance.isShort = isShort;
        return instance;
    }

    @Override
    protected void initView() {
        rvZhihuComment.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void loadData() {
        ZhiHuCommentActivity mZhiHuCommentActivity = (ZhiHuCommentActivity) getActivity();
        int id = mZhiHuCommentActivity.getId();
        if (isShort) {//懒加载在可见的时候加载，会让非静态变量最终都是同一个值所以只能用静态变量。
            mPresenter.fetchShortCommentInfo(id);
        } else {
            mPresenter.fetchLongCommentInfo(id);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhihu_comment;
    }

    @Override
    protected void initInject() {
        DaggerZhihuCommentComponent.builder()
                .zhihuHttpModule(new ZhihuHttpModule())
                .zhihuCommentModule(new ZhihuCommentModule())
                .build().injectZhihuComment(this);
    }



    @Override
    public void refreshView(List<CommentBean.CommentsBean> list) {
        mAdapter.setNewData(list);
        rvZhihuComment.setAdapter(mAdapter);
    }
}
