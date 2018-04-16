package com.BlackDiamond2010.hzs.ui.activity.lives.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 缓存工具类
 *
 * @author Liuj
 */
public class SHPUtils {


    //--------------------------登录后保存用户的一些信息----------------------------------//
    public static final String AUDIT_STATUS = "auditStatus";//认证状态，0-未认证，1-已认证
    public static final String TOKEN = "token";

    public static final String ADDRESSID = "addressid";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String HEAD = "head";
    public static final String NICKNAME = "nickname";
    public static final String INVATATION = "invatation";


    public static final String CONPANY = "conpany";//gongs
    public static final String REAL_NAME = "rela_name";
    public static final String JOB = "job";

    public static final String ISVIP = "isvip";

    public static final String PERCENT = "percent";

    public static final String CLIENTID = "clientid";
    public static final String MESSAGE = "message";//订单消息
    public static final String ORDER_MESSAGE = "order_message";//预约直播信息

    public static final String NEWS_MESSAGE = "news_message";//新闻

    public static final String PASSWORD = "password";//记住密码 需要
    public static final String PHONE_LOGIN = "loginphone";
    public static final String SHAREURL = "shareurl";

    public static final String HOME_SEARCH = "home_search";
    public static final String TOP_NEWS_SEARCH = "topnewssearch";
    public static final String PROJECT_SEARCH = "project_search";
    public static final String GOODS_SEARCH = "goods_search";


    /**
     * 保存参数
     */
    public static void saveParame(Context context, String key, String value) {
        SharedPreferences.Editor sharedata = context.getSharedPreferences(
                "data", 0).edit();
        sharedata.putString(key, value);
        sharedata.commit();
    }

    /**
     * 获取参数
     */
    public static String getParame(Context context, String key) {
        SharedPreferences sharedata = context.getSharedPreferences("data", 0);
        return sharedata.getString(key, null);
    }

    /**
     * 清除对应key值的值
     */
    public static void removeKey(Context context, String key) {
        SharedPreferences.Editor sharedata = context.getSharedPreferences(
                "data", 0).edit();
        sharedata.remove(key);
        sharedata.commit();
    }


    public static long getLongParame(Context context, String key) {
        SharedPreferences sharedata = context.getSharedPreferences("data", 0);
        return sharedata.getLong(key, 0);
    }

    public static void saveLongParame(Context context, String key, Long value) {
        SharedPreferences.Editor sharedata = context.getSharedPreferences(
                "data", 0).edit();
        sharedata.putLong(key, value);
        sharedata.commit();
    }

}

