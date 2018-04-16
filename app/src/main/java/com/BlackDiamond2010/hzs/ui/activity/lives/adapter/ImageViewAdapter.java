package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import java.util.List;

/**
 * Created by ASUS on 2017/10/17.
 *
 * 首页案例
 */

public class ImageViewAdapter extends BaseAdapter {
    List<String> list ;
    public ImageViewAdapter(List<String> list){
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
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img,parent,false);
        ImageView tv = (ImageView) convertView.findViewById(R.id.img);
        GlideUtils.loadImage(2,list.get(position),tv);
        return convertView;
    }
}
