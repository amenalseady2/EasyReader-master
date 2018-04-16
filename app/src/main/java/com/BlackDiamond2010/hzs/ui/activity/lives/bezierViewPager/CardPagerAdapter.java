package com.BlackDiamond2010.hzs.ui.activity.lives.bezierViewPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<String> mData;
    private Context mContext;

    private int MaxElevationFactor = 9;


    @Override
    public int getMaxElevationFactor() {
        return MaxElevationFactor;
    }

    @Override
    public void setMaxElevationFactor(int MaxElevationFactor) {
        this.MaxElevationFactor = MaxElevationFactor;
    }

    private List<GoodsModel> goodsList;

    public CardPagerAdapter(Context context, List<GoodsModel> goodsList) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.goodsList = goodsList;
        this.mContext = context;
    }

    public void addImgUrlList(List<String> imgUrlList) {
        mData.addAll(imgUrlList);

        for (int i = 0; i < imgUrlList.size(); i++) {
            mViews.add(null);
        }
    }


    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public View.OnClickListener mListener;

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardItemClickListener != null) {
                    cardItemClickListener.onClick(position);
                }
            }
        });
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        cardView.setMaxCardElevation(MaxElevationFactor);
        cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));


        TextView text = (TextView) view.findViewById(R.id.tv_item_viewpager_title);
        text.setText(goodsList.get(position).title);

        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(String imgUrl, View view) {
        ImageView iv = (ImageView) view.findViewById(R.id.item_iv);
        Glide.with(mContext).load(imgUrl)
                .placeholder(R.drawable.ic_default_place_holder)
                .error(R.drawable.ic_default_place_holder)
                .into(iv);

    }

    private OnCardItemClickListener cardItemClickListener;

    public interface OnCardItemClickListener {
        void onClick(int position);
    }

    public void setOnCardItemClickListener(OnCardItemClickListener cardItemClickListener) {
        this.cardItemClickListener = cardItemClickListener;
    }

}
