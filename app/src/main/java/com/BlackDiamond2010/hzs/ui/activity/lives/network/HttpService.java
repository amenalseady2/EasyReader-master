package com.BlackDiamond2010.hzs.ui.activity.lives.network;

import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.ToolListModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.AddressModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodDetailModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsEva;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HomeModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LiveDetailModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LiveModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LoginModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LogisticsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyDingYueModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderDeatilModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderItem;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.NewsBean;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.NewsDetailModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ProjectDetailModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ProjectModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Risgter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ShopCartModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ShopModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.UserInfo;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.VIPModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.WXPreOrderBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yq on 16/8/1.
 */
public interface HttpService {

/**************************************************登录注册模块*************************************************/
    /**
     * 注册
     *
     * @param device   邀请码 邀请者的集结号、手机号 可以为空
     * @param passWord 32位md5加密密码
     * @param nickname 6位数字短信验证码
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.REGISTER)
    Observable<HttpResult<Risgter>> Register(@Field("device") String device,
                                             @Field("nickname") String nickname,
                                             @Field("phone") String phoneNum,
                                             @Field("code") String code,
                                             @Field("password") String passWord);

    /**
     * 注册
     *
     * @param device
     * @param type   6位数字短信验证码
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.REGISTER_SMS)
    Observable<HttpResult> RegisterMsm(@Field("device") String device,
                                       @Field("phone") String phoneNum,
                                       @Field("type") String type);

    //找回密码
    @FormUrlEncoded
    @POST(HttpConfig.FINDPASS)
    Observable<HttpResult<Risgter>> findpass(@Field("device") String device,
                                             @Field("phone") String phoneNum,
                                             @Field("code") String code);

    /**
     * 快速登录
     *
     * @param username 账号
     * @param password 验证码
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.LOGIN)
    Observable<HttpResult<Object>> login(@Field("device") String device,
                                         @Field("username") String username,
                                         @Field("password") String password);

    /**
     * 快速登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.WEIXIN_ISBIND)
    Observable<HttpResult<LoginModel>> wxIsBind(@Field("device") String device,
                                                @Field("openid") String openid,
                                                @Field("nickname") String nickname,
                                                @Field("avatar") String avatar);


    /**
     * bind
     *
     * @param password 验证码
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.BIND)
    Observable<HttpResult<Object>> bind(@Field("token") String device,
                                        @Field("device") String openid,
                                        @Field("phone") String nickname,
                                        @Field("code") String code,
                                        @Field("password") String password);

    /**
     * 路演工具
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.PRODUCT_LIST)
    Observable<HttpResult<ToolListModel>> toolsList(@Field("token") String token,
                                                    @Field("device") String openid,
                                                    @Field("page") int page,
                                                    @Field("category_id") String category_id,
                                                    @Field("type") int type);

    /**
     * 头条
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.TOPNEWS)
    Observable<HttpResult<NewsBean>> topNewsList(@Field("token") String token,
                                                 @Field("device") String device,
                                                 @Field("page") int page,
                                                 @Field("type") int type);

    /**
     * 头条
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.PROJECT)
    Observable<HttpResult<ProjectModel>> projectList(
            @Field("token") String token,
            @Field("device") String device,
            @Field("page") int page,
            @Field("category_id") int category_id,
            @Field("type") int type);

    /**
     * 项目详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.PROJECT_DETAIL)
    Observable<HttpResult<ProjectDetailModel>> projectDetail(
            @Field("device") String device,
            @Field("id") String id);

    /**
     * 直播首页
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.LIVE)
    Observable<HttpResult<LiveModel>> liveList(
            @Field("device") String device,
            @Field("page") int page,
            @Field("type") int id);

    @FormUrlEncoded
    @POST(HttpConfig.GOODSDETAIL)
    Observable<HttpResult<GoodDetailModel>> goodsDetail(
            @Field("device") String device,
            @Field("device_type") int device_type,
            @Field("product_id") String id);

    /**
     * 商城首页
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.SHOPHOEM)
    Observable<HttpResult<ShopModel>> shopHome(
            @Field("device") String device);

    /**
     * 首页
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.HOME)
    Observable<HttpResult<HomeModel>> home(
            @Field("device_type") int device_type,
            @Field("client_id") String client_id,
            @Field("device") String device);

    /**
     * 新闻详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.NEWSDETAIL)
    Observable<HttpResult<NewsDetailModel>> newsDetail(
            @Field("id") String id,
            @Field("device") String device);

    /**
     * 添加地址
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.ADD_ADDRESS)
    Observable<HttpResult<Object>> addAddress(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("info") String info,
            @Field("type") int type,
            @Field("device") String device);


    @FormUrlEncoded
    @POST(HttpConfig.ALTERADDRESS)
    Observable<HttpResult<Object>> alterAddress(
            @Field("id") String id,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("info") String info,
            @Field("type") int type,
            @Field("device") String device);

    /**
     * 我的地址
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.MYADDRESS)
    Observable<HttpResult<List<AddressModel>>> myAddress(
            @Field("device") String device);


    /**
     * 我的地址
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.DETAULTADDRESS)
    Observable<HttpResult<Object>> dAddress(
            @Field("id") String id,
            @Field("device") String device);

    /**
     * 我的地址
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.DELETEADDRESS)
    Observable<HttpResult<Object>> deleteAddress(
            @Field("id") String id,
            @Field("device") String device);

    /**
     * type 1加入商品至购物车 2从购物车删除商品
     * product_id type 为1时 使用此字段
     * 购物车商品唯一标识	car_id	integer	否	type为2时 使用此字段 删除多个 逗号分隔 不能为空
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.AddressAndDeleteSHOPCART)
    Observable<HttpResult<Object>> addordeleteShopcart(
            @Field("type") int type,
            @Field("product_id") String product_id,
            @Field("car_id") String car_id,
            @Field("device") String device);


    @FormUrlEncoded
    @POST(HttpConfig.GOODS_NUM_CHANGE)
    Observable<HttpResult<Object>> numChange(
            @Field("num") int num,
            @Field("car_id") String car_id,
            @Field("device") String device);

    @FormUrlEncoded
    @POST(HttpConfig.SHOPCART)
    Observable<HttpResult<ShopCartModel>> Shopcart(
            @Field("page") int page,

            @Field("device") String device);


    @FormUrlEncoded
    @POST(HttpConfig.SUBMITORDER)
    Observable<HttpResult<MyOrderItem>> submitOrder(
            @Field("address_id") String address_id,
            @Field("product_info") String product_info,
            @Field("cart_list") String cart_list,
            @Field("device") String device);

    //	type	integer	是	1直播 2项目 3文章 4商品
    @FormUrlEncoded
    @POST(HttpConfig.COLLECT)
    Observable<HttpResult<Object>> addCollect(
            @Field("type") int type,
            @Field("id") String id,

            @Field("device") String device);


    /**
     * 直播首页type 1直播
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.MYCOLLECT)
    Observable<HttpResult<LiveModel>> myCollect(
            @Field("device") String device,
            @Field("page") int page,
            @Field("type") int id);

    /**
     * 直播首页type 2项目 11111111111111
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.MYCOLLECT)
    Observable<HttpResult<NewsBean>> myCollect3(
            @Field("device") String device,
            @Field("page") int page,
            @Field("type") int id);

    /**
     * 直播首页type 2项目 11111111111111
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.MYCOLLECT)
    Observable<HttpResult<Object>> myCollect2(
            @Field("device") String device,
            @Field("page") int page,
            @Field("type") int id);

    @FormUrlEncoded
    @POST(HttpConfig.GETTOKEN)
    Observable<HttpResult<Risgter>> getToken(
            @Field("device") String device);

    /**
     * 直播首页type 2项目 11111111111111
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.MYCOLLECT)
    Observable<HttpResult<ToolListModel>> myCollect4(
            @Field("device") String device,
            @Field("page") int page,
            @Field("type") int id);


    /**
     * 我的订单
     * status	integer	是	100全部 0待付款 1待发货 2待收货 3已完成
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.MYORDERLIST)
    Observable<HttpResult<MyOrderModel>> myOrderList(
            @Field("device") String device,
            @Field("page") int page,
            @Field("status") int status);

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.ALTERPASSWORD)
    Observable<HttpResult<Object>> alterPassword(
            @Field("device") String device,
            @Field("password") String password,
            @Field("new_pass") String new_pass);

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.FINDASSWORD)
    Observable<HttpResult<Object>> findPassword(
            @Field("device") String device,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("check_code") String check_code);


    /**
     * 微信 购买VIP创建订单
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.VIP_BUY)
    Observable<HttpResult<Risgter>> vipcreat(
            @Field("device") String device,
            @Field("type") int type);


    /**
     *
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.VIP_PAYMENT)
    Observable<HttpResult<WXPreOrderBean>> wxpaymentvip(
            @Field("device") String device,
            @Field("order_sn") String order_sn,
            @Field("type") int type);

    /**
     *
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.ALIPAY_PAYMENT)
    Observable<HttpResult<Risgter>> alipaymentvip(
            @Field("device") String device,
            @Field("device_type") int devicetype,
            @Field("order_sn") String order_sn,
            @Field("type") int type);

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.ALIPAY_PAYMENT)
    Observable<HttpResult<Risgter>> alipayaymentvip(
            @Field("device") String device,
            @Field("device_type") int devicetype,
            @Field("order_sn") String order_sn,
            @Field("type") int type);

    /**
     * 用户信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.USERINFO)
    Observable<HttpResult<Risgter>> getUserInfo(
            @Field("device") String device
    );

    /**
     * 用户详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.USERDETAILINFO)
    Observable<HttpResult<UserInfo>> getUserdetailInfo(
            @Field("device") String device
    );


    @FormUrlEncoded
    @POST(HttpConfig.GOODSEVALUATE)
    Observable<HttpResult<GoodsEva>> goodspingjia(
            @Field("device") String device,
            @Field("product_id") String product_id
    );

    @FormUrlEncoded
    @POST(HttpConfig.CHANGE)
    Observable<HttpResult<List<GoodsModel>>> change(
            @Field("device") String device
    );

    /*
    * 订单搜索
    * */
    @FormUrlEncoded
    @POST(HttpConfig.ORDERSERACH)
    Observable<HttpResult<MyOrderModel>> orderSearch(
            @Field("device") String device,
            @Field("keyword") String keyword,
            @Field("page") int page
    );

