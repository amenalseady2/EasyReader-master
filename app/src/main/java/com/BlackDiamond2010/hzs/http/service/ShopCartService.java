package com.BlackDiamond2010.hzs.http.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShopCartService {

    @GET("shopping_cart/action")
    Call<ResponseBody> addCart(@Query("token") String token, @Query("product_id") Integer product_id, @Query("type") String type,
                               @Query("spec") String spec, @Query("num") Integer num);
}
