package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerViewHolder;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ProModel;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全城热榜
 */
public class ProjectAdapter extends BaseRecyclerAdapter<ProModel> {

    public View.OnClickListener itemListener;



    @Override
    public void setOnItemClickListener(OnItemClickListener<ProModel> listener) {
        super.setOnItemClickListener(listener);
    }

    public ProjectAdapter(Context context, List<ProModel> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;


        //设置图片的相对于屏幕的宽高比
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(StaticConstant.sWidth, (int) (StaticConstant.sWidth * 0.43));
//        viewHolder.reImg.setLayoutParams(params);
        ProModel mode = mDatas.get(position);
        GlideUtils.loadImage(2, mode.cover, viewHolder.img);
        GlideUtils.loadImage(2, mode.logo, viewHolder.ivLogo);
        viewHolder.tvCompanyname.setText(mode.name);

        String status = "其他";
        if (mode.status == 1) {
            status = "拟挂牌";
        } else if (mode.status == 2) {
            status = "已挂牌";
        }
        viewHolder.companyStatus.setText(status);
        if (mode.is_collection == 1) {
            viewHolder.ivCollectBtn.setImageResource(R.drawable.soucang_pressed);
        } else {
            viewHolder.ivCollectBtn.setImageResource(R.drawable.soucang_normal);
        }
        viewHolder.tvHangye.setText("行业:" + mode.trade_name);
        viewHolder.tvPersonalnum.setText(mode.pv + "");
        viewHolder.itemProjectRoot.setTag(""+position);
        viewHolder.itemProjectRoot.setOnClickListener(itemListener);


    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        mView = mInflater.inflate(R.layout.item_project, parent, false);
        mViewHolder = new ViewHolder(mView);


        return mViewHolder;
    }

    public class ViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.re_img)
        RelativeLayout reImg;
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.company_status)
        TextView companyStatus;
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_companyname)
        TextView tvCompanyname;
        @BindView(R.id.tv_hangye)
        TextView tvHangye;
        @BindView(R.id.iv_collect_btn)
        ImageView ivCollectBtn;
        @BindView(R.id.tv_personalnum)
        TextView tvPersonalnum;
        @BindView(R.id.item_project_root)
        LinearLayout itemProjectRoot;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setData(List<ProModel> list) {
        mDatas.clear();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<ProModel> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public ProModel getitem(int position) {
        return mDatas.get(position);
    }
}
