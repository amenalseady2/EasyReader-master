package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.MessageDetailAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LiveDetailMessage;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//具体消息
public class MessageDetailActivity extends BaseActivity {

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
    List<LiveDetailMessage> messageList;
    int length = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent().getIntExtra("type", 2) == 2) {

            setTitle("物流消息");


            String message = SHPUtils.getParame(this, SHPUtils.MESSAGE);
            if (StringUtil.isEmpty(message)) {

            } else {
                messageList = new ArrayList<>();

                if (message.contains("&")) {
                    String[] messages = message.split("&");
                    length = messages.length;
                    for (int i = 0; i < length; i++) {
                        try {
                            JSONObject json = new JSONObject(messages[i]);
                            JSONObject dataObj = json.optJSONObject("data");
                            LiveDetailMessage model = new LiveDetailMessage();
                            model.avatar = dataObj.optString("avatar");
                            model.nickname = dataObj.optString("nickname");
                            model.content = dataObj.optString("content");
                            model.time = dataObj.optString("time");

                            messageList.add(model);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    length = 1;
                    try {
                        JSONObject json = new JSONObject(message);
                        JSONObject dataObj = json.optJSONObject("data");
                        LiveDetailMessage model = new LiveDetailMessage();
                        model.avatar = dataObj.optString("avatar");
                        model.nickname = dataObj.optString("nickname");
                        model.content = dataObj.optString("content");
                        model.time = dataObj.optString("time");

                        messageList.add(model);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                list.setAdapter(new MessageDetailAdapter(messageList,3));
            }
        } else if(getIntent().getIntExtra("type", 2) == 4)
        {
            setTitle("头条");


            String message = SHPUtils.getParame(this, SHPUtils.NEWS_MESSAGE);
            if (StringUtil.isEmpty(message)) {

            } else {
                messageList = new ArrayList<>();

                if (message.contains("&")) {
                    String[] messages = message.split("&");
                    length = messages.length;
                    for (int i = 0; i < length; i++) {
                        try {
                            JSONObject json = new JSONObject(messages[i]);
                            JSONObject dataObj = json.optJSONObject("data");
                            LiveDetailMessage model = new LiveDetailMessage();
                            model.avatar = dataObj.optString("avatar");
                            model.nickname = dataObj.optString("nickname");
                            model.content = dataObj.optString("content");
                            model.time = dataObj.optString("time");

                            messageList.add(model);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    length = 1;
                    try {
                        JSONObject json = new JSONObject(message);
                        JSONObject dataObj = json.optJSONObject("data");
                        LiveDetailMessage model = new LiveDetailMessage();
                        model.avatar = dataObj.optString("avatar");
                        model.nickname = dataObj.optString("nickname");
                        model.content = dataObj.optString("content");
                        model.time = dataObj.optString("time");

                        messageList.add(model);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                list.setAdapter(new MessageDetailAdapter(messageList,3));}
        }else {
            setTitle("小秘书");


            String message = SHPUtils.getParame(this, SHPUtils.ORDER_MESSAGE);
            if (StringUtil.isEmpty(message)) {

            } else {
                messageList = new ArrayList<>();

                if (message.contains("&")) {
                    String[] messages = message.split("&");
                    length = messages.length;
                    for (int i = 0; i < length; i++) {
                        try {
                            JSONObject json = new JSONObject(messages[i]);
                            JSONObject dataObj = json.optJSONObject("data");
                            LiveDetailMessage model = new LiveDetailMessage();
                            model.avatar = dataObj.optString("avatar");
                            model.nickname = dataObj.optString("nickname");
                            model.content = dataObj.optString("content");
                            model.time = dataObj.optString("time");

                            messageList.add(model);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    length = 1;
                    try {
                        JSONObject json = new JSONObject(message);
                        JSONObject dataObj = json.optJSONObject("data");
                        LiveDetailMessage model = new LiveDetailMessage();
                        model.avatar = dataObj.optString("avatar");
                        model.nickname = dataObj.optString("nickname");
                        model.content = dataObj.optString("content");
                        model.time = dataObj.optString("time");

                        messageList.add(model);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                list.setAdapter(new MessageDetailAdapter(messageList,3));
            }
        }
    }
}


