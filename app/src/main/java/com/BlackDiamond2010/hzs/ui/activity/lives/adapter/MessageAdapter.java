package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MessageType;

import java.util.List;

/**
 * Created by ASUS on 2017/10/17.
 *
 * 首页案例
 */

public class MessageAdapter extends BaseAdapter {
    private List<MessageType> mseesag;
    public MessageAdapter( List<MessageType> mMessageList){
        mseesag = mMessageList;
    }
    @Override
    public int getCount() {
        return mseesag.size();
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
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);

        TextView contentText = (TextView) convertView.findViewById(R.id.content);
        TextView numText = (TextView) convertView.findViewById(R.id.num);
        TextView timeText = (TextView) convertView.findViewById(R.id.time_text);
        TextView typeText = (TextView) convertView.findViewById(R.id.tv_type);
        ImageView image = (ImageView) convertView.findViewById(R.id.img);


        MessageType model = mseesag.get(position);
        timeText.setText(model.time);
        contentText.setText(model.content);
        if (model.num == 0 ){
            numText.setVisibility(View.GONE);
        }else{
            numText.setText(model.num+"");
            numText.setVisibility(View.VISIBLE);
        }

        if (model.type ==2 ){
            typeText.setText("购物通知");
            image.setImageResource(R.mipmap.gouwutongzhi_xiaoxi);
        }else if (model.type ==4 ){
            typeText.setText("头条");
            image.setImageResource(R.mipmap.jinrihuati_xiaoxi);
        }else {
            typeText.setText("小秘书");
            image.setImageResource(R.mipmap.xiaomishu_xiaoxi);

        }

        return convertView;
    }
}
