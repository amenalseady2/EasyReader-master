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
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Livebean;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全城热榜
 */
public class SubscirbeLiveAdapter extends BaseRecyclerAdapter<Livebean> {

    public View.OnClickListener mListener;



    @Override
    public void setOnItemClickListener(OnItemClickListener<Livebean> listener) {
        super.setOnItemClickListener(listener);
    }

    public SubscirbeLiveAdapter(Context context, List<Livebean> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {


        ViewHolder viewHolder = (ViewHolder) holder;
        Livebean mode = mDatas.get(position);

        GlideUtils.loadImage(2,mode.cover,viewHolder.ivImg);
        viewHolder.tvTime.setText(mode.create_at);
        viewHolder.tvTitle.setText(mode.title);
        viewHolder.tvSeenum.setText(mode.pv+"人观看");
        if (mode.status == 0){
            viewHolder.status.setText("预告");
        }else if (mode.status == 1){
            viewHolder.status.setText("直播中");
        }else {
            viewHolder.status.setText("回顾");
        }

        viewHolder.oneimgRoot.setTag(mode.status+","+mode.id);
        viewHolder.oneimgRoot.setOnClickListener(mListener);
    }


    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        mView = mInflater.inflate(R.layout.item_subscribe_live, parent, false);
        mViewHolder = new ViewHolder(mView);
        return mViewHolder;
    }


    public class ViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_seenum)
        TextView tvSeenum;
        @BindView(R.id.oneimg_root)
        LinearLayout oneimgRoot;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public void setData(List<Livebean> list) {
        mDatas.clear();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<Livebean> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

}
