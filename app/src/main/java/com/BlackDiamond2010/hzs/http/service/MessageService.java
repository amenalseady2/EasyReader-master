package com.BlackDiamond2010.hzs.http.service;

import com.BlackDiamond2010.hzs.bean.message.Topic;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MessageService {
    @GET("topic/list")
    Call<Topic> getTopicList();
}
