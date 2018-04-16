package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.WuliuAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.GoodsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.LogisticsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.MyOrderItem;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.utils.GlideUtils;

import butterknife.BindView;

//退款中
public class RefundActivity extends BaseActivity {

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
    @BindView(R.id.goodsname)
    TextView goodsname;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.list)
    ListView list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyOrderItem model = (MyOrderItem) getIntent().getSerializableExtra("model");
        GoodsModel item = model.product_list.get(0);
        GlideUtils.loadImage(3, item.cover, goodsimg);
//        status.setText(result.status);

        switch (model.service){

            case 1:
               status.setText("退款");
                setTitle("退款");
                break;
            case 2:
               status.setText("退货退款");
                setTitle("退货退款");
                break;
            case 3:
               status.setText("退款中");
                setTitle("退款中");
                break;
            case 4:
               status.setText("已退款");
                setTitle("已退款");
                break;
            case 5:
               status.setText("交易关闭");
                setTitle("交易关闭");
                break;

        }
        goodsname.setText(item.name);
        price.setText("￥"+item.price);
        getInfo(model.id);
    }

    public void getInfo(String id) {
        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().serverDetail(id, AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext())
                ),
                new HttpResultCall<HttpResult<LogisticsModel>, LogisticsModel>() {

                    @Override
                    public void onResponse(LogisticsModel result, String msg) {
                        if (result != null) {
                            list.setAdapter(new WuliuAdapter(result.list));
//                            GlideUtils.loadImage(3, result.cover, goodsimg);
//
//                            status.setText(result.status);
//                            companyName.setText(result.express_name);
//                            phone.setText(result.phone);
//                            orderid.setText("运单号：" + result.express_sn);

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
