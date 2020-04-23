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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.gy.R;
import com.crazy.gy.entity.BannerListBean;
import com.crazy.gy.entity.HomeListBean;
import com.crazy.gy.entity.UserBean;
import com.crazy.gy.mvc.model.HomeModelImpl;
import com.crazy.gy.mvc.model.OnHomeListener;
import com.crazy.gy.mvp.base.BaseFragment;
import com.crazy.gy.mvp.contract.HomeContract;
import com.crazy.gy.mvp.presenter.HomePagePresenter;
import com.crazy.gy.mvp.ui.activity.HomeDetailActivity;
import com.crazy.gy.mvp.ui.adapter.HomePageAdapter;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.crazy.gy.mvp.util.GlideImageLoader;
import com.crazy.gy.mvp.util.JumpUtil;
import com.crazy.gy.mvp.util.toast.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class mHomeToFragment extends BaseFragment implements OnHomeListener, HomePageAdapter.OnItemClickListener, HomePageAdapter.OnItemChildClickListener {
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.normal_view)
    SmartRefreshLayout normalView;
    private Banner banner;
    private LinearLayout bannerView;
    //    private HomePagePresenter presenter;
    private List<HomeListBean.HomeListDetail> articleList;
    private HomePageAdapter mAdapter;
    private List<String> linkList;
    private List<String> imageList;
    private List<String> titleList;
    HomeModelImpl homeModel;

   private int page = 0;

    public mHomeToFragment() {
        // Required empty public constructor
    }

    public static mHomeToFragment getInstance() {
        return new mHomeToFragment();
    }


    @Override
    public int getLayoutResID() {
        return R.layout.fragment_m_home;
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        rv.setLayoutManager(new LinearLayoutManager(activity));
        bannerView = (LinearLayout) getLayoutInflater().inflate(R.layout.view_banner, null);
        banner = bannerView.findViewById(R.id.banner);
        bannerView.removeView(banner);
        bannerView.addView(banner);
    }

    @Override
    protected void initData() {
        setRefresh();
        articleList = new ArrayList<>();
        linkList = new ArrayList<>();
        imageList = new ArrayList<>();
        titleList = new ArrayList<>();
        homeModel = new HomeModelImpl();
//        presenter = new HomePagePresenter(this);
//        presenter.getBanner();
//        presenter.getHomepageListData(0);
        homeModel.getHomeList(page, true, this);


        mAdapter = new HomePageAdapter(R.layout.item_homepage, articleList);
        mAdapter.addHeaderView(bannerView);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        rv.setAdapter(mAdapter);
    }

    /**
     * 刷新加载
     */
    private void setRefresh() {
        normalView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                refresh(refreshLayout);
            }
        });
        normalView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                load(refreshLayout);
            }
        });
    }

    private void load(RefreshLayout refreshLayout) {
        page++;
        homeModel.getHomeList(page, false, this);
        refreshLayout.finishLoadMore(1000);
    }

    private void refresh(RefreshLayout refreshLayout) {
        homeModel.getHomeList(page, true, this);
        refreshLayout.finishRefresh(1000);
    }

    public void scrollToTop() {
        rv.smoothScrollToPosition(0);
    }

//    @Override
//    public void getHomepageListOk(HomeListBean dataBean, boolean isRefresh) {
//        showNormal();
//        // 是 刷新adapter 则 添加数据到adapter
//        if (isRefresh) {
//            articleList = dataBean.getDatas();
//            mAdapter.replaceData(articleList);
//        } else {
//            articleList.addAll(dataBean.getDatas());
//            mAdapter.addData(dataBean.getDatas());
//        }
//
//    }
//
//    @Override
//    public void getHomepageListErr(String info) {
//        showError(info);
//    }
//
//    @Override
//    public void getBannerOk(List<BannerListBean> bannerBean) {
//        imageList.clear();
//        titleList.clear();
//        linkList.clear();
//        for (BannerListBean benarBean : bannerBean) {
//            imageList.add(benarBean.getImagePath());
//            titleList.add(benarBean.getTitle());
//            linkList.add(benarBean.getUrl());
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (!activity.isDestroyed()) {
//                // banner git 地址 https://github.com/youth5201314/banner
//                banner.setImageLoader(new GlideImageLoader())
//                        .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
//                        .setImages(imageList)
//                        .setBannerAnimation(Transformer.Accordion)
//                        .setBannerTitles(titleList)
//                        .isAutoPlay(true)
//                        .setDelayTime(5000)
//                        .setIndicatorGravity(BannerConfig.RIGHT)
//                        .start();
//            }
//        }
//        banner.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(int position) {
//                if (!TextUtils.isEmpty(linkList.get(position))) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString(ConstantUtil.HOME_DETAIL_TITLE, titleList.get(position));
//                    bundle.putString(ConstantUtil.HOME_DETAIL_PATH, linkList.get(position));
//                    JumpUtil.overlay(getActivity(), HomeDetailActivity.class, bundle);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void getBannerErr(String info) {
//
//    }
//
//    /**
//     * 登录成功
//     */
//    @Override
//    public void loginOk(UserBean userInfo) {
//        ToastUtil.show(activity, getString(R.string.auto_login_ok));
//
//    }
//
//    /**
//     * 登录失败
//     */
//    @Override
//    public void loginErr(String err) {
//        ToastUtil.show(activity, err);
//    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        HomeListBean.HomeListDetail bean = mAdapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.HOME_DETAIL_ID, bean.getId());
        bundle.putString(ConstantUtil.HOME_DETAIL_PATH, bean.getLink());
        bundle.putString(ConstantUtil.HOME_DETAIL_TITLE, bean.getTitle());
        bundle.putBoolean(ConstantUtil.HOME_DETAIL_IS_COLLECT, bean.isCollect());
        // webview 和跳转的界面布局 transitionName 一定要相同
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.web_view));
        startActivity(new Intent(activity, HomeDetailActivity.class).putExtras(bundle), options.toBundle());
    }


    @Override
    public void getHomepageListOk(HomeListBean dataBean, boolean isRefresh) {
        Log.e(TAG, "getHomepageListOk: ");
        showNormal();
        if (isRefresh) {
            articleList = dataBean.getDatas();
            mAdapter.replaceData(articleList);
        } else {
            articleList.addAll(dataBean.getDatas());
            mAdapter.addData(dataBean.getDatas());
        }
    }

    @Override
    public void getHomepageListErr(String info) {
        Log.e(TAG, "getHomepageListErr: ");
        showError(info);
    }
}
