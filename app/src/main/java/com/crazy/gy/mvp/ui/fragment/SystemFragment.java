package com.crazy.gy.mvp.ui.fragment;


import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.gy.R;
import com.crazy.gy.entity.KnowledgeListBean;
import com.crazy.gy.mvp.base.BaseFragment;
import com.crazy.gy.mvp.contract.SystemContract;
import com.crazy.gy.mvp.presenter.SystemPresenter;
import com.crazy.gy.mvp.ui.activity.SystemDetailActivity;
import com.crazy.gy.mvp.ui.adapter.SystemAdapter;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SystemFragment extends BaseFragment implements SystemContract.View,SystemAdapter.OnItemClickListener{


    @Bind(R.id.rv_system)
    RecyclerView rvSystem;
    @Bind(R.id.normal_view)
    SmartRefreshLayout normalView;

    private List<KnowledgeListBean> systemBeanList;
    private SystemPresenter presenter;
    private SystemAdapter madapter;
    public SystemFragment() {
        // Required empty public constructor
    }

    public static SystemFragment getInstance() {
        return new SystemFragment();
    }


    /**
     * 回到顶部
     */
    public void scrollToTop() {
        rvSystem.smoothScrollToPosition(0);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_system;
    }
    @Override
    protected void initUI() {
        super.initUI();
        rvSystem.setLayoutManager(new LinearLayoutManager(activity));
        showLoading();
        setRefresh();
    }
    @Override
    protected void initData() {
        presenter = new SystemPresenter(this);
        systemBeanList = new ArrayList<>();
        madapter = new SystemAdapter(R.layout.item_system,systemBeanList);
        presenter.getSystemList();
        madapter.setOnItemClickListener(this);
        rvSystem.setAdapter(madapter);
    }

    @Override
    public void getSystemListOk(List<KnowledgeListBean> dataBean) {
        systemBeanList = dataBean;
        madapter.replaceData(dataBean);
        showNormal();
    }

    @Override
    public void getSystemListErr(String info) {
        showError(info);
    }

    /**
     * SmartRefreshLayout刷新加载
     */
    private void setRefresh() {
        normalView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.autoRefresh();
                refreshLayout.finishRefresh(1000);
            }
        });
    }
    @Override
    public void reload() {
        showLoading();
        presenter.getSystemList();
    }
    /**
     * item 点击事件
     * @param adapter
     * @param view
     * @param position
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.web_view));
        Intent intent = new Intent(activity, SystemDetailActivity.class);
        intent.putExtra(ConstantUtil.SYSTEM, (Serializable) madapter.getData().get(position));
        startActivity(intent,options.toBundle());
    }
}
