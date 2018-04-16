package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.NewsModel;

import java.util.List;

/**
 * Created by ASUS on 2017/10/17.
 *
 * 首页案例
 */

public class NewsDetailAdapter extends BaseAdapter {
    List<NewsModel> list;
    public NewsDetailAdapter(List<NewsModel> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newsdetail,parent,false);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView auther = (TextView) convertView.findViewById(R.id.auther);
        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        TextView tv_seenum = (TextView) convertView.findViewById(R.id.tv_seenum);

        NewsModel model = list.get(position);
        title.setText(model.title);
        auther.setText(model.author);
        tv_time.setText(model.create_at);
        tv_seenum.setText(model.pv+"");


        return convertView;
    }
}