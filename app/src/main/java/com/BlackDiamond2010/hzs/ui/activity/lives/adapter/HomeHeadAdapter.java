package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerViewHolder;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全城热榜
 */
public class HomeHeadAdapter extends BaseRecyclerAdapter<GoodsModel> {

    public View.OnClickListener itemListener;

    public HomeHeadAdapter(Context context, List<GoodsModel> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        GoodsModel mode = mDatas.get(position);
        GlideUtils.loadImage(3,mode.cover,viewHolder.ivGoods);
        viewHolder.homeTitle.setText(mode.name);
        viewHolder.price.setText("￥"+mode.price);
        viewHolder.llContent.setTag(mode.id);
        viewHolder.llContent.setOnClickListener(itemListener);
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        mView = mInflater.inflate(R.layout.adapter_home_head, parent, false);
        mViewHolder = new ViewHolder(mView);
        return mViewHolder;
    }

    public class ViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.home_title)
        TextView homeTitle;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.ll_content)
        LinearLayout llContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
}
