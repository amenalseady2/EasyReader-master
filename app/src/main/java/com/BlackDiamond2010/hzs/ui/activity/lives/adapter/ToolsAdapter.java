package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerViewHolder;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ToolsModel;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/10/17.
 */

public class ToolsAdapter extends BaseRecyclerAdapter<ToolsModel> {


    public View.OnClickListener itemListener;


    @Override
    public void setOnItemClickListener(OnItemClickListener<ToolsModel> listener) {
        super.setOnItemClickListener(listener);
    }

    public void setData(List<ToolsModel> mList) {
        mDatas = mList;
        notifyDataSetChanged();
    }

    public void addData(List<ToolsModel> mList) {
        if (mList != null && mList.size() != 0) {
            mDatas.addAll(mList);
            notifyDataSetChanged();
        }
    }

    public ToolsAdapter(Context context, List<ToolsModel> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;

        ToolsModel model = mDatas.get(position);
        GlideUtils.loadImage(3, model.cover, viewHolder.img);

        if (model.pay_num == 0) {
            viewHolder.goumai.setText("暂时没人购买");
        } else {
            viewHolder.goumai.setText("已有" + model.pay_num + "人购买");
        }

        viewHolder.title.setText(model.name);
        viewHolder.orgPrice.setText("￥" + model.local_price);
        viewHolder.orgPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.vipPrice.setText("VIP会员价：￥" + model.vip_price);
        viewHolder.price.setText("￥" + model.price);

        viewHolder.itemRoot.setTag(model.id+"");
        viewHolder.itemRoot.setOnClickListener(itemListener);
        //设置图片的相对于屏幕的宽高比
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        mView = mInflater.inflate(R.layout.item_tools, parent, false);
        mViewHolder = new ViewHolder(mView);


        return mViewHolder;
    }

    public class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.goumai)
        TextView goumai;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.org_price)
        TextView orgPrice;
        @BindView(R.id.vip_price)
        TextView vipPrice;
        @BindView(R.id.item_root)
        RelativeLayout itemRoot;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
