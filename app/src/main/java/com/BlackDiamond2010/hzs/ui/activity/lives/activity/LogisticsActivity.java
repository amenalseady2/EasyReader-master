package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.WuliuAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LogisticsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import butterknife.BindView;

//物流
public class LogisticsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.goodsimg)
    ImageView goodsimg;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.orderid)
    TextView orderid;
    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.list)
    ListView list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_logistics;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("查看物流");
        getInfo(getIntent().getStringExtra("id"));

    }


    public void getInfo(String id) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().express(id, AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext())
                ),
                new HttpResultCall<HttpResult<LogisticsModel>, LogisticsModel>() {

                    @Override
                    public void onResponse(LogisticsModel result, String msg) {
                        if (result != null){
                            list.setAdapter(new WuliuAdapter(result.list));
                            GlideUtils.loadImage(3,result.cover,goodsimg);

                            status.setText(result.status);
                            companyName.setText(result.express_name);
                            phone.setText(result.phone);
                            orderid.setText("运单号："+result.express_sn);

                        }


                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        dismissDialog();
                    }
                });


    }

}
