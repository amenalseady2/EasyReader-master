package com.BlackDiamond2010.hzs.ui.activity.lives.config;/**
 * Created by yq on 2016/8/8.
 */


/**
 * 项目名称：Aicaomei_MVVMFrame_Retrofit
 * 类描述：一些基础配置信息
 * 创建人：yq
 * 创建时间：2016/8/8 11:31
 * 修改人：yq
 * 修改时间：2016/8/8 11:31
 * 修改备注：
 */
public class Config {
    public static final boolean ISDEBUG = true;//
    public static final String BASE_URL = "http://api.kanjian2020.com/api/";//测试服务器根地址
//    public static final String BASE_URL = "http://member.icaomei.com";
    //appid
    //请同时修改  androidmanifest.xml里面，.PayActivityd里的属性<data android:scheme="wxb4ba3c02aa476ea1"/>为新设置的appid
    public static final String APP_ID = "wx7d0357199177d6fe";

    /**
     * 列表类型枚举
     */
    public static class ListType {
        public static final String MessageList = "MessageList";//消息列表
        public static final String SYS_MESSAGE = "SYS_MESSAGE";//系统消息列表
        public static final String REBATE_ARRIVAL = "REBATE_ARRIVAL";//返利消息列表
        public static final String SUBSCRIBE_TRADE = "SUBSCRIBE_TRADE";//预订消息列表
        public static final String TRADE_ARRIVAL = "TRADE_ARRIVAL";//订单消息列表
        public static final String INTEGRAL_TRADE = "INTEGRAL_TRADE";//U米订单消息列表
        public static final String CAOMEI_NOTICE = "CAOMEI_NOTICE";//草媒消息列表
        public static final String DYNAMICS_NOTICE = "DYNAMICS_NOTICE";//动态消息列表

        public static final String BillList = "BillList";//收支明细列表
        public static final String RechargeList = "RechargeList";//充值列表
        public static final String WithdrawList = "WithdrawList";//提现列表
        public static final String ProfitList = "ProfitList";//返利列表
        public static final String SubsidyList = "SubsidyList";//平台补贴列表
    }


    public static class Key {
        /**
         * 设备唯一ID
         */
        public static final String DEVICE_ID = "DEVICE_ID";
        /**
         * 异常重启次数记录
         */
        public static final String RESTART_TIME = "RESTART_TIME";
        /**
         * 登录成功后的token
         */
        public static final String TICKET = "TICKET";
        /**
         * 发现模块登录成功后的token
         */
        public static final String TOKEN = "TOKEN";
        /**
         * 发现模块登录成功后的usermdf
         */
        public static final String USERMDF = "USERMDF";
        /**
         * 爱草媒登录成功后的usermdf
         */
        public static final String USERMDF_ICAOMEI = "USERMDF_ICAOMEI";
        /**
         * 是否登录
         */
        public static final String LOGINED = "LOGINED";
        /**
         * 用户名
         */
        public static final String USERNAME = "USERNAME";
        /**
         * 快捷登录用户名
         */
        public static final String USERNAME_FAST = "USERNAME_FAST";
        /**
         * 用户密码
         */
        public static final String USERPWD = "USERPWD";
        /**
         * 是否已经启动过引导页
         */
        public static final String BOOTED = "BOOTED";
        /**
         * 城市
         */
        public static final String CITY = "CITY";
        /**
         * 城市id
         */
        public static final String CITY_ID = "CITY_ID";
        /**
         * 城市code
         */
        public static final String CITY_CODE = "CITY_CODE";
        /**
         * 区域
         */
        public static final String DISTRICT = "DISTRICT";
        /**
         * 区域id
         */
        public static final String DISTRICT_ID = "DISTRICT_ID";
        /**
         * 区域code
         */
        public static final String DISTRICT_CODE = "DISTRICT_CODE";
        /**
         * 经度
         */
        public static final String LATITUDE = "LATITUDE";
        /**
         * 纬度
         */
        public static final String LONGITUDE = "LONGITUDE";

        /**
         * 启动图片地址
         */
        public static final String START_IMG = "START_IMG";

        /**
         * 第一次进入首页
         */
        public static final String FIRST_IN_HOME = "FIRST_IN_HOME";
        /**
         * 第一次进入闪付
         */
        public static final String FIRST_IN_PAY = "FIRST_IN_PAY";
        /**
         * 第一次进入活动详情
         */
        public static final String FIRST_IN_ACTIVITY_DETAIL = "FIRST_IN_ACTIVITY_DETAIL";
        /**
         * 第一次进入有优惠充值的充值界面
         */
        public static final String FIRST_IN_TECHARGE = "FIRST_IN_TECHARGE";
    }
}
