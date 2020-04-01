package com.crazy.gy.mvp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.crazy.gy.R;
import com.crazy.gy.entity.WxListBean;
import com.crazy.gy.mvp.base.BaseFragment;
import com.crazy.gy.mvp.contract.WxContract;
import com.crazy.gy.mvp.presenter.WxPresenter;
import com.crazy.gy.mvp.ui.adapter.WxFragmentAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WxFragment extends BaseFragment implements WxContract.View {


    @Bind(R.id.wx_tab_layout)
    SlidingTabLayout wxTabLayout;
    @Bind(R.id.wx_view_pager)
    ViewPager wxViewPager;
    @Bind(R.id.normal_view)
    LinearLayout normalView;
    List<WxListBean> wxBeanList;
    List<Fragment> fragmentList;
    List<String> titles;
    private WxPresenter presenter;
    private WxFragmentAdapter adapter;
    public WxFragment() {
        // Required empty public constructor
    }

    public static WxFragment getInstance() {
        return new WxFragment();
    }



    @Override
    public int getLayoutResID() {
        return R.layout.fragment_wx;
    }

    @Override
    protected void initData() {
        presenter = new WxPresenter(this);
        fragmentList = new ArrayList<>();
        wxBeanList = new ArrayList<>();
        titles = new LinkedList<>();
        adapter = new WxFragmentAdapter(getChildFragmentManager(),fragmentList);
        showLoading();
        presenter.getWxTitleList();
    }


    @Override
    public void getWxResultOK(List<WxListBean> demoBeans) {
        wxBeanList.clear();
        titles.clear();
        fragmentList.clear();
        wxBeanList.addAll(demoBeans);
        if(wxBeanList.size()>0){
            for(WxListBean bean : wxBeanList){
                fragmentList.add(WxDetailFragment.getInstance(bean.getId()));
                titles.add(bean.getName());
            }
            wxViewPager.setAdapter(adapter);
            wxTabLayout.setViewPager(wxViewPager, titles.toArray(new String[titles.size()]));
            adapter.notifyDataSetChanged();
        }
        showNormal();
    }

    @Override
    public void getWxResultErr(String info) {
        showError(info);
    }

    @Override
    public void reload() {
        super.reload();
        presenter.getWxTitleList();
    }

    /**
     * 查找 子 fragment 回到顶部
     */
    public void scrollChildToTop(){
        WxDetailFragment currentFragment = adapter.getCurrentFragment();
        currentFragment.scrollToTop();
    }
}
