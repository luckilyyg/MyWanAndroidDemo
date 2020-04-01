package com.crazy.gy.mvp.ui.fragment;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.gy.R;
import com.crazy.gy.entity.DemoDetailListBean;
import com.crazy.gy.mvp.base.BaseFragment;
import com.crazy.gy.mvp.contract.DemoDetailListContract;
import com.crazy.gy.mvp.presenter.DemoDetailListPresenter;
import com.crazy.gy.mvp.ui.activity.HomeDetailActivity;
import com.crazy.gy.mvp.ui.adapter.DemoDetailListAdapter;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.crazy.gy.mvp.util.toast.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DemoDetailListFragment extends BaseFragment implements DemoDetailListContract.View,
        BaseQuickAdapter.OnItemClickListener{


    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.normal_view)
    SmartRefreshLayout normalView;
    DemoDetailListAdapter adapter;
    DemoDetailListPresenter presenter;
    List<DemoDetailListBean.DatasBean> demoDetailListBeans;

    public DemoDetailListFragment() {
        // Required empty public constructor
    }

    /**
     * id 编号
     */
    private int id = -1;

    public static DemoDetailListFragment getInstance(int id) {
        DemoDetailListFragment fragment = new DemoDetailListFragment();
        Bundle args = new Bundle();
        args.putInt(ConstantUtil.DEMO_FRAGMENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_demo_detail_list;
    }
    @Override
    protected void initUI() {
        super.initUI();
        normalView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.autoRefresh();
                refreshLayout.finishRefresh(1000);
            }
        });
        normalView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadMore();
                refreshLayout.finishLoadMore(1000);
            }
        });
        showLoading();
        rv.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    protected void initData() {
        presenter = new DemoDetailListPresenter(this);
        demoDetailListBeans = new LinkedList<>();
        adapter = new DemoDetailListAdapter(R.layout.item_demo_list,demoDetailListBeans);
        adapter.setOnItemClickListener(this);
        if(getArguments()!=null){
            id = getArguments().getInt(ConstantUtil.DEMO_FRAGMENT_ID);
            presenter.getDemoList(1,id);
        }
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void reload() {
        super.reload();
        if(id != -1){
            presenter.getDemoList(1,id);
        }
    }
    /**
     * 滚动到 顶部
     */
    public void scrollToTop() {
        rv.smoothScrollToPosition(0);
    }

    /**
     * item 跳转事件
     * @param madapter
     * @param view
     * @param position
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(BaseQuickAdapter madapter, View view, int position) {
        DemoDetailListBean.DatasBean bean = adapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.HOME_DETAIL_ID,bean.getId());
        bundle.putString(ConstantUtil.HOME_DETAIL_PATH,bean.getLink());
        bundle.putString(ConstantUtil.HOME_DETAIL_TITLE,bean.getTitle());
        bundle.putBoolean(ConstantUtil.HOME_DETAIL_IS_COLLECT,bean.isCollect());
        // webview 和跳转的界面布局 transitionName 一定要相同
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.web_view));
        startActivity(new Intent(activity, HomeDetailActivity.class).putExtras(bundle), options.toBundle());

    }
    /**
     * 刷新 重置，不刷新 添加
     */
    @Override
    public void getDemoListOK(DemoDetailListBean beans, boolean isRefresh) {
        if(id==-1 && adapter==null){
            return;
        }
        if(isRefresh){
            demoDetailListBeans.clear();
            demoDetailListBeans.addAll(beans.getDatas());
            adapter.replaceData(beans.getDatas());
        }else{
            if(beans.getDatas().size()>0){
                demoDetailListBeans.addAll(beans.getDatas());
                adapter.addData(beans.getDatas());
            }else{
                ToastUtil.show(context,getString(R.string.load_more_no_data));
            }
        }
        showNormal();
    }

    @Override
    public void getDemoListErr(String err) {
        showError(err);
    }

}
