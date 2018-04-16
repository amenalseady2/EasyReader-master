package com.BlackDiamond2010.hzs.ui.activity.lives.util;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.BlackDiamond2010.hzs.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by ASUS on 2017/11/9.
 */

public class ShareUtil {
    Activity activity;
    private View view;
    private String title;
    private int res;
    private String content;
    private String url;

    /**
     * @param activity 上下文
     * @param view     activity的根佈局
     * @param title     标题
     * @param res      本地資源圖片
     * @param content  分享的内容
     * @param url      分享的url
     */
    public ShareUtil(Activity activity, View view, String title, int res, String content, String url,int flag) {
        this.activity = activity;
        this.view = view;
        this.title = title;
        this.res = res;
        this.content = content;
        this.url = url;
//        new ShareAction(activity)
//                .withText("http://api.kanjian2020.com/app-release.apk")
//                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
//                .setCallback(shareListener)
//                .open();

        if (flag == 1){
            showInfoWindow(activity);
        }

    }

    PopupWindow mPopupWindow;
    public void showInfoWindow(Activity activity) {


        View popView = LayoutInflater.from(activity).inflate(R.layout.share_ui,
                null, false);

       mPopupWindow = new PopupWindow(popView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.style.AnimBottom);
//        // popView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
//        popView.findViewById(R.id.funtions).setOnClickListener(click);
//        popView.findViewById(R.id.img).setOnClickListener(click);
//        popView.findViewById(R.id.camera).setOnClickListener(click);
//        popView.findViewById(R.id.choise_cancel).setOnClickListener(click);
        popView.findViewById(R.id.weichat).setOnClickListener(listener);
        popView.findViewById(R.id.weichat_friend).setOnClickListener(listener);
        popView.findViewById(R.id.sina).setOnClickListener(listener);
        popView.findViewById(R.id.qq).setOnClickListener(listener);
        popView.findViewById(R.id.share_root).setOnClickListener(listener);
        popView.findViewById(R.id.cancle).setOnClickListener(listener);
        mPopupWindow.showAtLocation(view,
                Gravity.BOTTOM, 0, 0);
    }

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int id = v.getId();
            switch (id) {
                case R.id.share_root:
                case R.id.cancle:
                    mPopupWindow.dismiss();
                    break;
                case R.id.weichat:
                    ShareOne(SHARE_MEDIA.WEIXIN);

                    break;
                case R.id.weichat_friend:
                    ShareOne(SHARE_MEDIA.WEIXIN_CIRCLE);
                    break;

                case R.id.sina:
                    ShareOne(SHARE_MEDIA.SINA);
                    break;

                case R.id.qq:
                    ShareOne(SHARE_MEDIA.QQ);
                    break;


            }

        }
    };


    public void ShareOne(SHARE_MEDIA media) {

        UMImage img = new UMImage(activity, R.drawable.luyan_logo);
//        if (!StringUtil.isEmpty(imageurl)) {
//            img = new UMImage(activity, imageurl);
//        }

        UMWeb web = new UMWeb(url);
        web.setTitle(title);
        web.setThumb(img);
        web.setDescription(content);


        new ShareAction(activity)
                .withText(content)
                .withMedia(web)
                .setPlatform(media)
                .setCallback(shareListener).share();


    }


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(activity, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(activity, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(activity, "取消了", Toast.LENGTH_LONG).show();

        }
    };
}
