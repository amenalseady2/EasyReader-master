package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import java.util.List;

/**
 * Created by ASUS on 2017/10/17.
 */

public class HomeHotSaleAdapter extends BaseAdapter {
    int w;
    List<GoodsModel> list;

    public HomeHotSaleAdapter(int w, List<GoodsModel> list) {
        this.w = w;
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

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mylike, parent, false);

        LinearLayout root = (LinearLayout) convertView.findViewById(R.id.ll_content);
//        root.setBackgroundResource(parent.getContext().getResources().getColor(R.color.colorWhite));
        root.setBackgroundResource(R.color.colorWhite);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, (int) (w * 1.37));
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv_goods);
        iv.setLayoutParams(lp);

        GoodsModel mode = list.get(position);
        GlideUtils.loadImage(2, mode.cover, iv);
        TextView tvN = (TextView) convertView.findViewById(R.id.home_title);
        TextView pri = (TextView) convertView.findViewById(R.id.price);
        tvN.setText(mode.name);
        pri.setText("￥" + mode.price);
        return convertView;
    }

}
