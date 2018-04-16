package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerViewHolder;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Livebean;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全城热榜
 */
public class LiveAdapter extends BaseRecyclerAdapter<Livebean> {

    public View.OnClickListener mListener;



    public LiveAdapter(Context context, List<Livebean> datas) {
        super(context, datas);
    }

    /**
     * @param seconds 多少秒
     * */
    private void  formtData(int seconds,TextView text){
        int day =  0;
        day = seconds/60/60/24;
        int  h = (seconds - day*60*60*24)/60/60;
        int m = (seconds -  day*60*60*24  -   h*60*60 )/60;
        int  s = (seconds -  day*60*60*24  -   h*60*60  - m*60);
        String  hString  ;
        String mString ;
        String sString ;
        if (h < 10) {
            hString = "0"+h;
        }else {
            hString = ""+h;
        }
        if (m < 10) {
            mString = "0"+m;
        }else {
            mString = ""+m;
        }
        if (s< 10) {
            sString = "0"+s;
        }else {
            sString = ""+s;
        }
        text.setText(hString + "时" + mString + "分" + sString + "分" );
    }


    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;

        Livebean bean = mDatas.get(position);
        if (bean.status == 0) {
            viewHolder.liveStatus.setBackgroundResource(R.mipmap.yugao_shouye);
            viewHolder.liveStatus.setText("预告");
            viewHolder.titleBg.setVisibility(View.VISIBLE);
            viewHolder.tvPersonalnumAndTime.setVisibility(View.VISIBLE);
            viewHolder.ivRen.setVisibility(View.GONE);

            if (Integer.valueOf(bean.start_time)/60/60/24 > 0 ){
                viewHolder.tvPersonalnumAndTime.setText(bean.start_at);
            }else {
                formtData(Integer.valueOf(bean.start_time),viewHolder.tvPersonalnumAndTime);
            }
        } else if (bean.status == 1) {

            viewHolder.liveStatus.setBackgroundResource(R.mipmap.zhibozhong_shouye);
            viewHolder.liveStatus.setText("直播中");
            viewHolder.titleBg.setVisibility(View.VISIBLE);
            viewHolder.tvPersonalnumAndTime.setVisibility(View.VISIBLE);
            viewHolder.ivRen.setVisibility(View.VISIBLE);
            viewHolder.tvPersonalnumAndTime.setText(bean.pv+"");


        } else if (bean.status == 2) {
            viewHolder.liveStatus.setBackgroundResource(R.mipmap.huigu_shouye);
            viewHolder.liveStatus.setText("回顾");
            viewHolder.titleBg.setVisibility(View.GONE);
            viewHolder.tvPersonalnumAndTime.setVisibility(View.GONE);
            viewHolder.ivRen.setVisibility(View.GONE);
        }
        GlideUtils.loadImage(2,bean.cover,viewHolder.ivBigimgpath);
        viewHolder.title.setText(bean.title);
        viewHolder.time.setText(bean.create_at);
        viewHolder.root.setTag(bean.id+"_"+bean.status);
        viewHolder.root.setOnClickListener(mListener);
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        mView = mInflater.inflate(R.layout.item_live, parent, false);
        mViewHolder = new ViewHolder(mView);
        return mViewHolder;
    }

    public class ViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.div)
        View div;
        @BindView(R.id.iv_bigimgpath)
        ImageView ivBigimgpath;
        @BindView(R.id.title_bg)
        View titleBg;
        @BindView(R.id.live_status)
        TextView liveStatus;
        @BindView(R.id.tv_personalnum_and_time)
        TextView tvPersonalnumAndTime;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.root)
        RelativeLayout root;
        @BindView(R.id.iv_ren)
        ImageView ivRen;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public void setData(List<Livebean> mList){
        mDatas = mList ;
        notifyDataSetChanged();
    }
    public void addData(List<Livebean> mList){
        if (mList != null && mList.size()!= 0){
            mDatas.addAll(mList);
            notifyDataSetChanged();
        }
    }
//    public void removeAll() {
//        mDatas.clear();
//        notifyDataSetChanged();
//    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }






    public void CountDown(){

        for ( Livebean bean:
             mDatas) {
            if (bean.status == 0 ){
                int totalSecend = Integer.valueOf(bean.start_time);
                if (totalSecend>0){
                    --totalSecend;
                    bean.start_time = ""+totalSecend;
                }
            }
        }

        notifyDataSetChanged();
    }


    private int seconds;
    private Timer timer;
    private MyTimerTask mTimerTask;
    private boolean flag;

    /**
     * 设置获取验证码按钮不能点击并开始倒计时
     */
    public void setUnClick() {
        timer = new Timer(true);
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }
        mTimerTask = new MyTimerTask();
        timer.schedule(mTimerTask, 1000, 1000);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                  CountDown();
                    break;
            }
        };
    };


    /**
     * 自定义任务定时执行器
     *
     * @author Liuj
     */
    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            handler.sendEmptyMessage(1);
        }
    }
}
