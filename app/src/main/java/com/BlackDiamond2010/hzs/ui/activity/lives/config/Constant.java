package com.BlackDiamond2010.hzs.ui.activity.lives.config;/**
 * Created by yq on 2016/7/26.
 */

import java.math.BigDecimal;

/**
 * 项目名称：Aicaomei_user
 * 类描述：常量
 * 创建人：yq
 * 创建时间：2016/7/26 15:54
 * 修改人：yq
 * 修改时间：2016/7/26 15:54
 * 修改备注：
 */
public class Constant {
    public static final String secretKey = "CLIENT-vewicm@#235g%(^$}";//服务器提供秘钥
    public static final String POST = "post";
    public static final String GET = "get";
    public static final String TICKET = "TICKET";
//    public static final String WX_APP_ID = "wx13a45d61897d9562";
    public static final String WX_APP_ID = "wxfca75014cf3f347e";

    public static String DISCOVERY_Broadcast = "DISCOVERY_REFRUSH";
    public static String Broadcast_GOOD = "Broadcast_GOOD";
    public static String Broadcast_BAD = "Broadcast_BAD";
    public static String Broadcast_reflush = "Broadcast_reflush";
    public static String Broadcast_discovery_moveTop = "Broadcast_discovery_moveTop";
    public static String Broadcast_discovery_loginStatus = "Broadcast_discovery_loginStatus";
    public static String Broadcast_send_discovery = "Broadcast_send_discovery";
    public static String Broadcast_failure_discovery = "Broadcast_failure_discovery";
    public static String Broadcast_delete_discovery = "Broadcast_delete_discovery";
    public static String MYBroadcast = "MY_DISCOVERY_REFRUSH";
    public static String MYBroadcast_delete_discovery = "MYBroadcast_delete_discovery";
    public static String MYBroadcast_discuss_success = "MYBroadcast_discuss_success";

    /**
     * 排序position
     */
    public static String NOTIFY_BROADCAST = "notify.broadcast";
    public static String NOTIFY_BROADCAST_INTEGRAL = "notify.broadcast.integral";
    public static int area_currentPosition;//首页当前选中地区position
    public static int CitycurrentPosition;//店铺列表中全城选择地区position
    public static int ClassifycurrentPosition;//店铺列表中主分类position
    public static int BranchcurrentPosition;//店铺列表中子分类position
    public static int SortcurrentPosition;//店铺列表中智能排序position
    public static int BillcurrentPosition;//收支明细排序position
    public static int SortShopPosition;//店铺连锁店列表中排序

    //#############相关数字限制#####################
    public static BigDecimal PAY_MAX_NUM = new BigDecimal("2000");//消费最大金额限制
    public static BigDecimal PAY_MIN_NUM = new BigDecimal("0.1");//消费最小金额限制
    //红包数量限制
    public static int RED_BAG_MAX_NUM = 100;
    public static int RED_BAG_MAX_MONEY = 200;
    //红包金额限制
    public static double RED_BAG_MIN_MONEY = 0.01;
    public static int RED_BAG_SUM_MAX_MONEY = 5000;
    //红包描述字数限制
    public static int RED_BAG_DES_MAX_WORDS = 20;
    //登录密码长度限制
    public static int LOGINPWD_MIN_WORDS = 8;
    public static int LOGINPWD_MAX_WORDS = 16;
    //昵称






}
