package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerViewHolder;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Risgter;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/10/17.
 */

public class RecordAdapter extends BaseRecyclerAdapter<Risgter> {




    @Override
    public void setOnItemClickListener(OnItemClickListener<Risgter> listener) {
        super.setOnItemClickListener(listener);
    }

    public RecordAdapter(Context context, List<Risgter> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        Risgter model = mDatas.get(position);
        viewHolder.orderid.setText("订单号"+model.order_sn);
        if (StringUtil.isEmpty(model.end_at)){
            viewHolder.limit.setText("有效期截止：");
        }else {
            viewHolder.limit.setText("有效期截止："+model.end_at);
        }
        viewHolder.time.setText(model.create_at);
        viewHolder.price.setText("-"+model.price+"元");
        viewHolder.title.setText(model.info);
        //设置图片的相对于屏幕的宽高比
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        mView = mInflater.inflate(R.layout.item_record, parent, false);
        mViewHolder = new ViewHolder(mView);
        return mViewHolder;
    }

    public class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.orderid)
        TextView orderid;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.limit)
        TextView limit;
        @BindView(R.id.price)
        TextView price;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setData(List<Risgter> mList) {
        mDatas = mList;
        notifyDataSetChanged();
    }

    public void addData(List<Risgter> mList) {
        if (mList != null && mList.size() != 0) {
            mDatas.addAll(mList);
            notifyDataSetChanged();
        }
    }
}
