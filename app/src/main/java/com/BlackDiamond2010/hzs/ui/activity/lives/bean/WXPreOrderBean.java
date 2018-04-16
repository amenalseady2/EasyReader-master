package com.BlackDiamond2010.hzs.ui.activity.lives.bean;


public class WXPreOrderBean extends BaseBean {

	/**
	 * 微信预支付订单体
	 */
	private static final long serialVersionUID = 1L;

	private String appid;
	private String noncestr;
	private String mpackage;
	private String partnerid;
	private String prepayid;
	private String sign;
	private String timestamp;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getMpackage() {
		return mpackage;
	}

	public void setMpackage(String mpackage) {
		this.mpackage = mpackage;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getPrepayid() {
		return prepayid;
	}

	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
