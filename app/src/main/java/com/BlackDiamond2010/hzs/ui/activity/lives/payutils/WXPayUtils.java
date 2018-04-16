/**
 *
 */
package com.BlackDiamond2010.hzs.ui.activity.lives.payutils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.BlackDiamond2010.hzs.ui.activity.lives.bean.WXPreOrderBean;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.BaseConfig;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.show.T;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;
import java.util.Map;

/**
 * @author yeqing
 * @date 2015-11-18 下午1:37:01
 */
public class WXPayUtils {
	//微信支付
	PayReq req;
	final IWXAPI msgApi ;
	Map<String,String> resultunifiedorder;
	StringBuffer sb;
	Context context;
	public WXPayUtils(Context context, WXPreOrderBean preOrderBean){
		this.context=context;
		req = new PayReq();
		sb=new StringBuffer();
		msgApi = WXAPIFactory.createWXAPI(context, null);
		msgApi.registerApp(BaseConfig.APP_ID);
		if(isWXAppInstalledAndSupported(context, msgApi)){
			//调用微信支付
			genPayReq(preOrderBean);
			sendPayReq();
		}else{
			T.showShort(context,"微信客户端未安装，请确认");
		}
		
	}
	/**
	 * 是否安装微信
	 *
	 * @param @param  context
	 * @param @param  api
	 * @param @return
	 * @return boolean
	 * @author yeqing
	 * @date 2015-12-16 上午11:26:14
	 */
	public static boolean isWXAppInstalledAndSupported(Context context,
													   IWXAPI api) {
		// LogOutput.d(TAG, "isWXAppInstalledAndSupported");
		boolean sIsWXAppInstalledAndSupported = api.isWXAppInstalled();
//				&& api.isWXAppSupportAPI();
		if (!sIsWXAppInstalledAndSupported) {
			return false;
		}

		return sIsWXAppInstalledAndSupported;
	}

	public static boolean isWeixinAvilible(Context context) {
		final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				if (pn.equals("com.tencent.mm")) {
					return true;
				}
			}
		}

		return false;
	}

	private void genPayReq(WXPreOrderBean preOrderBean) {
		req.appId=preOrderBean.getAppid();
		req.nonceStr=preOrderBean.getNoncestr();
		req.packageValue="Sign=WXPay";
		req.partnerId=preOrderBean.getPartnerid();
		req.prepayId=preOrderBean.getPrepayid();
		req.timeStamp=preOrderBean.getTimestamp();
		req.sign = preOrderBean.getSign();
	}

	private void sendPayReq() {
		msgApi.registerApp(BaseConfig.APP_ID);
		msgApi.sendReq(req);
	}

	

}
