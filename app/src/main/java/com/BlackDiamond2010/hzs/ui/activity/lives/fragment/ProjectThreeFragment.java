package com.BlackDiamond2010.hzs.ui.activity.lives.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.AppConstants;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.LoginActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.activity.ProjectDetailActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.ProjectAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.adapter.ProjectHeadAdapter;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.Category;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ProModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.ProjectModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CommonUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.SHPUtils;
import com.BlackDiamond2010.hzs.ui.fragment.BaseFragment;
import com.BlackDiamond2010.hzs.view.GridViewForScrollView;
import com.BlackDiamond2010.hzs.view.XRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectThreeFragment extends BaseFragment {
    @BindView(R.id.recyclerView_topnews)
    XRecycleView recyclerViewTopnews;
    private ProjectAdapter mAdapter;
    private int page = 1;
    private int type = 3;
    private int category_id = 0;
    ProjectHeadAdapter headAdapter;

    @Override
    protected void loadData() {setState(AppConstants.STATE_SUCCESS);

        getData(type, page, category_id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_one;
    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置分割线
        recyclerViewTopnews.setLayoutManager(layoutManager);
        recyclerViewTopnews.addHeadView(headView());


        recyclerViewTopnews.setRefreshAndLoadMoreListener(new XRecycleView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData(type, page, category_id);
            }

            @Override
            public void onLoadMore() {
                ++page;
                getData(type, page, category_id);
//                recyclerViewTopnews.setloadMoreDone();
            }
        });
    }

    View.OnClickListener itemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            mackToastLONG(v.getTag().toString(),getContext());
            Intent intent = new Intent(getContext(),ProjectDetailActivity.class);
            ProModel mode =  mAdapter.getitem(Integer.valueOf(v.getTag().toString()));
            intent.putExtra("name",mode.name);
            intent.putExtra("id",mode.id+"");
            startActivityForResult(intent,888);

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null&&requestCode == 888){

            getData(type, page, category_id);

        }
    }

    GridViewForScrollView gridView;
    private View headView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.head_project, null);
        gridView = (GridViewForScrollView) view.findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getData(type, page, cateList.get(position).id);
                headAdapter.setPositon(position);
            }
        });
        return view;
    }

    @Override
    protected void initInject() {

    }
    public List<Category> cateList = new ArrayList<>();
    public void getData(int type, final int page, int category) {

        showLoadingDialog();
        String token = SHPUtils.getParame(getContext(), SHPUtils.TOKEN);
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        projectList(token, AndroidUtils.getAndroidId(getContext()), page, category, type),
                new HttpResultCall<HttpResult<ProjectModel>, ProjectModel>() {


                    @Override
                    public void onResponse(ProjectModel shopBeen, String msg) {
                        if (shopBeen == null || shopBeen.project.size() == 0) {

                        } else {
                            if (page == 1) {
                                if (mAdapter == null) {
                                    mAdapter = new ProjectAdapter(getContext(), shopBeen.project);
                                    mAdapter.itemListener =itemListener;
                                    recyclerViewTopnews.setAdapter(mAdapter);
                                    if (shopBeen.category.size() != 0){
                                        cateList = shopBeen.category;
                                        if (headAdapter == null){
                                            headAdapter = new ProjectHeadAdapter(shopBeen.category);
                                            gridView.setAdapter(headAdapter);
                                        }
                                    }

                                } else {
                                    mAdapter.setData(shopBeen.project);
                                }


                            } else {
                                mAdapter.addData(shopBeen.project);
                            }

                        }

                        if (shopBeen.next == 0) {
                            recyclerViewTopnews.setloadMoreDone();
                        }
                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                        if (status == 405) {
                            CommonUtils.startAct(getContext(), LoginActivity.class);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        recyclerViewTopnews.setReFreshComplete();
                        dismissDialog();
                        super.onCompleted();
                    }
                });


    }

}
