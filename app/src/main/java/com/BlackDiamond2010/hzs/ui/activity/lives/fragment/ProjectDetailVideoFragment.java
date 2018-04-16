package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.VideoListAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.VideoModel;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;
import com.BlackDiamond2010.hzs.view.XRecycleView;
import com.BlackDiamond2010.hzs.view.gsy_videoplay.JumpUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ASUS on 2017/10/23.
 */

public class ProjectDetailVideoFragment extends BaseFragment {
    @BindView(R.id.video_recycle)
    XRecycleView videoRecycle;
    private List<VideoModel> videoList;
    private Context context;
    private Activity activity;


    public ProjectDetailVideoFragment(List<VideoModel> videoList, Context context,Activity activity) {
        this.videoList = videoList;
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected void loadData() {

        setState(AppConstants.STATE_SUCCESS);

//        List<VideoModel> videoList = new ArrayList<>();
//
//        VideoModel model =new VideoModel();
//        model.cover = "http://img2.imgtn.bdimg.com/it/u=2838934065,571280381&fm=27&gp=0.jpg";
//        model.path = "http://cdn.kanjian2020.com/v11.mp4";
//        VideoListAdapter mAdapter =  new VideoListAdapter(context,videoList);
//        videoRecycle.setAdapter(mAdapter);
//
//        mAdapter.mListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                JumpUtils.goToVideoPlayer(activity, v,v.getTag().toString());
//            }
//        };


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_videolist;
    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        //设置分割线
        videoRecycle.setLayoutManager(layoutManager);
        VideoListAdapter mAdapter =  new VideoListAdapter(context,videoList);
        videoRecycle.setAdapter(mAdapter);

        mAdapter.mListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.goToVideoPlayer(activity, v,v.getTag().toString());
            }
        };


    }

    @Override
    protected void initInject() {

    }

}
