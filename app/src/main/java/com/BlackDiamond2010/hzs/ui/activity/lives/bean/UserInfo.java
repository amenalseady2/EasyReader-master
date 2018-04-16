package com.BlackDiamond2010.hzs.ui.activity.lives.bean;/**
 * Created by yq on 2016/7/26.
 */

/**
 * 项目名称：Aicaomei_user
 * 类描述：登录，注册，找回密码返回实体
 * 创建人：yq
 * 创建时间：2016/7/26 14:20
 * 修改人：yq
 * 修改时间：2016/7/26 14:20
 * 修改备注：
 */
public class UserInfo extends BaseBean {

    private static final long serialVersionUID = 3195007158557653610L;
    public String  avatar;//头像地址
    public String  nickname;//邀请码
    public String  phone;//昵称

    public String  realname;//身份类型
    public String  company;//身份说明
    public String  job;//是否已设置登录密码（1:已设置;0:未设置）
    public String   is_vip; //是否 是vip
}
