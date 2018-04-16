package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StringUtil;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;

public class WebActivity extends BaseActivity {


    @BindView(R.id.div)
    View div;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.videoplayer)
    StandardGSYVideoPlayer videoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");
        setTitle(title);
        String content = intent.getStringExtra("content");
        if (!StringUtil.isEmpty(content)) {
            loadimgContent(content);
        } else {
            initWebView(url);
        }
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.drawable.ic_default_place_holder);
        videoPlayer.setThumbImageView(imageView);
    }


    private void loadimgContent(String content) {

        setWebViewSetting();
        webview.loadData(content, "text/html", "UTF-8");
    }


    @Override
    protected int getLayoutId() {
        return R.layout.webview;
    }

    /**
     * 网页的一些优化
     */
    public void setWebViewSetting() {
        webview.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        // goods_des.getSettings().setSupportZoom(true);
        // goods_des.getSettings().setBuiltInZoomControls(true);
        // 扩大比例的缩放
        webview.getSettings().setUseWideViewPort(true);
        // 自适应屏幕
        // 支持内容重新布局
        webview.getSettings().setLayoutAlgorithm(
                WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 缩放至屏幕的大小
        webview.getSettings().setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

    }

    /**
     * webview 加载网络
     */
    private void initWebView(String url) {
        setWebViewSetting();
        //WebView加载web资源
//        webview.loadUrl(url);//商品详情 ，加载ur
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                try {
                    Document document = Jsoup.connect(strings[0]).get();
                    Elements elements = document.getElementsByTag("video");
                    if (elements.size() > 0) {
                        Element elementVideo = elements.get(0);
                        elementVideo.remove();
                        final String videoUrl = elementVideo.attr("src");
                        WebActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                videoPlayer.setVisibility(View.VISIBLE);
                                videoPlayer.setUp(videoUrl, false);
                            }
                        });
                    }
                    return document.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "";
            }

            @Override
            protected void onPostExecute(String s) {
                webview.loadDataWithBaseURL(null, s, "text/html", "utf-8", null);
            }
        }.execute(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoPlayer.onVideoPause();
    }
}