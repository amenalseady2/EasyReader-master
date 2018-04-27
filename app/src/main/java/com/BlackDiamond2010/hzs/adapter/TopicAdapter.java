package com.BlackDiamond2010.hzs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.bean.message.Topic;

import java.util.List;

public class TopicAdapter extends BaseAdapter {
    private Context mContext;
    private List<Topic.DataBean.ListBean> allValues;

    public TopicAdapter(Context mContext, List<Topic.DataBean.ListBean> allValues) {
        this.mContext = mContext;
        this.allValues = allValues;
    }

    @Override
    public int getCount() {
        return allValues.size();
    }

    @Override
    public Object getItem(int i) {
        return allValues.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_today_topic_adapter, null);
        }
        TextView today_topic_title = view.findViewById(R.id.today_topic_title);
        TextView today_topic_time = view.findViewById(R.id.today_topic_time);
        Topic.DataBean.ListBean listBean = allValues.get(i);
        today_topic_title.setText(listBean.getTitle());
        today_topic_time.setText(listBean.getCreate_at());
        return view;
    }
}
