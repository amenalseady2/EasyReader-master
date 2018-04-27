package com.BlackDiamond2010.hzs.http.service;

import com.BlackDiamond2010.hzs.bean.ProductDetails.MessageUtil;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhoneService {

    @GET("bind_sms")
    Call<MessageUtil> send(@Query("token") String token, @Query("phone") String phone);

    @GET("bind_phone")
    Call<MessageUtil> changeBind(@Query("token") String token, @Query("phone") String phone, @Query("code") String code);
}