    /*
* 首页直播搜索
* */
    @FormUrlEncoded
    @POST(HttpConfig.HOMESERACH)
    Observable<HttpResult<LiveModel>> homeSearch(
            @Field("device") String device,
            @Field("keyword") String keyword,
            @Field("page") int page

    );

    /*
* 商品收藏
* */
    @FormUrlEncoded
    @POST(HttpConfig.GOODSSERACH)
    Observable<HttpResult<ToolListModel>> goodsSearch(
            @Field("device") String device,
            @Field("keyword") String keyword,
            @Field("page") int page

    );

    /*
* 头条搜索
* */
    @FormUrlEncoded
    @POST(HttpConfig.NEWSERACH)
    Observable<HttpResult<NewsBean>> newsSearch(
            @Field("device") String device,
            @Field("keyword") String keyword,
            @Field("page") int page

    );

    /*
* 头条搜索
* */
    @FormUrlEncoded
    @POST(HttpConfig.PROJECTSERACH)
    Observable<HttpResult<ProjectModel>> projectSearch(
            @Field("device") String device,
            @Field("keyword") String keyword,
            @Field("page") int page

    );

    /*
* 头条搜索
* */
    @FormUrlEncoded
    @POST(HttpConfig.CREAT_ORDER)
    Observable<HttpResult<Risgter>> creatorder(

            @Field("pay_type") int pay_type,
            @Field("remark") String remark,

            @Field("address_id") String address_id,
            @Field("product_info") String product_info,
            @Field("cart_list") String cart_list,
            @Field("device") String device);



