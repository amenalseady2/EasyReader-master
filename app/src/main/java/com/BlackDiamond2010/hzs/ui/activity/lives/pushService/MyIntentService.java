package com.BlackDiamond2010.hzs.ui.activity.lives.pushService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LiveDetailMessage;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ASUS on 2017/11/13.
 */

public class MyIntentService extends GTIntentService {

    public MyIntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + msg.toString());
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();

        // 第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
        Log.d(TAG, "call sendFeedbackMessage = " + (result ? "success" : "failed"));

        Log.d(TAG, "onReceiveMessageData -> " + "appid = " + appid + "\ntaskid = " + taskid + "\nmessageid = " + messageid + "\npkg = " + pkg
                + "\ncid = " + cid);

        if (payload == null) {
            Log.e(TAG, "receiver payload = null");
        } else {
            String data = new String(payload);
            Log.d(TAG, "receiver payload = " + data);

            try {
                JSONObject json = new JSONObject(data);

                JSONObject dataObj = json.optJSONObject("data");
                LiveDetailMessage mdoel = new LiveDetailMessage();
                mdoel.avatar = dataObj.optString("avatar");
                mdoel.nickname = dataObj.optString("nickname");
                mdoel.content = dataObj.optString("content");
                mdoel.time = dataObj.optString("time") + "";

                if (json.optInt("type") == 1) {

                    Intent intent = new Intent();
                    intent.setAction("chat");
                    intent.putExtra("data", data);
                    sendBroadcast(intent);

                } else if(json.optInt("type") == 2){
                    String message = SHPUtils.getParame(context, SHPUtils.MESSAGE);
                    if (data.contains("\n")){
                        data.replaceAll("\n","");
                    }
                   if(StringUtil.isEmpty(message)){
                       SHPUtils.saveParame(context, SHPUtils.MESSAGE, data);
                   }else {
                       String newsMessage= data+"&"+message;
                       SHPUtils.saveParame(context, SHPUtils.MESSAGE, newsMessage);
                   }

                    showNotif(context,"你有一条订单消息，敬请查收");
                   //---------------下面的代码没机吧用----------------
                    //用AlarmManager定时发送广播
//                    AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//                    Intent intent = new Intent(this, ShowNotificationReceiver.class);
//                    PendingIntent pendingIntent =
//                            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                    am.set(AlarmManager.ELAPSED_REALTIME, SystemClock.currentThreadTimeMillis(), pendingIntent);
//                    sendBroadcast(intent);
                }else if(json.optInt("type") == 3){


                    String message = SHPUtils.getParame(context, SHPUtils.ORDER_MESSAGE);
                    if (data.contains("\n")){
                        data.replaceAll("\n","");
                    }
                    if(StringUtil.isEmpty(message)){
                        SHPUtils.saveParame(context, SHPUtils.ORDER_MESSAGE, data);
                    }else {
                        String newsMessage= data+"&"+message;
                        SHPUtils.saveParame(context, SHPUtils.ORDER_MESSAGE, newsMessage);
                    }
                    showNotif(context,"你预约的直播即将开始，敬请关注");
                }else if(json.optInt("type") == 4) {


                    String message = SHPUtils.getParame(context, SHPUtils.NEWS_MESSAGE);
                    if (data.contains("\n")) {
                        data.replaceAll("\n", "");
                    }
                    if (StringUtil.isEmpty(message)) {
                        SHPUtils.saveParame(context, SHPUtils.NEWS_MESSAGE, data);
                    } else {
                        String newsMessage = data + "&" + message;
                        SHPUtils.saveParame(context, SHPUtils.NEWS_MESSAGE, newsMessage);
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            // 测试消息为了观察数据变化
//            if (data.equals(getResources().getString(R.string.push_transmission_data))) {
//                data = data + "-" + cnt;
//                cnt++;
//            }
//            sendMessage(data, 0);
        }
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
        SHPUtils.saveParame(context, SHPUtils.CLIENTID, clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + cmdMessage.toString());
    }


    public void showNotif(Context context,String content){
        // 获取状态栏通知 管理
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(context.NOTIFICATION_SERVICE);
        // 实例化通知栏构造器NotificationCompat.Builder：
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context);
        // 对Builder进行配置
        mBuilder.setContentTitle("你有新的消息")
                // 设置通知栏标题
                .setContentText(content)
                .setContentIntent(
                        getDefalutIntent(context,Notification.FLAG_AUTO_CANCEL)) // 设置通知栏点击意图
                // .setNumber(number) //设置通知集合的数量
                .setTicker("测试通知来啦") // 通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) // 设置该通知优先级
                // .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                // Notification.DEFAULT_ALL
                // Notification.DEFAULT_SOUND 添加声音 // requires
                // VIBRATE permission
                .setSmallIcon(R.drawable.luyan_logo);// 设置通知小ICON

        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        // /第三步：启动通知栏，第一个参数是一个通知的唯一标识
        mNotificationManager.notify(2, notification);
    }

    /**
     *
     * 设置 pendingIentent；
     * */
    public PendingIntent getDefalutIntent(Context context ,int flags) {
        Intent broadcastIntent = new Intent(context, NotificationReceiver.class);
        Bundle bundle = new Bundle();
//        bundle.putString("data", data);
        broadcastIntent.putExtra("bundle", bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }

}
