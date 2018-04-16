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
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.NewsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.constants.StaticConstant;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全城热榜
 */
public class TopNewsAdapter extends BaseRecyclerAdapter<NewsModel> {

    public View.OnClickListener mListener;


    @Override
    public void setOnItemClickListener(OnItemClickListener<NewsModel> listener) {
        super.setOnItemClickListener(listener);
    }

    public TopNewsAdapter(Context context, List<NewsModel> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {


        NewsModel mode = mDatas.get(position);
        if (mode.cover_count <= 1) {
            final ViewHolder viewHolder = (ViewHolder) holder;

            ViewGroup.LayoutParams params = viewHolder.ivImg.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            params.width = (StaticConstant.sWidth - 84) / 3;
            viewHolder.ivImg.setLayoutParams(params);
            GlideUtils.loadImage(2, mode.cover, viewHolder.ivImg);
            viewHolder.tvJingjiren.setText(mode.author);
            viewHolder.tvTitle.setText(mode.title);
            viewHolder.tvTime.setText(mode.create_at);
            viewHolder.tvLooknum1.setText(mode.pv+"");
            viewHolder.oneimgRoot.setTag(""+mode.id);
            viewHolder.oneimgRoot.setOnClickListener(mListener);


        } else {
            //TODO
            final Mux_imgHolder viewHolder = (Mux_imgHolder) holder;
            if (mode.cover != null) {
                if (mode.cover.contains(",")) {
                    String[] paths = mode.cover.split(",");
                    for (int i = 0; i < paths.length; i++) {

                        if (i == 0) {
                            GlideUtils.loadImage(2, paths[i], viewHolder.ivImg1);
                        } else if (i == 1) {
                            GlideUtils.loadImage(2, paths[i], viewHolder.ivImg2);
                        } else {
                            GlideUtils.loadImage(2, paths[i], viewHolder.ivImg3);
                        }
                    }
                }

                viewHolder.moreimgRoot.setTag(""+mode.id);
                viewHolder.moreimgRoot.setOnClickListener(mListener);
                viewHolder.tvLooknum.setText(mode.pv+"");

            }


            viewHolder.tvJingjiren.setText(mode.author);
            viewHolder.tvTitle.setText(mode.title);
            viewHolder.tvTime.setText(mode.create_at);
//            viewHolder.tvLooknum.setText(mode.pv);

            int w = (StaticConstant.sWidth - 84) / 3;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.rightMargin = 12;
            viewHolder.ivImg1.setLayoutParams(lp);
            viewHolder.ivImg2.setLayoutParams(lp);
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT);
            viewHolder.ivImg3.setLayoutParams(lp1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        NewsModel mode = mDatas.get(position);
        int viewT = 0;
        if (mode.cover_count <= 1) {
            viewT = 1;
        }

        return viewT;
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        if (viewType == 1) {

            mView = mInflater.inflate(R.layout.item_topnews, parent, false);
            mViewHolder = new ViewHolder(mView);
        } else {
            mView = mInflater.inflate(R.layout.head_topnews, parent, false);
            mViewHolder = new Mux_imgHolder(mView);
        }


        return mViewHolder;
    }


    public class Mux_imgHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_img1)
        ImageView ivImg1;
        @BindView(R.id.iv_img2)
        ImageView ivImg2;
        @BindView(R.id.iv_img3)
        ImageView ivImg3;
        @BindView(R.id.tv_jingjiren)
        TextView tvJingjiren;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_personlook)
        TextView tvLooknum;
        @BindView(R.id.moreimg_root)
        LinearLayout moreimgRoot;

        Mux_imgHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_jingjiren)
        TextView tvJingjiren;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_seenum)
        TextView tvLooknum1;
        @BindView(R.id.iv_img)
        ImageView ivImg;
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

    public void setData(List<NewsModel> list) {
        mDatas.clear();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<NewsModel> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public NewsModel getItem(int position){
        return mDatas.get(position);
    }

}
