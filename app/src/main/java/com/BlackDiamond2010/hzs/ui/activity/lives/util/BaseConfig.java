package com.BlackDiamond2010.hzs.ui.activity.lives.util;/**
 * Created by yq on 2016/8/5.
 */

/**
 * 项目名称：Aicaomei_MVVMFrame_Retrofit
 * 类描述：
 * 创建人：yq
 * 创建时间：2016/8/5 15:43
 * 修改人：yq
 * 修改时间：2016/8/5 15:43
 * 修改备注：
 */
public class BaseConfig {
    public  static String SERVER="";//服务器地址
    public final static String SHOPAPP="SHOP";
    public final static String USERAPP="USER";
    /** AES加密seed */
    public final static String APPMDF = "01830b5ad38b11e4aefa902b349a62cf";
    //appid
    //请同时修改  androidmanifest.xml里面，.PayActivityd里的属性<data android:scheme="wxb4ba3c02aa476ea1"/>为新设置的appid
    public static final String APP_ID = "wx7d0357199177d6fe";
    public static String WXPay_Broadcast="WXPay_Broadcast";//微信充值广播
    /**
     * ViewModelNotify类型
     */
    public final static int CODE_FAILURE= 0;//失败
    public final static int CODE_SUCCESS= 1;//成功
    public final static int CODE_NET_ERROR =5;//没有网络
    public final static int CODE_DATA = 3;//接口返回数据
    public final static int CODE_HEADER = 4;//点击列表头部
    public final static int CODE_UNLOGIN =6;//没有登录
    public final static int CODE_SHARE =7;//分享

    public final static int CODE_PROFIT_SUCCESS =8;//我的返利成功
    public final static int CODE_DAY_PROFIT_SUCCESS =9;//按天返利成功

    public final static int CODE_DELETE_SUCCESS =10;//我的提现账户删除成功
    public final static int CODE_DELETE_FAILURE =11;//我的提现账户删除失败

    public final static int CODE_DONE = 12;//已加载全部数据

    public final static int CODE_BANANER_SUCCESS = 13;//Bananer条成功
    public final static int CODE_RECOMMEND_SUCCESS = 14;//获取推荐成功
    public final static int CODE_LISTSON_SUCCESS = 15;//获取分类成功
    public final static int CODE_DISCOUNT_SUCCESS = 16;//获取分类成功
    public final static int CODE_CITYSON_SUCCESS = 17;//获取分类成功

    public final static int CODE_CITYLIST_SUCCESS = 18;//获取城市列表成功

    public final static int CODE_HOTSEARCH_SUCCESS = 19;//获取搜索推荐成功

    public final static int CODE_HOT_FAILURE = 20;//失败
    public final static int CODE_HOT_SUCCESS = 21;//成功
    public final static int CODE_HOT_DONE = 22;//已加载全部数据

    public final static int CODE_SEARCH_TAG = 23;
    public final static int CODE_SEARCH_TAG_ERR = 29;
    public final static int CODE_HOT_SHOPS_SUCCESS = 24;
    public final static int CODE_HOT_SHOPS_ERR = 26;

    public final static int CODE_COLLECTION_SUCCESS = 25;
    public final static int CODE_DISCUSS_SUCCESS = 27;
    public final static int CODE_DISCUSS_ERROR = 28;

    public final static int CODE_INTEGRAL_STATUS_SUCCESS = 29;

    public  static String APPTYPE = USERAPP;//APP类型，SHOP商户版,USER用户版
    public static void initService(String service,String appType){
        SERVER = service;
        if(SHOPAPP.equals(appType)){
            APPTYPE = SHOPAPP;
        }
    }
    //服务器返回参数码
    public static class HttpStatusConstants {
        //系统返回参数码(-1, "重新登录"),(0, "错误提示内容"), (1, "操作成功");
        public static final int RESULT_OK = 200;
        public static final int RESULT_FAILURE = 400;
        public static final int RESULT_UNLOGIN = 405;//token 过期

//        200 请求接口成功【拉取数据成功、点赞成功、订阅成功等】
//                400 Tusi【可自定义错误提示信息】
//                402 Tusi【提示返回的错误信息】
//                403 Tusi【权限相关没有权限】
//                405 未登录【需要登录权限接口,而用户未登录】
//                422 Tusi网络错误【参数错误一般由于请求参数错误,其他情况,用于本地接口调试】
//                500 服务器内部错误【出现此错误,请及时联系后端】
    }

    //Intent Tag
    public static class IntentTag{
        public static final String DATA = "DATA";
        public static final String TYPE = "TYPE";
        public static final String ID = "ID";
        public static final String TAG = "TAG";
        public static final String TITLE = "TITLE";
        public static final String MSG = "MSG";
        public static final String PUSH_DATA = "PUSH_DATA";
        public static final String EXTRA = "EXTRA";
    }

    public static final String TICKET = "TICKET";// 登录成功后的token

    public static class IntentKey{
        public static final String IS_LOGIN_BACK = "IS_LOGIN_BACK";
        public static final String IS_FORRESULT = "IS_FORRESULT";
        public static final String NEW_SET = "NEW_SET";
        public static final String INDEX = "INDEX";
        public static final String DISTANCE = "DISTANCE";
        public static final String COUPONID = "COUPONID";
        public static final String PAYMONEY = "PAYMONEY";
        /** 经度 */
        public static final String LAT = "ExtraLAT";
        /** 维度 */
        public static final String LNG = "ExtraLNG";
        /** 数据 */
        public static final String DATA = "DATA";
        public static final String ALL = "ALL";
        public static final String LEVEL= "LEVEL";
        /** 传递的URL */
        public static final String WEB_URL = "ExtraWebUrl";
        /** 当前Activity的Title */
        public static final String TITLE = "ExtraTitle";
        public static final String CONTENT = "ExtraContent";
        /** 额外的 */
        public static final String EXTRA = "Extra";
        /** ID */
        public static final String ID = "ExtraID";
        /** 标志当前Activity是否需要顶部view默认false(包含) */
        public static final String HAS_TOP = "ExtraHasTop";
        /** 当前的Type */
        public static final String TYPE = "ExtraType";

        public static final String EXTRA_BUNDLE="EXTRA_BUNDLE";
    }
}
