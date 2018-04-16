package com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends
        RecyclerView.Adapter<BaseRecyclerViewHolder> {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDatas;
    protected OnItemClickListener<T> mOnItemClickListener;

    public BaseRecyclerAdapter(Context context, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void addItemLast(List<T> datas) {
        mDatas.addAll(datas);
    }

    public void addItemTop(List<T> datas) {
        mDatas = datas;
    }

    public void remove(int position) {
        mDatas.remove(position);
    }

    public void removeAll() {
        if (mDatas != null) {
            mDatas.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    // 点击事件接口
    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T model);

        void onItemLongClick(View view, int position, T model);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        try {
            showViewHolder(holder, position);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewHOldeHolder(parent, viewType);
    }


    protected abstract void showViewHolder(BaseRecyclerViewHolder holder, int position) throws ParseException;

    /***
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent,
                                                                    int viewType);

}

