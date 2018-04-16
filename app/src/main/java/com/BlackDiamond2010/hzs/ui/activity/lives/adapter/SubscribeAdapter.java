package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerViewHolder;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.SubscribeModel;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全城热榜
 */
public class SubscribeAdapter extends BaseRecyclerAdapter<SubscribeModel> {


    public View.OnClickListener itemListener;

    @Override
    public void setOnItemClickListener(OnItemClickListener<SubscribeModel> listener) {
        super.setOnItemClickListener(listener);
    }

    public SubscribeAdapter(Context context, List<SubscribeModel> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        SubscribeModel model = mDatas.get(position);
        viewHolder.subscribeRoot.setTag(model);
        viewHolder.subscribeRoot.setOnClickListener(itemListener);
        GlideUtils.loadImage(3, model.avatar,viewHolder.img);
        viewHolder. name.setText(model.name);
        viewHolder.tvFans.setText(model.fans+"个粉丝");
        viewHolder.tvDingyue.setTag(""+position);
        viewHolder.tvDingyue.setOnClickListener(itemListener);
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        mView = mInflater.inflate(R.layout.item_subscribe, parent, false);
        mViewHolder = new ViewHolder(mView);
        return mViewHolder;
    }

    public class ViewHolder extends BaseRecyclerViewHolder {


        @BindView(R.id.img)
        CircleImageView img;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.tv_fans)
        TextView tvFans;
        @BindView(R.id.yidingyue)
        TextView tvDingyue;
        @BindView(R.id.rl_dingyue)
        RelativeLayout rlDingyue;
        @BindView(R.id.subscribe_root)
        LinearLayout subscribeRoot;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setData(List<SubscribeModel> list) {
        mDatas.clear();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void clearAll() {
        mDatas.clear();

        notifyDataSetChanged();
    }

    public void addData(List<SubscribeModel> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public SubscribeModel getitem(int position) {
        return mDatas.get(position);
    }
}
