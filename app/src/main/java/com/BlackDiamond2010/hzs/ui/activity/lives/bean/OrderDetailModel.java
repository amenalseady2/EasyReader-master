package com.BlackDiamond2010.hzs.ui.activity.lives.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/11/12.
 */

public class OrderDetailModel {

    public String id ;//
    public String order_sn ;//
    public String  create_at;//
    public String  total_price;//
    public String  freight;//
    public String  express_name;//
    public String  express_sn;//运单号
    public int  pay_type;//
    public String  address;//
    public String  phone;//
    public String  name;//
    public String  end_time;//
    public int is_review;
    public String shop_phone;

    public List<GoodsModel> product_list = new ArrayList<>();

}
