package com.BlackDiamond2010.hzs.ui.activity.lives.bezierViewPager;


import android.support.v7.widget.CardView;

interface CardAdapter {
    CardView getCardViewAt(int position);

    int getCount();

    int getMaxElevationFactor();

    void setMaxElevationFactor(int MaxElevationFactor);
}
