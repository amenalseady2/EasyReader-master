package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.NewsDetailAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.NewsDetailModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.NewsModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.ShareUtil;
import com.BlackDiamond2010.hzs.view.ListViewForScrollView;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.auther)
    TextView auther;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_seenum)
    TextView tvSeenum;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.lv_news)
    ListViewForScrollView lvNews;
    @BindView(R.id.scroll)
    ScrollView scroll;
    String id;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_right2)
    ImageView ivRight2;
    @BindView(R.id.iv_like)
    ImageView ivLike;
    @BindView(R.id.num_like)
    TextView numLike;
    @BindView(R.id.videoplayer)
    StandardGSYVideoPlayer videoPlayer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("头条详情");
        id = getIntent().getStringExtra("id");

        ivRight.setVisibility(View.VISIBLE);
        ivRight2.setVisibility(View.VISIBLE);
        ivRight.setBackgroundResource(R.mipmap.fenxiang_anli);
        ivRight2.setBackgroundResource(R.drawable.soucang_normal);
        getData(id);
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.drawable.ic_default_place_holder);
        videoPlayer.setThumbImageView(imageView);
    }

    //	type	integer	是	1直播 2项目 3文章 4商品  收藏操作
    public void addCollect(String id, int type) {

        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        addCollect(type, id, AndroidUtils.getAndroidId(getApplicationContext())),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object ShopModel, String msg) {
                        mackToastLONG(msg, getApplicationContext());

                        if ("已取消".equals(msg)) {
                            ivRight2.setBackgroundResource(R.drawable.soucang_normal);
                        } else if ("已收藏".equals(msg)) {
                            ivRight2.setBackgroundResource(R.drawable.soucang_pressed);
                        }


                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                        if ("不能重复收藏".equals(err)) {
                            ivRight2.setBackgroundResource(R.drawable.soucang_pressed);
                        }
                        if (status == 405) {
                            CommonUtils.startAct(NewsDetailActivity.this, LoginActivity.class);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        dismissDialog();
                    }
                });


    }


    public void dianzan(String id) {

        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        dianzan(AndroidUtils.getAndroidId(getApplicationContext()), id),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object ShopModel, String msg) {
//                        mackToastLONG("", getApplicationContext());
                        newsDetailModel.is_like = 1;
                        int dianz = Integer.valueOf(numLike.getText().toString());
                        ivLike.setImageResource(R.mipmap.dianzan_pressd);
                        numLike.setText(++dianz + "");

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

    private void setData(final NewsDetailModel mod) {
        NewsModel model = mod.detail;
        title.setText(model.title);
        auther.setText(model.author);
        tvTime.setText(model.create_at);
        tvSeenum.setText(model.pv + "");

        if (mod.is_collection == 1) {
            ivRight2.setBackgroundResource(R.drawable.soucang_pressed);
        } else {
            ivRight2.setBackgroundResource(R.drawable.soucang_normal);
        }

        if (mod.is_like == 1) {
            ivLike.setImageResource(R.mipmap.dianzan_pressd);
        } else {
            ivLike.setImageResource(R.mipmap.dianzan_nomar);
        }
        numLike.setText(mod.total_like + "");


        initWebView(model.url);


        lvNews.setAdapter(new NewsDetailAdapter(mod.other));
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.startActWithData(NewsDetailActivity.this, NewsDetailActivity.class, "id", mod.other.get(position).id + "");
//                getData(mod.other.get(position).id+"");
                finish();
            }
        });

    }

    /**
     * webview 加载网络
     */
    private void initWebView(String url) {
        setwebSetting();
        //WebView加载web资源
//        webview.loadUrl(url);//商品详情 ，加载ur
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
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
                        NewsDetailActivity.this.runOnUiThread(new Runnable() {
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
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

//        webview.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    private void setWebview(String content) {
        setwebSetting();
        webview.loadData(content, "text/html", "UTF-8");
    }

    /**
     * 网页的一些优化
     */
    public void setwebSetting() {
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


//        WebSettings ws = webview.getSettings();
//        ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
//
//        ws.setUseWideViewPort(true);// 可任意比例缩放
//        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
//
//        ws.setSavePassword(true);
//        ws.setSaveFormData(true);// 保存表单数据
//        ws.setJavaScriptEnabled(true);
//
//        ws.setDomStorageEnabled(true);
//        ws.setSupportMultipleWindows(true);// 新加
//
////我就是没有这一行，死活不出来。MD，硬是没有人写这一句的
//        webview.setWebChromeClient(new WebChromeClient());
//        webview.setWebViewClient(new WebViewClient());
//        webview.loadUrl("https://my.tv.sohu.com/us/312813498/99969124.shtml");


    }

    NewsDetailModel newsDetailModel;

    private void getData(String id) {

        showLoadingDialog();
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().newsDetail(id,
                AndroidUtils.getAndroidId(MyApplication.instance.getApplicationContext())),
                new HttpResultCall<HttpResult<NewsDetailModel>, NewsDetailModel>() {

                    @Override
                    public void onResponse(NewsDetailModel result, String msg) {

                        setData(result);
                        newsDetailModel = result;
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

    @OnClick({R.id.iv_right, R.id.iv_right2, R.id.rl_dianz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_dianz:
                if (newsDetailModel.is_like != 1) {
                    dianzan(id);
                } else {
                    mackToastLONG("您已点赞过", getApplicationContext());
                }

                break;
            case R.id.iv_right:
                View viewRoot = NewsDetailActivity.this.findViewById(R.id.newsdetail_root);
                ShareUtil u = new ShareUtil(NewsDetailActivity.this, viewRoot, newsDetailModel.detail.title, R.drawable.luyan_logo, newsDetailModel.detail.title, newsDetailModel.detail.url, 1);
                break;
            case R.id.iv_right2:
                addCollect(id, 3);
                break;
        }
    }
}
