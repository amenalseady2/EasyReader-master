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
import com.BlackDiamond2010.hzs.ui.activity.lives.constants.StaticConstant;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全城热榜
 */
public class MyLikeAdapter extends BaseRecyclerAdapter<GoodsModel> {

    public View.OnClickListener itemListener;


    public MyLikeAdapter(Context context, List<GoodsModel> datas) {
        super(context, datas);
    }

    public void setData(List<GoodsModel> datas) {
        if (datas != null && datas.size() != 0) {
            mDatas = datas;
            notifyDataSetChanged();
        }
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        ViewGroup.LayoutParams params = viewHolder.ivGoods.getLayoutParams();
        GoodsModel goods = mDatas.get(position);
        //设置图片的相对于屏幕的宽高比
        params.width = StaticConstant.sWidth / 2 - 30;
        if (goods.ratio == 0) {
            goods.ratio = 1;
        }
        params.height =  (int) (params.width*goods.ratio) ;
//         TODO: 2018/4/1
//        params.height = params.width;
        viewHolder.ivGoods.setLayoutParams(params);
        GlideUtils.loadImage(2, mDatas.get(position).cover, viewHolder.ivGoods);
        viewHolder.homeTitle.setText(goods.name);
        viewHolder.price.setText("￥" + goods.price);

        viewHolder.llContent.setTag(goods.id);
        viewHolder.llContent.setOnClickListener(itemListener);
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        mView = mInflater.inflate(R.layout.item_mylike, parent, false);
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
