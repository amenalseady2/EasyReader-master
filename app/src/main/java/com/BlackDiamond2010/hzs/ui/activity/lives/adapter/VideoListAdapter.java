package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerViewHolder;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.VideoModel;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全城热榜
 */
public class VideoListAdapter extends BaseRecyclerAdapter<VideoModel> {

    public View.OnClickListener mListener;
    private Context context;


    @Override
    public void setOnItemClickListener(OnItemClickListener<VideoModel> listener) {
        super.setOnItemClickListener(listener);
    }

    public VideoListAdapter(Context context, List<VideoModel> datas) {
        super(context, datas);
        this.context = context;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {

        ViewHolder hold = (ViewHolder) holder;
        VideoModel mode = mDatas.get(position);
        GlideUtils.loadImage(2,mode.cover,hold.ivCover);
        hold.ivBofang.setTag(""+mDatas.get(position).path);

       hold.ivBofang.setOnClickListener(mListener);

    }


    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;

        mView = mInflater.inflate(R.layout.item_video, parent, false);
        mViewHolder = new ViewHolder(mView);


        return mViewHolder;
    }


    public class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.iv_bofang)
        ImageView ivBofang;
        @BindView(R.id.tv_videotitle)
        TextView tvVideotitle;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public void setData(List<VideoModel> list) {
        mDatas.clear();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<VideoModel> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

}
