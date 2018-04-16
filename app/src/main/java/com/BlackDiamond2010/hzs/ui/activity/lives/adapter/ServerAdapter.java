package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerViewHolder;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.OrderModel;
import com.BlackDiamond2010.hzs.view.ListViewForScrollView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/10/17.
 */

public class ServerAdapter extends BaseRecyclerAdapter<OrderModel> {

    public View.OnClickListener itemListener;


    @Override
    public void setOnItemClickListener(OnItemClickListener<OrderModel> listener) {
        super.setOnItemClickListener(listener);
    }

    public ServerAdapter(Context context, List<OrderModel> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;

//        ArrayList<GoodsModel> list = new ArrayList<>();
//        GoodsModel mode = new GoodsModel();
//        mode.num = 2;
//        mode.price = "88.8";
//        mode.vip_price = "88.8";
//        mode.local_price = "88.8";
//        mode.name = "钻石路演";
//                mode.cover = "http://cdn.kanjian2020.com/sl1.jpg";
//        list.add(mode );

        viewHolder.myorderChildListview.setAdapter(new OrderGoodsAdapter(null));
        viewHolder.myorderChildListview.setFocusable(false);
        viewHolder.myorderChildRoot.setOnClickListener(itemListener);
        //设置图片的相对于屏幕的宽高比
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        mView = mInflater.inflate(R.layout.item_server, parent, false);
        mViewHolder = new ViewHolder(mView);


        return mViewHolder;
    }

    public class ViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.orderid)
        TextView orderid;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.myorder_child_listview)
        ListViewForScrollView myorderChildListview;
        @BindView(R.id.info)
        TextView info;

        @BindView(R.id.myorder_child_root)
        LinearLayout myorderChildRoot;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
