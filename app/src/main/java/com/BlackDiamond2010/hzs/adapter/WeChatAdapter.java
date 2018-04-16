package com.BlackDiamond2010.hzs.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.bean.wechat.WXItemBean;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.webview.WebViewActivity;

import java.util.List;

/**
 * Created by quantan.liu on 2017/3/30.
 */

public class WeChatAdapter extends BaseQuickAdapter<WXItemBean, BaseViewHolder> {
    public WeChatAdapter(List<WXItemBean> data) {
        super(R.layout.item_wechat, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final WXItemBean item) {
        GlideUtils.loadMovieTopImg((ImageView) helper.getView(R.id.iv_android_pic),item.getPicUrl());
        helper.setText(R.id.tv_android_des,item.getTitle());
        helper.setText(R.id.tv_android_who,item.getDescription());
        helper.setText(R.id.tv_android_time,item.getCtime());
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(mContext,item.getUrl(),item.getTitle());
            }
        });
    }
}
