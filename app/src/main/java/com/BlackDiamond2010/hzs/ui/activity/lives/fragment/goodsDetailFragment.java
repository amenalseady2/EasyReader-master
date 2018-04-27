package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class goodsDetailFragment extends BaseFragment {
    @BindView(R.id.detail_content)
    WebView detailContent;

//    @BindView(R.id.rcv_activity)
//    RecyclerView rcvActivity;

    private String url;

    public goodsDetailFragment(String url) {
        this.url = url;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void loadData() {
        setState(AppConstants.STATE_SUCCESS);
//        rcvActivity.setLayoutManager(new LinearLayoutManager(getContext()));
//        List<String> urlList = new ArrayList<>();
//        urlList.add(url);
//        rcvActivity.setAdapter(new WebViewAdapter(getContext(),urlList));
        detailContent.loadDataWithBaseURL(url,url,"text/html", "UTF-8", null);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_webview;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initInject() {

    }

}