    /*
* 修改用户信息
* */
    @FormUrlEncoded
    @POST(HttpConfig.ALTERUSERINFO)
    Observable<HttpResult<Object>> alterUserInfo(
            @Field("job") String job,
            @Field("avatar") String avatar,
            @Field("nickname") String nickname,

            @Field("mobile") String mobile,
            @Field("realname") String realname,
            @Field("company") String company,
            @Field("device") String device);


    /*
* vip购买记录
* */
    @FormUrlEncoded
    @POST(HttpConfig.VIPRECORD)
    Observable<HttpResult<VIPModel>> viprecord(

            @Field("page") int page,
            @Field("device") String device);


    /*
 * 预告
 * */
    @FormUrlEncoded
    @POST(HttpConfig.LIVEPREVAE)
    Observable<HttpResult<LiveDetailModel>> livePrevae(

            @Field("id") String id,
            @Field("device") String device);

    /*
* 回归
* */
    @FormUrlEncoded
    @POST(HttpConfig.LIVEREVIEW)
    Observable<HttpResult<LiveDetailModel>> livereview(

            @Field("id") String id,
            @Field("device") String device);
    /*
* 回归
* */
    @FormUrlEncoded
    @POST(HttpConfig.LIVEDetail)
    Observable<HttpResult<LiveDetailModel>> live(

            @Field("id") String id,
            @Field("device") String device);




        /*
 * 添加订阅
 * */
    @FormUrlEncoded
    @POST(HttpConfig.ADDSUBSCRIBE)
    Observable<HttpResult<Object>> addSubscribe(

            @Field("id") String id,
            @Field("device") String device);

