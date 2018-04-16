package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerViewHolder;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全城热榜
 */
public class ShopCartAdapter extends BaseRecyclerAdapter<GoodsModel> {


    public View.OnClickListener itemListener;
    public PriceAddGoodsNumCall call;
    public View.OnClickListener add_jian_Listener;


    @Override
    public void setOnItemClickListener(OnItemClickListener<GoodsModel> listener) {
        super.setOnItemClickListener(listener);
    }

    public ShopCartAdapter(Context context, List<GoodsModel> datas, PriceAddGoodsNumCall call) {
        super(context, datas);
        this.call = call;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        GoodsModel mode = mDatas.get(position);
        viewHolder.itemRoot.setTag(mode.product_id);
        viewHolder.itemRoot.setOnClickListener(itemListener);
        viewHolder.shopcartItemCheckbox.setTag("" + position);
        viewHolder.shopcartItemCheckbox.setOnClickListener(checkListener);

        viewHolder.num.setText(mode.num + "");
        viewHolder.shopcartItemCheckbox.setChecked(mode.ischeck);
        GlideUtils.loadImage(3, mode.cover, viewHolder.img);
        viewHolder.num.setText("" + mode.num);
        viewHolder.price.setText("￥" + mode.price);
        viewHolder.vipPrice.setText("VIP会员价:￥" + mode.vip_price);
        viewHolder.orgPrice.setText("￥" + mode.local_price);
        viewHolder.orgPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.title.setText(mode.name);
        viewHolder.ivJian.setTag(""+position);
        viewHolder.ivAdd.setTag(""+position);
        if (add_jian_Listener == null){
            add_jian_Listener = new myLitener();
        }
        viewHolder.ivAdd.setOnClickListener(add_jian_Listener );
        viewHolder.ivJian.setOnClickListener(add_jian_Listener);


    }


    private class myLitener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int index = Integer.valueOf(v.getTag().toString());
            GoodsModel model =      mDatas.get(index);
            if (v.getId() == R.id.iv_add){
                // 加
                ++model.num;
                notifyDataSetChanged();
            }else{
                if(model.num>1){
                    --model.num;
                    notifyDataSetChanged();
                }
            }

            call.goodsNumChange(model);
        }
    }


    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        mView = mInflater.inflate(R.layout.item_shopcart, parent, false);
        mViewHolder = new ViewHolder(mView);
        return mViewHolder;
    }

    View.OnClickListener checkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = Integer.valueOf(v.getTag().toString());
            GoodsModel mode = mDatas.get(index);
            mode.ischeck = !mode.ischeck;
            //TODO  没全选的  把全选的狗去掉
            call.setcheck(isAll());
            totalPrice();
            notifyDataSetChanged();
        }
    };

    /**
     * 判断是否全选
     */
    public Boolean isAll() {
        boolean flag = true;

        for (GoodsModel model :
                mDatas) {
            if (!model.ischeck) {
                flag = false;
                break;
            }
        }


        return flag;
    }

    public class ViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.shopcart_item_checkbox)
        CheckBox shopcartItemCheckbox;
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.org_price)
        TextView orgPrice;
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.vip_price)
        TextView vipPrice;
        @BindView(R.id.item_root)
        RelativeLayout itemRoot;
        @BindView(R.id.iv_add)
        ImageView ivAdd;
        @BindView(R.id.iv_jian)
        ImageView ivJian;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setData(List<GoodsModel> list) {
        mDatas.clear();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<GoodsModel> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public GoodsModel getitem(int position) {
        return mDatas.get(position);
    }

    //全选或者全不选
    public void all(boolean ischeck) {
        for (GoodsModel model :
                mDatas) {
            model.ischeck = ischeck;
        }
        totalPrice();

        notifyDataSetChanged();
        //suanjiage
    }


    //在要删除和购买的时候去 查看那些商品勾选了 type 1  购买  2 删除

    public void isCheckGoods(int type) {
        ArrayList<GoodsModel> list = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        for (GoodsModel model :
                mDatas) {
            if (model.ischeck) {
                idList.add(model.id);
                list.add(model);
            }
        }

        if (type == 1) {
            call.wantToBuyGoodsList(list);
        } else {
            call.delectGoods(idList);
        }
    }


    public void totalPrice() {
        int goodCount = 0;
        BigDecimal total = new BigDecimal(0.00f + "");
        for (GoodsModel good : mDatas) {
            if (good.ischeck) {
                goodCount = goodCount + good.num;
                total = total.add(new BigDecimal(good.price + "")
                        .multiply(new BigDecimal(good.num + "")));
            }
        }
        String allPrice = total.toString();
        call.priceCallback(allPrice + "");
//        callback.goodsNumCallback(goodCount + "");
    }


    public interface PriceAddGoodsNumCall {
        //总价回调
        void priceCallback(String price);

        //数量回调
        void goodsNumCallback(String price);

        //全选回调
        void setcheck(boolean flag);

        //要删除的 id回调
        void delectGoods(List<String> goodscartIdList);

        //被选中的 商品
        void wantToBuyGoodsList(ArrayList<GoodsModel> goodsListid);//返回选中的商品id的list

        void goodsNumChange(GoodsModel goodsModel);

        //被选中的 商品


    }
}
