package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;


import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import java.util.List;

/***
 * ADAPTER
 */

public class SampleAdapter extends ArrayAdapter<String> {

    private static final String TAG = "SampleAdapter";


    private final LayoutInflater mLayoutInflater;
    List<GoodsModel> mList;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    public SampleAdapter(final Context context, final int textViewResourceId,  List<GoodsModel> mList){

        super(context, textViewResourceId);
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        convertView = mLayoutInflater.inflate(R.layout.item_mylike, parent, false);

        GlideUtils.load(parent.getContext(),mList.get(position).img,(ImageView) convertView.findViewById(R.id.iv_goods));
        return convertView;
    }
}