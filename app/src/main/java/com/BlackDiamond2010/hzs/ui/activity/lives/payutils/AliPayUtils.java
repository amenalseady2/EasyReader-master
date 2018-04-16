package com.BlackDiamond2010.hzs.ui.activity.lives.payutils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.BlackDiamond2010.hzs.ui.activity.lives.listener.ReflushListener;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.show.L;
import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * 支付宝付款工具类
 * @author yeqing
 * @date 2015-9-19 下午2:19:32
 */
public class AliPayUtils {
	private Activity mContext;
	private final int SDK_PAY_FLAG = 1;
	private final int SDK_CHECK_FLAG = 2;
	private ReflushListener frushinterface;
	public AliPayUtils(Activity context) {
		this.mContext = context;
	}
	public AliPayUtils(Activity context, ReflushListener frushinterface) {
		this.mContext = context;
		this.frushinterface=frushinterface;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();
				//支付结果错误码：
			    /*9000:订单支付成功
			     *8000:正在处理中（"支付结果确认中"）  代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
			     *4000:订单支付失败
			     *6001:用户中途取消
			     *6002:网络连接出错  */
				String resultStatus = payResult.getResultStatus();
                L.i("resultStatus-------"+resultStatus);
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
					if(frushinterface!=null){
						frushinterface.reflush("0", "success");
					}
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT)
								.show();

					}else if(TextUtils.equals(resultStatus, "4000")){
						Toast.makeText(mContext, "支付失败，未安装支付宝客户端", Toast.LENGTH_SHORT)
						.show();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT)
								.show();

					}
					if(frushinterface!=null){
						frushinterface.reflush("1", "failed");
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(mContext, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT)
						.show();
				break;
			}
			default:
				break;
			}
		};
	};

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(final String payInfo) {

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(mContext);
				getSDKVersion();
				// 调用支付接口，获取支付结果
				Map<String, String> result = alipay.payV2(payInfo,true);
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
//		Runnable checkRunnable = new Runnable() {
//
//			@Override
//			public void run() {
//				// 构造PayTask 对象
//				PayTask payTask = new PayTask(mContext);
//				// 调用查询接口，获取查询结果
//				boolean isExist = payTask.checkAccountIfExist();
//
//				Message msg = new Message();
//				msg.what = SDK_CHECK_FLAG;
//				msg.obj = isExist;
//				mHandler.sendMessage(msg);
//			}
//		};
//
//		Thread checkThread = new Thread(checkRunnable);
//		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(mContext);
		String version = payTask.getVersion();
	}

}
