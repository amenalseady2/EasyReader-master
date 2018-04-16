package com.BlackDiamond2010.hzs.ui.activity.lives.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/10/25.
 */

public class OrderModel extends BaseBean {

    public String id ;
    public int status;//1 quanbu   2
    public List<GoodsModel> goodsList = new ArrayList<>();
    public int totalNum;
    public String totalPrice;


    public String total_price;
    public int  product_count;//

    public String freight;
    public int service ;//

    public String order_sn ; //

}
