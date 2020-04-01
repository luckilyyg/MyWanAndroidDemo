package com.crazy.gy.mvp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.gy.R;
import com.crazy.gy.entity.HotKeyBean;
import com.crazy.gy.mvp.base.BaseResultActivity;
import com.crazy.gy.mvp.contract.SearechContract;
import com.crazy.gy.mvp.presenter.SearechPresenter;
import com.crazy.gy.mvp.ui.adapter.SearechAdapter;
import com.crazy.gy.mvp.util.ColorUtil;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.crazy.gy.mvp.util.JumpUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearechActivity extends BaseResultActivity implements SearechContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.flow_search)
    TagFlowLayout flowSearch;
    @Bind(R.id.tv_clear)
    TextView tvClear;
    @Bind(R.id.rv_history)
    RecyclerView rvHistory;
    @Bind(R.id.normal_view)
    LinearLayout normalView;
    /**
     * 本地保存历史记录
     */
    private List<String> historyList;
    private SearechPresenter presenter;
    private SearechAdapter adapter;
    /**
     * 热搜 词语
     */
    private List<HotKeyBean> hotBeans;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_searech;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    protected void initView() {
        super.initView();
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void initData() {
        hotBeans = new LinkedList<>();
        historyList = new ArrayList<>();
        presenter = new SearechPresenter(this);
        adapter = new SearechAdapter(R.layout.item_search_history,historyList);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        rvHistory.setAdapter(adapter);
        presenter.getHotListResult();
        presenter.getHistoryList(activity,historyList);
        showLoading();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        historyList.remove(position);
        adapter.notifyDataSetChanged();
        presenter.saveHistory(activity, historyList);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtil.SEARCH_RESULT_KEY,(String) adapter.getData().get(position));
        JumpUtil.overlay(activity, SearechDetailActivity.class, bundle);
    }

    @Override
    public void getHotListOk(List<HotKeyBean> beanList) {
        hotBeans.clear();
        hotBeans.addAll(beanList);
        adapter.notifyDataSetChanged();
        initFlowLayout();
        showNormal();
    }
    /**
     * 初始化 flowLayout
     */
    private void initFlowLayout() {
        TagAdapter<HotKeyBean> tagAdapter = new TagAdapter<HotKeyBean>(hotBeans) {
            @Override
            public View getView(FlowLayout parent, int position, HotKeyBean searchHot) {
                TextView text = (TextView) getLayoutInflater().inflate(R.layout.textview_item_hot, null);
                String name = searchHot.getName();
                text.setText(name);
                text.setTextColor(ColorUtil.getRandomColor());
                return text;
            }
        };
        flowSearch.setAdapter(tagAdapter);
        flowSearch.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String name = hotBeans.get(position).getName();
                if (!historyList.contains(name)) {
                    historyList.add(name);
                    presenter.saveHistory(activity, historyList);
                }
                Bundle bundle = new Bundle();
                bundle.putString(ConstantUtil.SEARCH_RESULT_KEY, name);
                JumpUtil.overlay(activity, SearechDetailActivity.class, bundle);
                return true;
            }
        });
    }
    @Override
    public void getHotListErr(String err) {
        showError(err);
    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError(String err) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void reload() {
        super.reload();
        if(adapter!=null){
            presenter.getHotListResult();
            presenter.getHistoryList(activity,historyList);
        }
    }

    /**
     * 设置title 上的搜索 栏
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        mSearchView.setQueryHint(getString(R.string.input_search_content));
        mSearchView.setIconified(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!historyList.contains(query)) {
                    historyList.add(query);
                    adapter.notifyDataSetChanged();
                    presenter.saveHistory(activity, historyList);
                }
                Bundle bundle = new Bundle();
                bundle.putString(ConstantUtil.SEARCH_RESULT_KEY,query);
                JumpUtil.overlay(activity, SearechDetailActivity.class, bundle);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @OnClick(R.id.tv_clear)
    public void onViewClicked() {
        historyList.clear();
        adapter.notifyDataSetChanged();
        presenter.saveHistory(activity, historyList);
    }
}
