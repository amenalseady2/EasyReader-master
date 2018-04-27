package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.adapter.TopicAdapter;
import com.BlackDiamond2010.hzs.bean.message.Topic;
import com.BlackDiamond2010.hzs.http.service.MessageService;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TodayTopicActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private TopicAdapter adapter;
    private List<Topic.DataBean.ListBean> allValues = new ArrayList<>();
    private ListView topic_list_view;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_today_topic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topic_list_view = this.findViewById(R.id.topic_list_view);

        setTitle("今日话题");
        topic_list_view.setOnItemClickListener(this);

        getTopicList();
    }

    private void getTopicList() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://47.95.224.184/api/")
                .build();
        MessageService messageService = retrofit.create(MessageService.class);
        Call<Topic> call = messageService.getTopicList();
        call.enqueue(new Callback<Topic>() {
            @Override
            public void onResponse(Call<Topic> call, Response<Topic> response) {
                Topic topic = response.body();
                if (topic.getCode() == 200) {
                    allValues = topic.getData().getList();

                    adapter = new TopicAdapter(TodayTopicActivity.this, allValues);
                    topic_list_view.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Topic> call, Throwable t) {
                mackToastSHORT(t.getMessage(), TodayTopicActivity.this);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Topic.DataBean.ListBean listBean = allValues.get(i);
        Intent intent = new Intent(this, TopicDetailActivity.class);
        intent.putExtra("url", listBean.getUrl());
        startActivity(intent);
    }
}
