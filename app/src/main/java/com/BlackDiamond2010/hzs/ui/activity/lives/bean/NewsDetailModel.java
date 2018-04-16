package com.BlackDiamond2010.hzs.ui.activity.lives.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/11/4.
 */

public class NewsDetailModel extends BaseBean {

    public NewsModel detail;
    public List<NewsModel> other = new ArrayList<>();
    public int is_collection;
    public int total_like;
    public int is_like;
}
