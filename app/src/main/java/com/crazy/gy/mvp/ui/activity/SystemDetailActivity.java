package com.crazy.gy.mvp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crazy.gy.R;
import com.crazy.gy.entity.KnowledgeListBean;
import com.crazy.gy.mvp.base.mBaseActivity;
import com.crazy.gy.mvp.ui.adapter.SystemDetailListAdapter;
import com.crazy.gy.mvp.ui.fragment.SystemDetailListFragment;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SystemDetailActivity extends mBaseActivity {

    @Bind(R.id.system_toolbar)
    Toolbar systemToolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.system_tab_layout)
    SlidingTabLayout systemTabLayout;
    @Bind(R.id.system_viewpager)
    ViewPager systemViewpager;
    @Bind(R.id.float_button)
    FloatingActionButton floatButton;
    private List<String> titles;
    private List<Fragment> fragments;
    private SystemDetailListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_detail;
    }

    @Override
    protected void initView() {
        setSupportActionBar(systemToolbar);
        systemToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition(SystemDetailActivity.this);
            }
        });
    }


    @Override
    protected void initData() {
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
        adapter = new SystemDetailListAdapter(getSupportFragmentManager(), fragments);
        getSystemBundleData();
    }

    /**
     * 获取体系界面传过来的参数
     */
    private void getSystemBundleData() {
        KnowledgeListBean systemBean = (KnowledgeListBean) getIntent().getSerializableExtra(ConstantUtil.SYSTEM);
        if (systemBean != null) {
            fragments.clear();
            getSupportActionBar().setTitle(systemBean.getName());
            for (KnowledgeListBean.Children childrenBean : systemBean.getChildren()) {
                titles.add(childrenBean.getName());
                fragments.add(SystemDetailListFragment.getInstance(childrenBean.getId()));
            }
        }
        systemViewpager.setAdapter(adapter);
        systemTabLayout.setViewPager(systemViewpager, titles.toArray(new String[titles.size()]));
        adapter.notifyDataSetChanged();
    }



    @OnClick(R.id.float_button)
    public void onViewClicked() {
        SystemDetailListFragment fragment = adapter.getCurrentFragment();
        fragment.scrollToTop();
    }
}
