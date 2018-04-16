package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LiveDetailMessage;

import java.util.List;

/**
 * Created by ASUS on 2017/10/17.
 *
 * 首页案例
 */

public class MessageDetailAdapter extends BaseAdapter {
    private List<LiveDetailMessage> mseesagList;
    private int type ;
    public MessageDetailAdapter( List<LiveDetailMessage> mseesagList,int type){
        this.mseesagList = mseesagList;
        this.type = type;
    }
    @Override
    public int getCount() {
        return  mseesagList.size();
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
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messagedetail,parent,false);

        LiveDetailMessage  message =  mseesagList.get(position);
        TextView contentText = (TextView) convertView.findViewById(R.id.content);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        TextView timeText = (TextView) convertView.findViewById(R.id.time_text);
        timeText.setText(message.time);
        contentText.setText(message.content);
//        GlideUtils.loadImage(3,message.avatar,img);
        if (type == 2){
            img.setImageResource(R.mipmap.gouwutongzhi_xiaoxi);
        }else if(type == 4){
            img.setImageResource(R.mipmap.jinrihuati_xiaoxi);
        }else {
            img.setImageResource(R.mipmap.xiaomishu_xiaoxi);
        }
        return convertView;

    }
}
