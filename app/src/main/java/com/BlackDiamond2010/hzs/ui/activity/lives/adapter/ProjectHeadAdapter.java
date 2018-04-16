package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Category;
import com.BlackDiamond2010.hzs.ui.activity.lives.constants.StaticConstant;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.DisplayUtil;

import java.util.List;

/**
 * Created by ASUS on 2017/10/17.
 *
 * 首页案例
 */

public class ProjectHeadAdapter extends BaseAdapter {

    private int index = -1;

    private List<Category> mlist;
    public ProjectHeadAdapter(List<Category> mlist){
        this.mlist=mlist;
    }
    @Override
    public int getCount() {
        return mlist.size();
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
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_projecthead,parent,false);

        TextView text = (TextView) convertView.findViewById(R.id.text);
        int w = (StaticConstant.sWidth- DisplayUtil.dip2px(parent.getContext(),80))/4;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(w,(int)(w*0.41));
        text.setLayoutParams(lp);
        text.setText(mlist.get(position).name);

        if (position == index){
            text.setBackgroundResource(R.drawable.project_perssd);
            text.setTextColor(Color.argb(255,255,255,255));
//            text.setTextColor(R.color.colorWhite);
        }else {
            text.setBackgroundResource(R.drawable.project_nomar);
            text.setTextColor(Color.argb(255,247,157,79));
        }

        return convertView;
    }

    public void setPositon(int positon){
        index = positon;
        notifyDataSetChanged();
    }
}
