package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.MessageAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MessageType;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyMessageActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.list)
    ListView list;
    //    List<LiveDetailMessage> messageList;
    int length = 0;

    private List<MessageType> mMessageList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的消息");


        mMessageList = new ArrayList<>();

        String newsList = SHPUtils.getParame(this, SHPUtils.NEWS_MESSAGE);
        if (StringUtil.isEmpty(newsList)) {
            MessageType model = new MessageType();
            model.content = "还没有收到任何通知";
            model.time = "";
            model.type = 4;//
            model.num = 0;
            mMessageList.add(model);
        } else {
//                messageList = new ArrayList<>();

            if (newsList.contains("&")) {
                String[] messages = newsList.split("&");
                length = messages.length;
                for (int i = 0; i < 1; i++) {
                    try {
                        JSONObject json = new JSONObject(messages[i]);
                        JSONObject dataObj = json.optJSONObject("data");
                        MessageType model = new MessageType();
                        model.content = dataObj.optString("content");
                        model.time = dataObj.optString("time");
                        model.type = 4;
                        model.num = length;
                        mMessageList.add(model);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                length = 1;
                try {
                    JSONObject json = new JSONObject(newsList);
                    JSONObject dataObj = json.optJSONObject("data");
                    MessageType model = new MessageType();
                    model.content = dataObj.optString("content");
                    model.time = dataObj.optString("time");
                    model.type = 4;
                    model.num = 1;
                    mMessageList.add(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        String XIAOMISHU = SHPUtils.getParame(this, SHPUtils.ORDER_MESSAGE);
        if (StringUtil.isEmpty(XIAOMISHU)) {
            MessageType model = new MessageType();
            model.content = "还没有收到任何通知";
            model.time = "";
            model.type = 3;//小秘书
            model.num = 0;
            mMessageList.add(model);
        } else {
//                messageList = new ArrayList<>();

            if (XIAOMISHU.contains("&")) {
                String[] messages = XIAOMISHU.split("&");
                length = messages.length;
                for (int i = 0; i < 1; i++) {
                    try {
                        JSONObject json = new JSONObject(messages[i]);
                        JSONObject dataObj = json.optJSONObject("data");
                        MessageType model = new MessageType();
                        model.content = dataObj.optString("content");
                        model.time = dataObj.optString("time");
                        model.type = 3;
                        model.num = length;
                        mMessageList.add(model);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                length = 1;
                try {
                    JSONObject json = new JSONObject(XIAOMISHU);
                    JSONObject dataObj = json.optJSONObject("data");
                    MessageType model = new MessageType();
                    model.content = dataObj.optString("content");
                    model.time = dataObj.optString("time");
                    model.type = 3;
                    model.num = 1;
                    mMessageList.add(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        String message = SHPUtils.getParame(this, SHPUtils.MESSAGE);
        if (StringUtil.isEmpty(message)) {
            MessageType model = new MessageType();
            model.content = "还没有收到任何通知";
            model.time = "";
            model.type = 2;
            model.num = 0;
            mMessageList.add(model);
        } else {
//            messageList = new ArrayList<>();

            if (message.contains("&")) {
                String[] messages = message.split("&");
                length = messages.length;
                for (int i = 0; i < 1; i++) {
                    try {
                        JSONObject json = new JSONObject(messages[i]);
                        JSONObject dataObj = json.optJSONObject("data");
                        MessageType model = new MessageType();
                        model.content = dataObj.optString("content");
                        model.time = dataObj.optString("time");
                        model.type = 2;
                        model.num = length;
                        mMessageList.add(model);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                length = 1;
                try {
                    JSONObject json = new JSONObject(message);
                    JSONObject dataObj = json.optJSONObject("data");
                    MessageType model = new MessageType();
                    model.content = dataObj.optString("content");
                    model.time = dataObj.optString("time");
                    model.type = 2;
                    model.num = 1;
                    mMessageList.add(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        list.setAdapter(new MessageAdapter(mMessageList));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(MyMessageActivity.this, TodayTopicActivity.class);
                    startActivity(intent);
                } else {
                    if (mMessageList.get(position).num == 0) {
                        mackToastLONG("没有任何通知信息", MyMessageActivity.this);
                    } else {
                        CommonUtils.startActWithData(MyMessageActivity.this, MessageDetailActivity.class, "type", mMessageList.get(position).type);
                    }
                }
            }
        });
    }
}
