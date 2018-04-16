package com.BlackDiamond2010.hzs.http.service;

import com.BlackDiamond2010.hzs.bean.zhihu.CommentBean;
import com.BlackDiamond2010.hzs.bean.zhihu.DailyListBean;
import com.BlackDiamond2010.hzs.bean.zhihu.DetailExtraBean;
import com.BlackDiamond2010.hzs.bean.zhihu.HotListBean;
import com.BlackDiamond2010.hzs.bean.zhihu.SectionChildListBean;
import com.BlackDiamond2010.hzs.bean.zhihu.SectionListBean;
import com.BlackDiamond2010.hzs.bean.zhihu.ThemeChildListBean;
import com.BlackDiamond2010.hzs.bean.zhihu.ThemeListBean;
import com.BlackDiamond2010.hzs.bean.zhihu.WelcomeBean;
import com.BlackDiamond2010.hzs.bean.zhihu.ZhihuDetailBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by quantan.liu on 2017/3/21.
 */

public interface ZhiHuService {
    String HOST = "http://news-at.zhihu.com/api/4/";
    /**
     * 启动界面图片
     */
    @GET("start-image/{res}")
    Observable<WelcomeBean> fetchWelcomeInfo(@Path("res") String res);

    /**
     * 最新日报
     */
    @GET("news/latest")
    Observable<DailyListBean> fetchDailyList();
    /**
     * 主题日报
     */
    @GET("themes")
    Observable<ThemeListBean> fetchThemeList();

    /**
     * 主题日报详情
     */
    @GET("theme/{id}")
    Observable<ThemeChildListBean> fetchThemeChildList(@Path("id") int id);

    /**
     * 专栏日报
     */
    @GET("sections")
    Observable<SectionListBean> fetchSectionList();

    /**
     * 专栏日报详情
     */
    @GET("section/{id}")
    Observable<SectionChildListBean> fetchSectionChildList(@Path("id") int id);

    /**
     * 热门日报
     */
    @GET("news/hot")
    Observable<HotListBean> fetchHotList();

    /**
     * 日报详情
     */
    @GET("news/{id}")
    Observable<ZhihuDetailBean> fetchDetailInfo(@Path("id") int id);

    /**
     * 日报的额外信息
     */
    @GET("story-extra/{id}")
    Observable<DetailExtraBean> fetchDetailExtraInfo(@Path("id") int id);

    /**
     * 日报的长评论
     */
    @GET("story/{id}/long-comments")
    Observable<CommentBean> fetchLongCommentInfo(@Path("id") int id);

    /**
     * 日报的短评论
     */
    @GET("story/{id}/short-comments")
    Observable<CommentBean> fetchShortCommentInfo(@Path("id") int id);
}
