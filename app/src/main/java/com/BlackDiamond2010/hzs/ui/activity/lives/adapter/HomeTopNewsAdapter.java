package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;

import java.util.List;

/**
 * Created by ASUS on 2017/10/17.
 */

public class HomeTopNewsAdapter extends BaseAdapter  {


    private List<GoodsModel> list;
    public HomeTopNewsAdapter(List<GoodsModel> list){
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
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_topnews,parent,false);
        TextView tv = (TextView) convertView.findViewById(R.id.title);
        tv.setText(list.get(position).title);

        return convertView;
    }
}
