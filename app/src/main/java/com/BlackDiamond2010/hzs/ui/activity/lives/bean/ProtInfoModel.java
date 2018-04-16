package com.BlackDiamond2010.hzs.ui.activity.lives.bean;/**
 * Created by yq on 2016/7/26.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 项目信息
 * 创建时间：2016/7/26 14:59
 */
public class ProtInfoModel extends BaseBean {

    public String id;
    public String cover_video;
    public String name;
    public String trade_name;
    public int pv;
    public String phone;
    public String contact;
    public String online_time;
    public String company_info;
    public String industry_info;
    public String cover;
    public List<VideoModel> video =new ArrayList<>();

    public String  core_info;
    public String  cover_video_img;
    public String  pm_name;
    public String  pm_phone;
    public String  good_at;
    public String  share;
}
