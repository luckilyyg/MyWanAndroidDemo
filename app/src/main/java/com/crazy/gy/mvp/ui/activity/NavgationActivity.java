package com.crazy.gy.mvp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.gy.R;
import com.crazy.gy.adapter.NavigationAdapter;
import com.crazy.gy.entity.NavigationListBean;
import com.crazy.gy.mvp.base.BaseResultActivity;
import com.crazy.gy.mvp.contract.NavgationContract;
import com.crazy.gy.mvp.presenter.NavgationPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class NavgationActivity extends BaseResultActivity implements NavgationContract.View, NavigationAdapter.OnItemClickListener {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.navigation_tab_layout)
    VerticalTabLayout mTabLayout;
    @Bind(R.id.navigation_RecyclerView)
    RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private NavigationAdapter mNavigationAdapter;
    private NavgationPresenter presenter;
    private boolean needScroll;
    private int index;
    private boolean isClickTab;
    private List<NavigationListBean> navigationDataList;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_navgation;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.navgation));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();


        mManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setHasFixedSize(true);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (needScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    scrollRecyclerView();
                }
                rightLinkageLeft(newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (needScroll) {
                    scrollRecyclerView();
                }
            }
        });

        mTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tabView, int i) {
                isClickTab = true;
                selectTag(i);
            }

            @Override
            public void onTabReselected(TabView tabView, int i) {
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();

        navigationDataList = new ArrayList<>();
        mNavigationAdapter = new NavigationAdapter(R.layout.navgation_item, navigationDataList);
        mRecyclerView.setAdapter(mNavigationAdapter);
        mNavigationAdapter.setOnItemClickListener(this);
        presenter = new NavgationPresenter(this);
        presenter.getNavgationListData();
    }
    @Override
    public void getNavgationOk(List<NavigationListBean> navigationListBean) {
        showNormal();
        updateUI(navigationListBean);

    }

    private void updateUI(final List<NavigationListBean> navigationListBean) {
        mTabLayout.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return navigationListBean == null ? 0 : navigationListBean.size();
            }

            @Override
            public ITabView.TabBadge getBadge(int i) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int i) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int i) {
                return new TabView.TabTitle.Builder()
                        .setContent(navigationListBean.get(i).getName())
                        .setTextColor(getResources().getColor(R.color.shallow_green), getResources().getColor(R.color.shallow_grey))
                        .build();
            }

            @Override
            public int getBackground(int i) {
                return -1;
            }
        });
        mNavigationAdapter.replaceData(navigationListBean);
    }

    @Override
    public void getNavgationErr(String err) {
        showError(err);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }


    /**
     * Right recyclerView linkage left tabLayout
     * SCROLL_STATE_IDLE just call once
     *
     * @param newState RecyclerView new scroll state
     */
    private void rightLinkageLeft(int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (isClickTab) {
                isClickTab = false;
                return;
            }
            int firstPosition = mManager.findFirstVisibleItemPosition();
            if (index != firstPosition) {
                index = firstPosition;
                setChecked(index);
            }
        }
    }

    private void selectTag(int i) {
        index = i;
        mRecyclerView.stopScroll();
        smoothScrollToPosition(i);
    }


    private void smoothScrollToPosition(int currentPosition) {
        int firstPosition = mManager.findFirstVisibleItemPosition();
        int lastPosition = mManager.findLastVisibleItemPosition();
        if (currentPosition <= firstPosition) {
            mRecyclerView.smoothScrollToPosition(currentPosition);
        } else if (currentPosition <= lastPosition) {
            int top = mRecyclerView.getChildAt(currentPosition - firstPosition).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        } else {
            mRecyclerView.smoothScrollToPosition(currentPosition);
            needScroll = true;
        }
    }

    /**
     * Smooth right to select the position of the left tab
     *
     * @param position checked position
     */
    private void setChecked(int position) {
        if (isClickTab) {
            isClickTab = false;
        } else {
            if (mTabLayout == null) {
                return;
            }
            mTabLayout.setTabSelected(index);
        }
        index = position;
    }

    private void scrollRecyclerView() {
        needScroll = false;
        int indexDistance = index - mManager.findFirstVisibleItemPosition();
        if (indexDistance >= 0 && indexDistance < mRecyclerView.getChildCount()) {
            int top = mRecyclerView.getChildAt(indexDistance).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        }
    }


}
