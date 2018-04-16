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
public class UserBean extends BaseBean {

    private static final long serialVersionUID = 3195007158557653610L;
    private String  headPicUrl;//头像地址
    private String  invitCode;//邀请码
    private String  nickName;//昵称

    private String  userType;//身份类型
    private String  userTypeDetail;//身份说明
    private int  isSetLoginPass;//是否已设置登录密码（1:已设置;0:未设置）
    private int  isSetPayPass;//是否已设置支付密码（1:已设置;0:未设置）
    private String  userId;//账号ID
    private String userName;//账号
    private String rongyunToken;

    public String getRongyunToken() {
        return rongyunToken;
    }

    public void setRongyunToken(String rongyunToken) {
        this.rongyunToken = rongyunToken;
    }

    public String getHeadPicUrl() {
        return headPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        this.headPicUrl = headPicUrl;
    }

    public String getInvitCode() {
        return invitCode;
    }

    public void setInvitCode(String invitCode) {
        this.invitCode = invitCode;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserTypeDetail() {
        return userTypeDetail;
    }

    public void setUserTypeDetail(String userTypeDetail) {
        this.userTypeDetail = userTypeDetail;
    }

    public int getIsSetLoginPass() {
        return isSetLoginPass;
    }

    public void setIsSetLoginPass(int isSetLoginPass) {
        this.isSetLoginPass = isSetLoginPass;
    }

    public int getIsSetPayPass() {
        return isSetPayPass;
    }

    public void setIsSetPayPass(int isSetPayPass) {
        this.isSetPayPass = isSetPayPass;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
