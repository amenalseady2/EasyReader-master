package com.BlackDiamond2010.hzs.http.service;

import com.BlackDiamond2010.hzs.bean.ProductDetails.Product;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpConfig;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductDetailService {
    @GET(HttpConfig.PRODUCT_DETAIL)
    Call<Product> product(@Query("product_id") String product_id);
}
