package com.BlackDiamond2010.hzs.ui.activity.lives.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.base.BaseRecyclerViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/10/17.
 */

public class WebViewAdapter extends BaseRecyclerAdapter<String> {

    public View.OnClickListener itemListener;

    public View.OnClickListener btnListener;



    @Override
    public void setOnItemClickListener(OnItemClickListener<String> listener) {
        super.setOnItemClickListener(listener);
    }

    public WebViewAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;

//        initWebView(viewHolder.detailContent,"http://api.kanjian2020.com/template?type=1&id=1");
//        setWebViewSetting(viewHolder.detailContent);
        viewHolder.detailContent.loadDataWithBaseURL(mDatas.get(position),mDatas.get(position),"text/html", "UTF-8", null);
    }




    /**
     * 网页的一些优化
     */
    public void setWebViewSetting(WebView webview) {
//        webview.setWebChromeClient(new WebChromeClient());
//        webview.getSettings().setDefaultTextEncodingName("UTF-8") ;
//        webview.getSettings().setBlockNetworkImage(false);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            webview.getSettings().setMixedContentMode(webview.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);  //注意安卓5.0以上的权限
//        }
//        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        webview.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        // 扩大比例的缩放
        webview.getSettings().setUseWideViewPort(true);

        // 自适应屏幕
        // 支持内容重新布局
        webview.getSettings().setLayoutAlgorithm(
                WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 缩放至屏幕的大小
        webview.getSettings().setLoadWithOverviewMode(true);

    }

    /**
     * webview 加载网络
     */
    private void initWebView(WebView webView ,String url) {
        setWebViewSetting(webView);
        //WebView加载web资源
        webView.loadUrl(url);//商品详情 ，加载ur
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = null;
        BaseRecyclerViewHolder mViewHolder = null;
        mView = mInflater.inflate(R.layout.item_webview, parent, false);
        mViewHolder = new ViewHolder(mView);


        return mViewHolder;
    }

    public class ViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.detail_content)
        WebView detailContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
