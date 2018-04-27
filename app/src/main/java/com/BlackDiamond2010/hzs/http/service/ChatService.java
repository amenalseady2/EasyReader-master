package com.BlackDiamond2010.hzs.http.service;

import com.BlackDiamond2010.hzs.bean.chat.Chat;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChatService {

    @GET("chat/list")
    Call<Chat> getChatList(@Query("id") String id);
}
