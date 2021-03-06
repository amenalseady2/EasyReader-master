package com.BlackDiamond2010.hzs.ui.fragment.wechat;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.bean.wechat.WXItemBean;
import com.BlackDiamond2010.hzs.injector.component.fragment.DaggerWeChatComponent;
import com.BlackDiamond2010.hzs.injector.module.fragment.WeChatModule;
import com.BlackDiamond2010.hzs.injector.module.http.WeChatHttpModule;
import com.BlackDiamond2010.hzs.presenter.WeChatPresenter;
import com.BlackDiamond2010.hzs.presenter.impl.WeChatPresenterImpl;
import com.BlackDiamond2010.hzs.rx.RxBus;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by quantan.liu on 2017/3/22.
 */

public class WeChatFragment extends BaseFragment<WeChatPresenterImpl> implements WeChatPresenter.View {

    @BindView(R.id.rcv_activity)
    RecyclerView rcvActivity;

    private static final int NUM_OF_PAGE = 20;

    private int currentPage = 1;
    private List<WXItemBean> data;
    private CompositeSubscription searshSubscription;

    @Override
    public void refreshView(List<WXItemBean> data) {
        mAdapter.setNewData(data);
    }

    @Override
    protected void loadData() {
        mPresenter.fetchWeChatHot(NUM_OF_PAGE, currentPage);
        if (this.searshSubscription == null) {
            registerEvent();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initView() {
        rcvActivity.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvActivity.setAdapter(mAdapter);
    }

    @Override
    protected void initInject() {
        DaggerWeChatComponent.builder()
                .weChatHttpModule(new WeChatHttpModule())
                .weChatModule(new WeChatModule())
                .build().injectWeChat(this);
    }

    public void registerEvent() {
        Subscription mSubscription = RxBus.getDefault().toObservable(AppConstants.WECHA_SEARCH, String.class)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mPresenter.fetchWXHotSearch(20, 1, s);
                    }
                });
        if (this.searshSubscription == null) {
            searshSubscription = new CompositeSubscription();
        }
        searshSubscription.add(mSubscription);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (this.searshSubscription != null && searshSubscription.hasSubscriptions()) {
            this.searshSubscription.unsubscribe();
        }
    }
}
