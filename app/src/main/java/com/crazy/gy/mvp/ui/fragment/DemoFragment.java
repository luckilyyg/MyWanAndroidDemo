package com.crazy.gy.mvp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.crazy.gy.R;
import com.crazy.gy.entity.DemoTitleBean;
import com.crazy.gy.mvp.base.BaseFragment;
import com.crazy.gy.mvp.contract.DemoContract;
import com.crazy.gy.mvp.presenter.DemoPresenter;
import com.crazy.gy.mvp.ui.adapter.DemoFragmentAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DemoFragment extends BaseFragment implements DemoContract.View{


    @Bind(R.id.demo_tab_layout)
    SlidingTabLayout demoTabLayout;
    @Bind(R.id.demo_view_pager)
    ViewPager demoViewPager;
    @Bind(R.id.normal_view)
    LinearLayout normalView;
    List<DemoTitleBean> demoBeanList;
    List<Fragment> fragmentList;
    List<String> titles;
    DemoPresenter demoPresenter;
    DemoFragmentAdapter adapter;
    public DemoFragment() {
        // Required empty public constructor
    }

    public static DemoFragment getInstance() {
        return new DemoFragment();
    }



    @Override
    public int getLayoutResID() {
        return R.layout.fragment_demo;
    }

    @Override
    protected void initData() {
        demoBeanList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titles = new LinkedList<>();
        demoPresenter = new DemoPresenter(this);
        adapter = new DemoFragmentAdapter(getChildFragmentManager(),fragmentList);
        demoPresenter.getDemoTitleList();
    }


    @Override
    public void getDemoResultOK(List<DemoTitleBean> demoBeans) {
        demoBeanList.clear();
        fragmentList.clear();
        demoBeanList.addAll(demoBeans);
        if(demoBeanList.size()>0){
            for(DemoTitleBean demoTitleBean : demoBeanList){
                fragmentList.add(DemoDetailListFragment.getInstance(demoTitleBean.getId()));
                titles.add(demoTitleBean.getName());
            }
            demoViewPager.setAdapter(adapter);
            demoTabLayout.setViewPager(demoViewPager, titles.toArray(new String[titles.size()]));
            adapter.notifyDataSetChanged();
        }
        showNormal();
    }

    @Override
    public void getDemoResultErr(String info) {
        showError(info);
    }

    @Override
    public void reload() {
        super.reload();
        showLoading();
        demoPresenter.getDemoTitleList();
    }

    /**
     * 查找 子 fragment 回到顶部
     */
    public void scrollChildToTop(){
        DemoDetailListFragment currentFragment = adapter.getCurrentFragment();
        currentFragment.scrollToTop();
    }
}
