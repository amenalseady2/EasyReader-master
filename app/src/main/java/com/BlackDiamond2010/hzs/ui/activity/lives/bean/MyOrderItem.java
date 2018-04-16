package com.BlackDiamond2010.hzs.ui.activity.lives.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/11/9.
 */

public class MyOrderItem extends BaseBean {
    public String total_price;
    public int  product_count;//
    public int freight;
    public int service ;//
    public String total;
    public String order_sn ; //
    public int status;
    public int is_review; //1 以评价
    public String id ;
    public String end_time;//倒计时
    public List<GoodsModel> product_list= new ArrayList<>();


    public int pay_type;


    public int is_notice;

}
