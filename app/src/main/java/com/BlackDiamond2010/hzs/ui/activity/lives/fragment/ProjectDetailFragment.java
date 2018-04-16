package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;

import butterknife.BindView;

/**
 * Created by ASUS on 2017/10/23.
 */

public class ProjectDetailFragment extends BaseFragment {
    @BindView(R.id.web)
    WebView web;
    private String url;
    private String content;



    public ProjectDetailFragment(String url, String content){
        this.url = url;
        this.content = content;
//        if (url == null){
//            loadimgContent(content);
//        }else{
//            initweb(url);
//        }
    }



    @Override
    protected void loadData() {

        setState(AppConstants.STATE_SUCCESS);

    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        url=getArguments().getString("url");
//        content=getArguments().getString("content");
//        if (url == null){
//            loadimgContent(content);
//        }else{
//            initweb(url);
//        }
//    }
//
//    public static final ProjectDetailFragment newInstance(String url,String content)
//    {
//        ProjectDetailFragment fragment = new ProjectDetailFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("url",url);
//        bundle.putString("content", content);
//        fragment.setArguments(bundle);
//        return fragment ;
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_projectdetail;
    }

    @Override
    protected void initView() {
        if (url == null){
            loadimgContent(content);
        }else{
            initweb(url);
        }
    }


    /**
     * 网页的一些优化
     */
    public void setwebSetting() {
        web.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        // goods_des.getSettings().setSupportZoom(true);
        // goods_des.getSettings().setBuiltInZoomControls(true);
        // 扩大比例的缩放
        web.getSettings().setUseWideViewPort(true);
        // 自适应屏幕
        // 支持内容重新布局
        web.getSettings().setLayoutAlgorithm(
                WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 缩放至屏幕的大小
        web.getSettings().setLoadWithOverviewMode(true);

    }
    /**
     * web 加载网络
     */
    private void initweb(String url) {
        setwebSetting();
        //web加载web资源
        web.loadUrl(url);//商品详情 ，加载ur
        //覆盖web默认使用第三方或系统默认浏览器打开网页的行为，使网页用web打开
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去web打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }


    private void loadimgContent(String content){

        setwebSetting();
//        web.loadData(content, "text/html", "UTF-8") ;
        web.loadDataWithBaseURL(content,content,"text/html", "UTF-8", null);
    }
    
    
    @Override
    protected void initInject() {

    }

}
