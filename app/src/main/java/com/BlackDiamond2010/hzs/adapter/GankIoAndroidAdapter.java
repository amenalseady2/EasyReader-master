package com.BlackDiamond2010.hzs.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.bean.gankio.GankIoDataBean;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.utils.TimeUtil;
import com.BlackDiamond2010.hzs.webview.WebViewActivity;

import java.util.List;

/**
 * Created by quantan.liu on 2017/3/30.
 */

public class GankIoAndroidAdapter extends BaseQuickAdapter<GankIoDataBean.ResultBean, BaseViewHolder> {
    public GankIoAndroidAdapter(List<GankIoDataBean.ResultBean> data) {
        super(R.layout.item_wechat, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GankIoDataBean.ResultBean item) {
        ImageView ivAndroidPic = helper.getView(R.id.iv_android_pic);
        // 显示gif图片会很耗内存
        if (item.getImages() != null
                && item.getImages().size() > 0
                && !TextUtils.isEmpty(item.getImages().get(0))) {
            ivAndroidPic.setVisibility(View.VISIBLE);
            GlideUtils.loadMovieTopImg(ivAndroidPic, item.getImages().get(0));
        } else {
            ivAndroidPic.setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_android_des, item.getDesc());
        helper.setText(R.id.tv_android_who, item.getWho());
        helper.setText(R.id.tv_android_time, TimeUtil.getTranslateTime(item.getPublishedAt()));
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(mContext, item.getUrl(), item.getSource());
            }
        });
    }
}
