package com.BlackDiamond2010.hzs.view.gsy_videoplay;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.BlackDiamond2010.hzs.R;


/**
 * Created by shuyu on 2016/11/11.
 */

public class JumpUtils {

    /**
     * 跳转到视频播放
     *
     * @param activity
     * @param view
     */
    public static void goToVideoPlayer(Activity activity, View view,String path) {
        Intent intent = new Intent(activity, PlayActivity.class);
        intent.putExtra(PlayActivity.TRANSITION, true);
        intent.putExtra("path",path);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Pair pair = new Pair<>(view, PlayActivity.IMG_TRANSITION);
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair);
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
        } else {
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.alpha_enter, R.anim.alpha_exit);
        }
    }

//    /**
//     * 跳转到视频列表
//     *
//     * @param activity
//     */
//    public static void goToVideoPlayer(Activity activity) {
//        Intent intent = new Intent(activity, ListVideoActivity.class);
//        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity);
//        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
//    }
//
//    /**
//     * 跳转到视频列表2
//     *
//     * @param activity
//     */
//    public static void goToVideoPlayer2(Activity activity) {
//        Intent intent = new Intent(activity, ListVideo2Activity.class);
//        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity);
//        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
//    }
//
//    /**
//     * 跳转到视频列表
//     *
//     * @param activity
//     */
//    public static void goToVideoRecyclerPlayer(Activity activity) {
//        Intent intent = new Intent(activity, RecyclerViewActivity.class);
//        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity);
//        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
//    }
//
//    /**
//     * 跳转到视频列表2
//     *
//     * @param activity
//     */
//    public static void goToVideoRecyclerPlayer2(Activity activity) {
//        Intent intent = new Intent(activity, RecyclerView2Activity.class);
//        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity);
//        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
//    }

//    /**
//     * 跳转到详情播放
//     *
    //TODO可能有用
//     * @param activity
//     */
//    public static void goToDetailPlayer(Activity activity) {
//        Intent intent = new Intent(activity, DetailPlayer.class);
//        activity.startActivity(intent);
//    }
//
//    /**
//     * 跳转到详情播放
//     *
//     * @param activity
//     */
//    public static void goToDetailListPlayer(Activity activity) {
//        Intent intent = new Intent(activity, DetailListPlayer.class);
//        activity.startActivity(intent);
//    }


//    /**
//     * 跳转到详情播放
//     *
//     * @param activity
//     */
//    public static void gotoWebDetail(Activity activity) {
//        Intent intent = new Intent(activity, WebDetailActivity.class);
//        activity.startActivity(intent);
//    }

    /**
     * 跳转到弹幕
     *TODO  夏伟 可以能有用
     * @param activity
     */
//    public static void gotoDanmaku(Activity activity) {
//        Intent intent = new Intent(activity, DanmkuVideoActivity.class);
//        activity.startActivity(intent);
//    }

//    /**
//     * 跳转到弹幕
//     *
//     * @param activity
//     */
//    public static void gotoFragment(Activity activity) {
//        Intent intent = new Intent(activity, FragmentVideoActivity.class);
//        activity.startActivity(intent);
//    }
}
