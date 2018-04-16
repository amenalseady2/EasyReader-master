package com.BlackDiamond2010.hzs.injector.module.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.BlackDiamond2010.hzs.adapter.ZhiHuCommentAdapter;
import com.BlackDiamond2010.hzs.bean.zhihu.CommentBean;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quantan.liu on 2017/4/8.
 */
@Module
public class ZhihuCommentModule {
    @Provides
    @Singleton
    public BaseQuickAdapter provideAdapter() {
        return new ZhiHuCommentAdapter(new ArrayList<CommentBean.CommentsBean>());
    }
}
