package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FragmentPagerAdapter {
	List<Fragment> list;

	public MainAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<Fragment>();
		}
	}

	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}
	
	
}
