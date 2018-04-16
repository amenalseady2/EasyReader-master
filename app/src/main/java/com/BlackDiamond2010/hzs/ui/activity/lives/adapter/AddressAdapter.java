package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerViewHolder;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.AddressModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全城热榜
 */
public class AddressAdapter extends BaseRecyclerAdapter<AddressModel> {

    public View.OnClickListener mListener,deleteListener,morenListener;




    public AddressAdapter(Context context, List<AddressModel> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;

        AddressModel mode = mDatas.get(position);
        if (mode.type == 1) {
            viewHolder.moRen.setText("默认地址");
            viewHolder.moRen.setTextColor(mContext.getResources().getColor(R.color.red));
            viewHolder.checkImg.setImageResource(R.mipmap.choice_normal_checked);


            SHPUtils.saveParame(mContext,SHPUtils.ADDRESSID,mode.id);
            SHPUtils.saveParame(mContext,SHPUtils.ADDRESS,mode.address+mode.info);
            SHPUtils.saveParame(mContext,SHPUtils.NAME,mode.name);
            SHPUtils.saveParame(mContext,SHPUtils.PHONE,mode.phone);
        } else {
            viewHolder.moRen.setText("设为默认");
            viewHolder.moRen.setTextColor(mContext.getResources().getColor(R.color.black));
            viewHolder.checkImg.setImageResource(R.mipmap.choice_normal);
        }
        viewHolder.addressRoot.setTag(""+position);
        viewHolder.addressRoot.setOnClickListener(mListener);
        viewHolder.addressName.setText(mode.name);
        viewHolder.address.setText(mode.address+mode.info);
        viewHolder.addressPhone.setText(mode.phone);
        viewHolder.deleteText.setTag(""+position);
        viewHolder.deleteImg.setTag(""+position);
        viewHolder.deleteText.setOnClickListener(deleteListener);
        viewHolder.deleteImg.setOnClickListener(deleteListener);

        viewHolder.checkImg.setTag(""+position);
        viewHolder.moRen.setTag(""+position);
        viewHolder.checkImg.setOnClickListener(morenListener);
        viewHolder.moRen.setOnClickListener(morenListener);




    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        mView = mInflater.inflate(R.layout.address_item, parent, false);
        mViewHolder = new ViewHolder(mView);




        return mViewHolder;
    }

    public class ViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.moren_icon)
        ImageView morenIcon;
        @BindView(R.id.address_name)
        TextView addressName;
        @BindView(R.id.address_phone)
        TextView addressPhone;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.div)
        View div;
        @BindView(R.id.check_img)
        ImageView checkImg;
        @BindView(R.id.mo_ren)
        TextView moRen;
        @BindView(R.id.delete_img)
        ImageView deleteImg;
        @BindView(R.id.delete_text)
        TextView deleteText;
        @BindView(R.id.bianji_text)
        TextView bianjiText;
        @BindView(R.id.bianji_img)
        ImageView bianjiImg;
        @BindView(R.id.address_root)
        RelativeLayout addressRoot;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public void setData(List<AddressModel> mList){
        mDatas = mList ;
        notifyDataSetChanged();
    }
    public void addData(List<AddressModel> mList){
        if (mList != null && mList.size()!= 0){
            mDatas.addAll(mList);
            notifyDataSetChanged();
        }
    }
}
