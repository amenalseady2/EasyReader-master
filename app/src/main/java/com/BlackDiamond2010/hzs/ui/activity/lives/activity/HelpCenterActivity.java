package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.HelpAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//帮助中心
public class HelpCenterActivity extends BaseActivity {

    @BindView(R.id.list)
    ListView list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_center;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("帮助中心");
        List<String> listS = new ArrayList<>();
        listS.add("为什么要完善个人主页信息？");
        listS.add("如何购买VIP会员？");
        listS.add("忘记密码怎么办？");
        listS.add("账号遗失如何找回？");
        listS.add("如何发布自己的项目？");
        listS.add("如何参加黑钻石线下活动？");

        list.setAdapter(new HelpAdapter(listS));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HelpCenterActivity.this, XieyiActivity.class);

                String des = "=";


                switch (position) {
                    case 0:
                        des = "完善个人账号信息，有助于路演大侠核实您的真实身份，方便您在购物以及预约路演大侠线上线下活动时确认身份。";

                        break;
                    case 1:
                        des = "点击个人中心选择购买VIP页面，可以选择年度会员，季度会员，月度会员进入支付页面即可购买。";

                        break;
                    case 2:
                        des = "忘记密码可以通过用户名以及手机验证码登陆修改密码。";

                        break;
                    case 3:
                        des = "需联系客服提供有效证件及登陆信息确认无误之后提供找回。";

                        break;
                    case 4:
                        des = "需联系黑钻石官方客服电话400-1782-998，联系客服人员，经筛选后的项目方可发布。";

                        break;
                    case 5:
                        des = "需联系黑钻石官方客服电话400-1782-998，联系客服人员。";

                        break;

                }
                intent.putExtra("des", des);
                startActivity(intent);
            }
        });


    }
}
