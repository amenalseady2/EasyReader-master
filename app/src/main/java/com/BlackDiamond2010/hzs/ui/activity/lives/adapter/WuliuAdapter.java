package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Risgter;

import java.util.List;

/**
 * Created by ASUS on 2017/10/17.
 *
 * 首页案例
 */

public class WuliuAdapter extends BaseAdapter {
    List<Risgter> list ;
    public WuliuAdapter(List<Risgter> list){
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
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wuliu_item,parent,false);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
      if (position!= 0){
          img.setImageResource(R.mipmap.wuliu2);
      }
        TextView text = (TextView) convertView.findViewById(R.id.content);
        text.setText(list.get(position).content);

        TextView time = (TextView) convertView.findViewById(R.id.time);
        time.setText(list.get(position).time);

        return convertView;
    }
}
