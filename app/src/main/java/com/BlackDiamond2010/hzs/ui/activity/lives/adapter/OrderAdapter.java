package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.OrderDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.RefundActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerViewHolder;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderItem;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.view.ListViewForScrollView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/10/17.
 */

public class OrderAdapter extends BaseRecyclerAdapter<MyOrderItem> {

    public View.OnClickListener itemListener;

    public View.OnClickListener btnListener;


    @Override
    public void setOnItemClickListener(OnItemClickListener<MyOrderItem> listener) {
        super.setOnItemClickListener(listener);
    }

    public OrderAdapter(Context context, List<MyOrderItem> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final MyOrderItem mode = mDatas.get(position);
        viewHolder.myorderChildListview.setAdapter(new OrderGoodsAdapter(mode.product_list));


        viewHolder.orderroot.setTag(mode);
        viewHolder.orderroot.setOnClickListener(itemListener);
        //设置图片的相对于屏幕的宽高比

        viewHolder.button1.setTag(mode.id + "," + 1 + "," + mode.status + "," + mode.order_sn + "," + mode.pay_type + "," + position);
        viewHolder.button2.setTag(mode.id + "," + 2 + "," + mode.status + "," + position);
        viewHolder.button3.setTag(mode.id + "," + 3 + "," + mode.status);
        viewHolder.button1.setOnClickListener(btnListener);
        viewHolder.button2.setOnClickListener(btnListener);
        viewHolder.button3.setOnClickListener(btnListener);
        viewHolder.orderid.setText("订单号：" + mode.order_sn);

        viewHolder.button2.setVisibility(View.GONE);
        viewHolder.button3.setVisibility(View.GONE);

        if (mode.service == 0) {
            viewHolder.myorderChildListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CommonUtils.startActWithData(parent.getContext(), OrderDetailActivity.class, "bean", mode);
                }
            });
            if (mode.status == 0) {
                viewHolder.status.setText("待付款");
                viewHolder.button1.setText("去支付");
                viewHolder.button2.setText("取消订单");
                viewHolder.button2.setVisibility(View.VISIBLE);
            } else if (mode.status == 1) {
                viewHolder.status.setText("待发货");

                viewHolder.button1.setText("提醒发货");
            } else if (mode.status == 2) {
                viewHolder.status.setText("待收货");
                viewHolder.button1.setText("确定收货");
                viewHolder.button2.setText("查看物流");
                viewHolder.button2.setVisibility(View.VISIBLE);
            } else if (mode.status == 3) {
                viewHolder.status.setText("完成");
                if (mode.is_review != 1) {

                    viewHolder.button1.setText("晒单评价");
                    viewHolder.button2.setText("申请售后");
                    viewHolder.button3.setText("删除订单");
                    viewHolder.button2.setVisibility(View.VISIBLE);
                    viewHolder.button3.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.button3.setText("删除订单");
                    viewHolder.button3.setVisibility(View.VISIBLE);
                    viewHolder.button1.setVisibility(View.GONE);
                }
            }
        } else {
            viewHolder.myorderChildListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CommonUtils.startActWithData(parent.getContext(), RefundActivity.class, "model", mode);
                }
            });
            viewHolder.rlBottom.setVisibility(View.GONE);
//            1退款 2退货退款 3退款中 4已退款 5交易关闭
            switch (mode.service) {

                case 1:
                    viewHolder.status.setText("退款");

                    break;
                case 2:
                    viewHolder.status.setText("退货退款");

                    break;
                case 3:
                    viewHolder.status.setText("退款中");

                    break;
                case 4:
                    viewHolder.status.setText("已退款");

                    break;
                case 5:
                    viewHolder.status.setText("交易关闭");

                    break;

            }
        }


        viewHolder.info.setText("共" + mode.product_count + "件商品  合计：￥" + mode.total_price + "(含运费)");
    }

    public MyOrderItem getItems(int p) {
        return mDatas.get(p);
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        mView = mInflater.inflate(R.layout.item_order, parent, false);
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
        @BindView(R.id.button1)
        TextView button1;
        @BindView(R.id.button2)
        TextView button2;
        @BindView(R.id.button3)
        TextView button3;
        @BindView(R.id.myorder_child_root)
        LinearLayout myorderChildRoot;
        @BindView(R.id.rl_bottom)
        RelativeLayout rlBottom;
        @BindView(R.id.orderroot)
        LinearLayout orderroot;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setData(List<MyOrderItem> mList) {
        mDatas = mList;
        notifyDataSetChanged();
    }

    public void addData(List<MyOrderItem> mList) {
        if (mList != null && mList.size() != 0) {
            mDatas.addAll(mList);
            notifyDataSetChanged();
        }
    }

    public void clearAll(){
        mDatas.clear();
        notifyDataSetChanged();
    }
}
