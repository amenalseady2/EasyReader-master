package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerViewHolder;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.EvaluateModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.constants.StaticConstant;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.DisplayUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.ImageScaleUtil.ImagePagerActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;
import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.BlackDiamond2010.hzs.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/10/17.
 */

public class PingJiaAdapter extends BaseRecyclerAdapter<EvaluateModel> {

    private Context context;

    public View.OnClickListener mListener;



    @Override
    public void setOnItemClickListener(OnItemClickListener<EvaluateModel> listener) {
        super.setOnItemClickListener(listener);
    }

    public PingJiaAdapter(Context context, List<EvaluateModel> datas) {
        super(context, datas);
        this.context = context;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        EvaluateModel model = mDatas.get(position);

        if (StringUtil.isEmpty(model.image)) {
            viewHolder.llImg.setVisibility(View.GONE);
            viewHolder.img1.setVisibility(View.GONE);
            viewHolder.img2.setVisibility(View.GONE);
            viewHolder.img3.setVisibility(View.GONE);
        } else {
            viewHolder.llImg.setVisibility(View.VISIBLE);
            int w = (StaticConstant.sWidth - DisplayUtil.dip2px(mContext, 45)) / 3;

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, w);
            lp.rightMargin = DisplayUtil.dip2px(mContext, 15);
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(w, w);
            viewHolder.img1.setLayoutParams(lp);
            viewHolder.img2.setLayoutParams(lp);
            viewHolder.img3.setLayoutParams(lp1);
            viewHolder.img1.setVisibility(View.GONE);
            viewHolder.img2.setVisibility(View.GONE);
            viewHolder.img3.setVisibility(View.GONE);
            if (model.image.contains(",")) {
                String[] paths = model.image.split(",");
                for (int i = 0; i < paths.length; i++) {

                    if (i == 0) {
                        GlideUtils.loadImage(3, paths[i], viewHolder.img1);
                        viewHolder.img1.setVisibility(View.VISIBLE);
                        viewHolder.img1.setOnClickListener(mListener);

                        viewHolder.img1.setTag(position+"-"+i);

                    } else if (i == 1) {
                        GlideUtils.loadImage(3, paths[i], viewHolder.img2);
                        viewHolder.img2.setVisibility(View.VISIBLE);
                        viewHolder.img2.setOnClickListener(mListener);
                        viewHolder.img2.setTag(position+"-"+i);
                    } else {
                        GlideUtils.loadImage(3, paths[i], viewHolder.img3);
                        viewHolder.img3.setVisibility(View.VISIBLE);
                        viewHolder.img3.setOnClickListener(mListener);
                        viewHolder.img3.setTag(position+"-"+i);
                    }
                }
            } else {
                GlideUtils.loadImage(3, model.image, viewHolder.img1);
                viewHolder.img1.setVisibility(View.VISIBLE);
                viewHolder.img1.setTag(position+"-"+0);
                viewHolder.img1.setOnClickListener(mListener);
            }
        }
        GlideUtils.loadImage(3, model.avatar, viewHolder.img);
        viewHolder.name.setText(model.nickname);
        viewHolder.time.setText(model.create_at);
        viewHolder.content.setText(model.review_content);

        if ("".equals(model.reply_content)){
            viewHolder.huifu.setVisibility(View.GONE);
        }else {
            viewHolder.huifu.setVisibility(View.VISIBLE);
            viewHolder.huifu.setText("掌柜回复："+model.reply_content);
        }
    }


    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        mView = mInflater.inflate(R.layout.item_pingjia, parent, false);
        mViewHolder = new ViewHolder(mView);
        return mViewHolder;
    }

    public class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.img)
        CircleImageView img;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.img1)
        ImageView img1;
        @BindView(R.id.img2)
        ImageView img2;
        @BindView(R.id.img3)
        ImageView img3;
        @BindView(R.id.ll_img)
        LinearLayout llImg;
        @BindView(R.id.huifu)
        TextView huifu;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


   View.OnClickListener listener = new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           String[] tags = v.getTag().toString().split("-");
           ArrayList<String> imgList = new ArrayList<String>();
           if (mDatas.get(Integer.valueOf(tags[0])).image.contains(",")) {
               String[] paths = mDatas.get(Integer.valueOf(tags[0])).image.split(",");
               for (int i = 0; i < paths.length; i++) {
                imgList.add(paths[i]);
               }
           }else {
               imgList.add(mDatas.get(Integer.valueOf(tags[0])).image);
           }
           //路径
           Intent intentToImgPage = new Intent(mContext,
                   ImagePagerActivity.class);
           // 图片url
           intentToImgPage.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS,
                   imgList);
           intentToImgPage.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX,
                   Integer.valueOf(tags[1]));
           mContext.startActivity(intentToImgPage);

       }
   };
}
