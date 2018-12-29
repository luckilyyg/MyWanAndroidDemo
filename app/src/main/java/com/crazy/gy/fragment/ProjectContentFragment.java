package com.crazy.gy.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazy.gy.R;
import com.crazy.gy.adapter.ProjectListAdapter;
import com.crazy.gy.adapter.ProjectPagerAdapter;
import com.crazy.gy.entity.HomeListBean;
import com.crazy.gy.entity.KnowledgeListBean;
import com.crazy.gy.entity.ProjectContentListBean;
import com.crazy.gy.entity.ProjectListBean;
import com.crazy.gy.net.RxCallback.OnResultClick;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxRequest.ApiServerImp;
import com.crazy.gy.view.DialogText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectContentFragment extends Fragment {
    private static final String ARTICLEID = "articleId";
    @Bind(R.id.projectrecyclerview)
    RecyclerView projectrecyclerview;
    @Bind(R.id.refreshdata)
    SwipeRefreshLayout swipeRefreshData;
    private ApiServerImp mApiServerImp;
    private DialogText mDialog;
    private LinearLayoutManager linearLayoutManager;
    private int articleId;
    private ArrayList<ProjectContentListBean.DatasBean> beanList;
    private int PAGE_SIZE = 15;
    private int page = 0;
    private ProjectListAdapter listAdapter;
    private Handler handler = new Handler();

    public ProjectContentFragment() {
        // Required empty public constructor
    }

    public static ProjectContentFragment newInstance(int id) {
        Bundle args = new Bundle();
        ProjectContentFragment fragment = new ProjectContentFragment();
        fragment.setArguments(args);
        args.putInt(ARTICLEID, id);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_content, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        initAdapter();
        return view;
    }

    private void initView() {
        mApiServerImp = new ApiServerImp();
        mDialog = new DialogText(getActivity(), R.style.MyDialog);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        projectrecyclerview.setLayoutManager(linearLayoutManager);
        //设置加载进度的颜色变化值
        swipeRefreshData.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        articleId = getArguments().getInt(ARTICLEID);
        //设置下拉刷新的监听器
        swipeRefreshData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        beanList.clear();
                        initData();
                    }
                }, 2000);
            }
        });
    }

    private void initData() {
        mApiServerImp.ProjectContentListImp(1, articleId, new OnResultClick<BaseHttpBean>() {
            @Override
            public void success(BaseHttpBean baseHttpBean) {

                if (swipeRefreshData.isRefreshing()) {
                    swipeRefreshData.setRefreshing(false);
                }
                if (baseHttpBean != null) {
                    if (baseHttpBean.getErrorCode() == 0) {
                        ProjectContentListBean projectContentList = (ProjectContentListBean) baseHttpBean.getData();
                        beanList = (ArrayList<ProjectContentListBean.DatasBean>) projectContentList.getDatas();
                        setData(true, beanList);
                    } else {
                        mDialog.show();
                        showInfo(baseHttpBean.getErrorMsg(), 0);
                    }
                }
            }

            @Override
            public void fail(Throwable throwable) {
                if (swipeRefreshData.isRefreshing()) {
                    swipeRefreshData.setRefreshing(false);
                }
                mDialog.show();
                showInfo(throwable.getMessage(), 0);
            }
        });

    }

    private void initAdapter() {
        listAdapter = new ProjectListAdapter();
        projectrecyclerview.setAdapter(listAdapter);
    }

    private void setData(final boolean isFresh, final List<ProjectContentListBean.DatasBean> mData) {
        final int size = mData == null ? 0 : mData.size();
        if (isFresh) {
            listAdapter.setNewData(mData);

            if (size < PAGE_SIZE) {
                //第一页如果不够一页就不显示没有更多数据布局
                listAdapter.loadMoreEnd(isFresh);

            } else {
                listAdapter.loadMoreComplete();
            }
        } else {
            if (size > 0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (size < PAGE_SIZE) {
                            //第一页如果不够一页就不显示没有更多数据布局(比如只有十三条数据)
                            listAdapter.loadMoreEnd(isFresh);

                        } else {
                            listAdapter.loadMoreComplete();
                        }
                        listAdapter.addData(mData);
                    }
                }, 1000);

            }

        }


    }

    /**
     * @param errorString
     * @param is
     */
    public void showInfo(final String errorString, final int is) {
        String isString = "";
        if (is == 1) {
            isString = "success";
        }
        if (is == 0) {
            isString = "fail";
        }
        if (mDialog != null) {
            mDialog.setText(errorString, isString);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();
                }
            }, 1000);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
