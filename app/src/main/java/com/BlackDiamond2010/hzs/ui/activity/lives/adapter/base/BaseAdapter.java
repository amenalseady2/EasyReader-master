package com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter implements OnClickListener{
	protected String TAG = "MainListViewAdapter";
	protected List<T> mData = new ArrayList<T>();
	protected Context mContext;
	protected int mPageIndex;
	protected String mType;
	protected String mTypeID;

	public BaseAdapter(Context context) {
		mContext = context;
		TAG = getClass().getSimpleName();
		refresh(new ArrayList<T>());
	}

	public BaseAdapter(Context context, List<T> list) {
		this(context);
		refresh(list);
	}

	public BaseAdapter<T> refresh(List<T> list) {
		if (list == null) {
			// throw new IllegalArgumentException("Adapter dataSource is null");
			Log.e(TAG, "Adapter dataSource is null");
			list = new ArrayList<T>();
		} else {
			if(list.size() > 0){
				T t = list.get(0);
			}
		}
		mPageIndex++;
		mData.addAll(list);
		notifyDataSetChanged();
		return this;
	}
	
	public  BaseAdapter<T> replace(List<T> list){
		clear();
		refresh(list);
		return this;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public T getItem(int position) {
		return mData.get(position);
	}

	@Override
	public final long getItemId(int position) {
		return 0;
	}

	public final List<T> getList() {
		return mData != null ? mData : new ArrayList<T>();
	}

	public final BaseAdapter<T> clear() {
		mPageIndex = 1;
		mData.clear();
		notifyDataSetChanged();
		return this;
	}

	public final int getPageIndex() {
		return mPageIndex;
	}
	
	public void setPageIndex(int page) {
		mPageIndex=page;
	}
	
	public int getPageSize(){
		return 10;
	}
	
	public final BaseAdapter<T> setType(String type, String typeId) {
		this.mType = type;
		this.mTypeID = typeId;
		return this;
	}
	
	public boolean isNeedOnItemClick(){
		return true;
	}
	
	public Type getTypeToken(){
		return new TypeToken<ArrayList<T>>(){}.getType();
	}
	
	@Override
	public void onClick(View v) {
	}
}
