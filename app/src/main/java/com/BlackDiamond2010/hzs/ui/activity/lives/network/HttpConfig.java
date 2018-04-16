package com.BlackDiamond2010.hzs.ui.activity.lives.network;/**
 * Created by yq on 2016/8/6.
 */

/**
 * 项目名称：Aicaomei_MVVMFrame_Retrofit
 * 类描述：
 * 创建人：yq
 * 创建时间：2016/8/6 15:35
 * 修改人：yq
 * 修改时间：2016/8/6 15:35
 * 修改备注：
 */
public class HttpConfig {
    /*注册*/
    public final static String REGISTER = "account/register";
    /*注册获取验证码*/
    public final static String REGISTER_SMS = "sms";
    /*账户密码登录*/
    public final  static String FINDPASS = "account/forget";

    /*快捷登录登录*/
    public final static String LOGIN = "login/phone";
    public final static String WEIXIN_ISBIND = "login/wechat";

    public final  static String BIND = "account/bind";
    public final  static String PRODUCT_LIST = "product/list";
    //头条
    public final  static String TOPNEWS = "news/index";

    //项目
    public final  static String PROJECT = "project/index";
    //项目详情
    public final  static String PROJECT_DETAIL = "project/detail";
    //直播首页
    public final  static String LIVE = "live/list";
    //收藏
    public final static String MYCOLLECT = "mine_collection";

    public final static String GETTOKEN= "upload/token";

    //商城首页
    public final  static String SHOPHOEM = "shop/index";
    public final static String HOME= "index";
    public final static  String NEWSDETAIL = "news/detail";
    //商品详情
    public final static  String GOODSDETAIL = "product/detail";
    //添加地址
    public final static  String ADD_ADDRESS = "address/create";
    public final static  String MYADDRESS = "address/list";
    public final static  String ALTERADDRESS = "address/update";

    public final static  String DETAULTADDRESS = "address/set_default";

    public final static  String DELETEADDRESS = "address/delete";
    public final static  String AddressAndDeleteSHOPCART = "shopping_cart/action";

    public final static  String GOODS_NUM_CHANGE = "shopping_cart/change";
    public final static  String SHOPCART = "shopping_cart/list";
    public final static  String SUBMITORDER = "order/confirm";//预览订单

    public final static String COLLECT = "collection/create";//收藏

    public final static String MYORDERLIST = "mine_order/list";//我的订单
    public final static String SERVER_LIST = "mine_service/list";//我的订单


    public final static String ALTERPASSWORD ="member/modify_password";
    public final static String  FINDASSWORD ="account/modify_password";

    public final static  String VIP_BUY = "vip/create";//VIP创建订单
    public final static  String VIP_PAYMENT = "wechat/payment";
    public final static  String ALIPAY_PAYMENT = "alipay/payment";

    public final static  String USERINFO = "member/info";

    public final static String USERDETAILINFO = "member/detail";//用户详细信息
    public final static String GOODSEVALUATE = "product/evaluate_list";//商品评


    public final static String CHANGE = "change";//商品评价
    public final static String HOMESERACH = "search/index"; //首页搜索
    public final static String PROJECTSERACH = "search/project"; //XIANGMU
    public final static String NEWSERACH = "search/news"; //NEWS
    public final static String GOODSSERACH = "search/product"; //首页搜索
    public final static String ORDERSERACH = "search/order"; //首页搜索

    public final static String CREAT_ORDER = "order/create"; //创建订单

    public final static String ALTERUSERINFO = "member/modify_detail";//修改个人信息
    public final static String VIPRECORD = "mine_vip/record";

    public final static String LIVEPREVAE  ="live/prevue";//预告
    public final static String LIVEREVIEW  ="live/review";//回顾


    public final static  String ADDSUBSCRIBE = "publisher/subscribe";//添加订阅
    public final static String  LIVESIGNUP = "live/act_sign_up";//在线报名
    public final static String      MYSUBSCRIBE = "mine_other/subscribe";//我的订阅
    public final static String      SERACHSUBSCRIBE = "search/publisher";//我的订阅


    //订单相关
    public final static String  CLEAR_AND_REVICE = "order/handle";//取消订单和确认收货


    public final static String ORDER_DETAIL = "order/detail";

    public final static  String   ORDEREVALUATE= "order/evaluate";


    public final static String  EXPRESS="order/express";


    public final static String  SERVER_CREAT= "mine_service/create";//申请退款

    public final static String  SERVER_DATAIL= "mine_service/detail";//退款流程
    public final static String LIVEDetail = "live";
    public final static String REGISTER_CHAT = "chat/register";
    public final static String UNREGISTER_CHAT = "chat/unregister";
    public final static String CHAT_SEND = "chat/send";
    public final static String UPLOAD_CLIENTID = "init";

    public final static String VIPDETAIL = "mine_vip/info";
    public final static String DIANZAN = "news/like";


    public final static String  ACTION_DETAIL = "live/detail";//A活动详情

    public final static String  ADDAPPT = "live/appt";//添加预约

    public final static String  SHARE= "member/share"; //分享链接


}
