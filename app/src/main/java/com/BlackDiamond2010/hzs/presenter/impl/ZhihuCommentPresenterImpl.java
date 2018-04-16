package com.BlackDiamond2010.hzs.presenter.impl;

import com.BlackDiamond2010.hzs.bean.zhihu.CommentBean;
import com.BlackDiamond2010.hzs.http.service.ZhiHuService;
import com.BlackDiamond2010.hzs.http.utils.Callback;
import com.BlackDiamond2010.hzs.presenter.BasePresenter;
import com.BlackDiamond2010.hzs.presenter.ZhihuCommentPresenter;

import javax.inject.Inject;

/**
 * Created by quantan.liu on 2017/3/25.
 */

public class ZhihuCommentPresenterImpl extends BasePresenter<ZhihuCommentPresenter.View> implements ZhihuCommentPresenter.Presenter {
    private ZhiHuService mZhiHuService;

    @Inject
    public ZhihuCommentPresenterImpl(ZhiHuService mZhiHuService) {
        this.mZhiHuService = mZhiHuService;
    }


    public void fetchLongCommentInfo(int id){
        invoke(mZhiHuService.fetchLongCommentInfo(id),new Callback<CommentBean>(){
            @Override
            public void onResponse(CommentBean data) {
                checkState(data.getComments());
                mView.refreshView(data.getComments());
            }
        });
    }
    public void fetchShortCommentInfo(int id){
        invoke(mZhiHuService.fetchShortCommentInfo(id),new Callback<CommentBean>(){
            @Override
            public void onResponse(CommentBean data) {
                checkState(data.getComments());
                mView.refreshView(data.getComments());
            }
        });
    }

}
