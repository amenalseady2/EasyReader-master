package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Livebean;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import java.util.List;

/**
 * Created by ASUS on 2017/10/17.
 */

public class HomeLiveAdapter extends BaseAdapter {
    private List<Livebean> list;

    public HomeLiveAdapter(List<Livebean> list) {
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
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_live, parent, false);
        ImageView ivGoods = (ImageView) convertView.findViewById(R.id.iv_goods);
        TextView homeTitle = (TextView) convertView.findViewById(R.id.home_title);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_content);
        TextView liveStatus = (TextView) convertView.findViewById(R.id.live_status);
        Livebean bean = list.get(position);
        GlideUtils.loadImage(2, bean.cover, ivGoods);
        homeTitle.setText(bean.title);
        time.setText(bean.start_at);


        if (bean.status == 0) {
            liveStatus.setBackgroundResource(R.mipmap.yugao_shouye);
            liveStatus.setText("预告");


        } else if (bean.status == 1) {

            liveStatus.setBackgroundResource(R.mipmap.zhibozhong_shouye);
            liveStatus.setText("直播中");


        } else if (bean.status == 2) {
            liveStatus.setBackgroundResource(R.mipmap.huigu_shouye);
            liveStatus.setText("回顾");


        }
        return convertView;
    }
}