    /*
* 报名
* */
    @FormUrlEncoded
    @POST(HttpConfig.LIVESIGNUP)
    Observable<HttpResult<Object>> signup(

            @Field("job") String job,
            @Field("id") String id,
            @Field("nickname") String nickname,

            @Field("mobile") String mobile,
            @Field("realname") String realname,
            @Field("company") String company,
            @Field("device") String device);


   /*
* 我的订阅
* */
    @FormUrlEncoded
    @POST(HttpConfig.MYSUBSCRIBE)
    Observable<HttpResult<MyDingYueModel>> mySubscribe(
            @Field("page") int page,
            @Field("device") String device);

    /*
* 我的订阅
* */
    @FormUrlEncoded
    @POST(HttpConfig.SERACHSUBSCRIBE)
    Observable<HttpResult<LiveModel>> pubshSubscribe(
            @Field("publisher_id") String publisher_id,
            @Field("keyword") String keyword,
            @Field("page") int page,
            @Field("device") String device);

    /*
* 我的订阅
* */
    @FormUrlEncoded
    @POST(HttpConfig.CLEAR_AND_REVICE)
    Observable<HttpResult<Object>> cancleOrder(
            @Field("id") String id,
            @Field("type") String keyword,
            @Field("device") String device);

    /*
* 我的订阅
* */
    @FormUrlEncoded
    @POST(HttpConfig.SERVER_LIST)
    Observable<HttpResult<MyOrderModel>> serverList(
            @Field("page") int id,
            @Field("device") String device);



    /*
 * */
    @FormUrlEncoded
    @POST(HttpConfig.ORDER_DETAIL)
    Observable<HttpResult<MyOrderDeatilModel>> orderDetail(
            @Field("order_sn") String id,
            @Field("device") String device);

  /*
 * */
    @FormUrlEncoded
    @POST(HttpConfig.ORDEREVALUATE)
    Observable<HttpResult<Object>> orderEva(
            @Field("id") String id,
            @Field("device") String device,
            @Field("star") String star,
            @Field("content") String content,
            @Field("image") String image);


     /*
 * */
    @FormUrlEncoded
    @POST(HttpConfig.EXPRESS)
    Observable<HttpResult<LogisticsModel>> express(
            @Field("id") String id,
            @Field("device") String device);
    /*
* */
    @FormUrlEncoded
    @POST(HttpConfig.SERVER_DATAIL)
    Observable<HttpResult<LogisticsModel>> serverDetail(
            @Field("id") String id,
            @Field("device") String device);


    /*
* */
    @FormUrlEncoded
    @POST(HttpConfig.SERVER_CREAT)
    Observable<HttpResult<Object>> servercreat(
            @Field("id") String id,
            @Field("device") String device,
            @Field("type") String type,
            @Field("remark") String remark,
            @Field("image") String image);





    @FormUrlEncoded
    @POST(HttpConfig.REGISTER_CHAT)
    Observable<HttpResult<Object>> regist_chat(
                    @Field("id") String id,
                    @Field("device") String device,
                    @Field("client_id") String client_id
            );

    @FormUrlEncoded
    @POST(HttpConfig.UNREGISTER_CHAT)
    Observable<HttpResult<Object>> unregist_chat(
            @Field("id") String id,
            @Field("device") String device,
            @Field("client_id") String client_id
    );


    @FormUrlEncoded
    @POST(HttpConfig.CHAT_SEND)
    Observable<HttpResult<Object>> send_chat(
            @Field("content") String content,
            @Field("id") String id,
            @Field("device") String device,
            @Field("client_id") String client_id
    );


    @FormUrlEncoded
    @POST(HttpConfig.UPLOAD_CLIENTID)
    Observable<HttpResult<Object>> uploadClientId(
            @Field("device_type") int id,
            @Field("device") String device,
            @Field("client_id") String client_id
    );

    @FormUrlEncoded
    @POST(HttpConfig.VIPDETAIL)
    Observable<HttpResult<Risgter>> vipDetail(
            @Field("device") String device
    );


    @FormUrlEncoded
    @POST(HttpConfig.DIANZAN)
    Observable<HttpResult<Object>> dianzan(
            @Field("device") String device,
            @Field("id") String id
    );


    @FormUrlEncoded
    @POST(HttpConfig.ACTION_DETAIL)
    Observable<HttpResult<LiveDetailModel>> actionDetail(

            @Field("id") String id,
             @Field("device") String device
    );

    @FormUrlEncoded
    @POST(HttpConfig.ADDAPPT)
    Observable<HttpResult<LiveDetailModel>> addyuyue(

            @Field("id") String id,
            @Field("device") String device
    );

    @FormUrlEncoded
    @POST(HttpConfig.SHARE)
    Observable<HttpResult<Risgter>> share(
            @Field("device") String device

    );
}