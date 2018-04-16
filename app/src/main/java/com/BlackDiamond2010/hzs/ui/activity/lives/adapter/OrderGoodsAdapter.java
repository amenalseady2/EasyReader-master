package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import java.util.List;

/**
 * Created by ASUS on 2017/10/17.
 *
 * 首页案例
 */

public class OrderGoodsAdapter extends BaseAdapter {
    List<GoodsModel> shopModelList;
    public   OrderGoodsAdapter(List<GoodsModel> shopModelList) {
this.shopModelList = shopModelList;
    }
    @Override
    public int getCount() {
        return shopModelList.size();
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
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_goods,parent,false);

        TextView goodsName = (TextView) convertView.findViewById(R.id.goodsname);
        TextView goodsprice = (TextView) convertView.findViewById(R.id.goodsprice);
        TextView num = (TextView) convertView.findViewById(R.id.num);
        ImageView goodsimg = (ImageView) convertView.findViewById(R.id.goodsimg);
      GoodsModel model =  shopModelList.get(position);
        GlideUtils.loadImage(3,model.cover,goodsimg);
        goodsName.setText(model.name);
        goodsprice.setText("￥"+model.price);
        num.setText("X"+model.num);
        return convertView;
    }
}
