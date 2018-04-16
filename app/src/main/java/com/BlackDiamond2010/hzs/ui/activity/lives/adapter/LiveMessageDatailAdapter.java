package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LiveDetailMessage;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.CircleImageView;

import java.util.List;

/**
 * Created by ASUS on 2017/10/17.
 *
 * 首页案例
 */

public class LiveMessageDatailAdapter extends BaseAdapter {
    List<LiveDetailMessage> list ;
    public LiveMessageDatailAdapter(List<LiveDetailMessage> list){
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
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_livedetail,parent,false);
        CircleImageView tv = (CircleImageView) convertView.findViewById(R.id.userhead);
        TextView nameText = (TextView) convertView.findViewById(R.id.name);
        TextView c = (TextView) convertView.findViewById(R.id.content);
        GlideUtils.loadImage(3,list.get(position).avatar,tv);
        nameText.setText(list.get(position).nickname+":");
        c.setText(list.get(position).content);
        return convertView;
    }
    public void setData(List<LiveDetailMessage> messagesList){
        this.list = messagesList;
        notifyDataSetChanged();

    }
}
