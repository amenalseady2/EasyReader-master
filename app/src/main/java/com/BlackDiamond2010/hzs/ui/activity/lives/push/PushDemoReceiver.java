package com.BlackDiamond2010.hzs.ui.activity.lives.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;

import org.json.JSONException;
import org.json.JSONObject;


public class PushDemoReceiver extends BroadcastReceiver {

	/**
	 * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView ==
	 * null)
	 */
	public static StringBuilder payloadData = new StringBuilder();

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		switch (bundle.getInt(PushConsts.CMD_ACTION)) {
			case PushConsts.GET_MSG_DATA:
				// 获取透传数据
				byte[] payload = bundle.getByteArray("payload");
				String taskid = bundle.getString("taskid");
				String messageid = bundle.getString("messageid");
				// smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
				boolean result = PushManager.getInstance().sendFeedbackMessage(
						context, taskid, messageid, 90001);
				Log.d("pushTag", "第三方回执接口调用" + (result ? "成功" : "失败"));

				if (payload != null) {// 点击后执行
					String data = new String(payload);
					try {
						JSONObject json = new JSONObject(data);
						int type = json.getInt("type");
						String id = json.getString("id");

						if (type == 1) {

						} else if (type == 2) {
							// Intent resultIntent = new Intent();
							// resultIntent.putExtra("message", type + "," + id);
							// resultIntent.setAction("push_message");
							// context.sendBroadcast(resultIntent);
							//
//						SHPUtils.saveParame(context, SHPUtils.PUSH_MESSAGE, id);
//							Intent pushIntent = new Intent(context,
//									ResidentDetailActivity.class);
//							pushIntent.putExtra("vid", id);
//							pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//							context.startActivity(pushIntent);
						} else if (type == 3) {

						} else {

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				break;
			case PushConsts.GET_CLIENTID:
				final String cid = bundle.getString("clientid");
				Log.i("pushTag", "clientId = " + cid);
				break;

			case PushConsts.THIRDPART_FEEDBACK:
				break;

			default:
				break;
		}

	}
}
