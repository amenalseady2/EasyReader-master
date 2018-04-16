package com.BlackDiamond2010.hzs.ui.activity.lives.util;/**
 * Created by yq on 2016/8/13.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 类描述： 常用工具类
 * 创建人：xw
 * 创建时间：2016/8/13 14:56
 * 修改人：xw
 * 修改时间：2016/8/13 14:56
 * 修改备注：
 */
public class CommonUtils {
    /**
     * 启动Activity
     *
     * @param context
     * @param cls
     */
    public static void startAct(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void startActWithData(Context context, Class<?> cls,String key,String vulue) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(key,vulue);
        context.startActivity(intent);
    }

    /**
     * 启动Activity，并带数据
     *
     * @param context
     * @param cls
     * @param intentName
     * @param intentValue
     */
    public static void startActWithData(Context context, Class<?> cls,
                                        String intentName, Serializable intentValue) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(intentName, intentValue);
        context.startActivity(intent);
    }

    public static void startActWithDataForResult(Activity act, Class<?> cls,
                                                 String intentName, Serializable intentValue, int requestCode) {
        Intent intent = new Intent(act, cls);
        intent.putExtra(intentName, intentValue);
        act.startActivityForResult(intent, requestCode);
    }
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    /**
     * 启动Activity，并结束当前Activity
     *
     * @param act
     * @param cls
     */
    public static void startActWithFinish(Activity act, Class<?> cls) {
        startAct(act, cls);
        act.finish();
    }

    /**
     * 启动Activity，并结束当前Activity,并结束其他activity
     *
     * @param act
     * @param cls
     */
    public static void startActFlagWithFinish(Activity act, Class<?> cls) {
        Intent intent = new Intent(act, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        act.startActivity(intent);
        act.finish();
    }

    /**
     * 启动Activity
     *
     * @param act
     * @param cls
     * @param requestCode
     */
    public static void startActForResultWithData(Activity act, Class<?> cls,
                                                 int requestCode, Bundle extras) {
        Intent intent = new Intent(act, cls);
        intent.putExtras(extras);
        act.startActivityForResult(intent, requestCode);
    }

    /**
     * 启动Activity
     *
     * @param act
     * @param cls
     * @param requestCode
     */
    public static void startActForResult(Activity act, Class<?> cls,
                                         int requestCode) {
        Intent intent = new Intent(act, cls);
        act.startActivityForResult(intent, requestCode);
    }

    /**
     * 同步一下cookie
     */
    public static void synCookies(Context context, String ticket, String url) {
        //设置cookie
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, "ticket=" + ticket);//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }
/*
     *
     * @param context   上下文
     * @param focusView 获取焦点的View，可通过**.getCurrentFocus()方法获取
     * @author mslan
     * @date 2015-7-2 下午5:24:07
     */
    public static void closeKeyboard(Context context, View focusView) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
    }

    /**
     * 显示软键盘
     *
     * @param context   上下文
     * @param focusView 获取焦点的View，可通过**.getCurrentFocus()方法获取
     * @author mslan
     * @date 2015-7-2 下午5:26:21
     */
    public static void showKeyboard(Context context, View focusView) {
        InputMethodManager inputManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(focusView, 0);
    }

    /**
     * 点击输入法完成，隐藏键盘
     *
     * @param focusView
     */
    public static void hideKeyBoardClickDone(final Context context, EditText focusView) {
        focusView.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    KeyBoardUtils.closeKeyboard(context, ((Activity) context).getCurrentFocus());
                }
                return true;
            }

        });
    }



    /**
     * 混合两个bitmap
     *
     * @param @param  background
     * @param @param  foreground
     * @param @return
     * @return Bitmap
     * @author yeqing
     * @date 2015-12-7 下午5:33:45
     */
    public static Bitmap toConformBitmap(Bitmap background, Bitmap foreground) {
        if (background == null) {
            return null;
        }

        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        // create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
        Bitmap newbmp = Bitmap
                .createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);
        // draw bg into
        cv.drawBitmap(background, 0, 0, null);// 在 0，0坐标开始画入bg
        // draw fg into
        cv.drawBitmap(foreground, (bgWidth - fgWidth) / 2,
                (bgHeight - fgHeight) / 2, null);// 在 0，0坐标开始画入fg ，可以从任意位置画入
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储
        return newbmp;
    }

    /**
     * Drawable转化为Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = 100;
        int height = 100;
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 距离：单位为米
     */
    private static final double EARTH_RADIUS = 6378137;

    public static double DistanceOfTwoPoints(double lat1, double lng1,
                                             double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

//    /**
//     * 同步一下cookie
//     */
//    public static void synCookies(Context context, String url) {
//        //设置cookie
//        String ticket = PreferenceHelper.getIntance()
//                .readString(BaseConfig.TICKET, "");
//        CookieSyncManager.createInstance(context);
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);
//        cookieManager.removeSessionCookie();//移除
//        cookieManager.setCookie(url, "ticket=" + ticket);//cookies是在HttpClient中获得的cookie
//        CookieSyncManager.getInstance().sync();
//    }




    // Android跳转应用市场的应用详情页
    public static Intent getIntent(Context paramContext) {
        StringBuilder localStringBuilder = new StringBuilder()
                .append("market://details?id=");
        String str = paramContext.getPackageName();
        localStringBuilder.append(str);
        Uri localUri = Uri.parse(localStringBuilder.toString());
        return new Intent("android.intent.action.VIEW", localUri);
    }

    // Android跳转应用市场的应用详情页
    public static void start(Context paramContext, String paramString) {
        Uri localUri = Uri.parse(paramString);
        Intent localIntent = new Intent("android.intent.action.VIEW", localUri);
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        paramContext.startActivity(localIntent);
    }

    // Android跳转应用市场的应用详情页
    public static boolean judge(Context paramContext, Intent paramIntent) {
        List localList = paramContext.getPackageManager()
                .queryIntentActivities(paramIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if ((localList != null) && (localList.size() > 0)) {
            return false;
        } else {
            return true;
        }
    }

//    /**
//     * 设置全屏弹窗的高度为减去状态栏的高度
//     */
//    public static int setPopWindowHeight(Activity activity) {
//        int result = 0;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////            if(MIUISetStatusBarLightMode(activity.getWindow(), true)){
////                result = StaticConstant.sHeight- StatusBarUtil.getStatusHeight(activity);
////            }else if(FlymeSetStatusBarLightMode(activity.getWindow(), true)){
////                result = StaticConstant.sHeight- StatusBarUtil.getStatusHeight(activity);
////            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
////                result = StaticConstant.sHeight- StatusBarUtil.getStatusHeight(activity);
////            }
//            result = StaticConstant.sHeight;
//        } else {
//            result = StaticConstant.sHeight;
//        }
//        return result;
//    }

    /**
     * EditText动态长度限制
     */
//    public static void setEditInputLength(EditText editText) {
//        //设置充值限制最大金额为9999，可输入小数
//        StringUtil.setPricePoint(editText);
//        if (!StringUtil.isEmpty(editText.getText().toString())) {
//            double m = Double.parseDouble(editText.getText().toString());
//            if (editText.getText().toString().contains(".")) {
//                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
//                        7)});
//            } else {
//                if (m < 9999) {
//                    editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
//                            5)});
//                } else {
//                    editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
//                            4)});
//                    if (m > 9999) {
//                        editText.setText(editText.getText().toString().substring(0, 4));
//                        editText.setSelection(editText.getText().length());
//                    }
//                }
//            }
//        }
//    }

    /**动态设置listview高度*/
    public static void setListViewHeight(ListView listView,int maxHeight)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
        {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int total = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height = total>maxHeight?maxHeight:total;
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        listView.setLayoutParams(params);
    }

}
