package com.crazy.gy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.gy.R;
import com.crazy.gy.adapter.MyCollectArticleAdapter;
import com.crazy.gy.entity.ArticleBean;
import com.crazy.gy.entity.ProjectContentListBean;
import com.crazy.gy.net.RxCallback.OnResultClick;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxRequest.ApiServerImp;
import com.crazy.gy.ui.MyCollectionsActivity;
import com.crazy.gy.ui.WebViewActivity;
import com.crazy.gy.view.DialogText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class KnowledgeContentFragment extends Fragment {

    @Bind(R.id.knowledgerecyclerview)
    RecyclerView knowledgerecyclerview;
    @Bind(R.id.refreshdata)
    SwipeRefreshLayout swipeRefreshData;

    private static final String ID = "Id";
    private ApiServerImp mApiServerImp;
    private DialogText mDialog;
    private LinearLayoutManager linearLayoutManager;
    private int cid;
    private ArticleBean homeList;
    private ArrayList<ArticleBean.DatasBean> beanList;
    private MyCollectArticleAdapter listAdapter;
    private int PAGE_SIZE = 15;
    private int page = 0;

    public KnowledgeContentFragment() {
        // Required empty public constructor
    }

    public static KnowledgeContentFragment newInstance(int id) {
        Bundle args = new Bundle();
        KnowledgeContentFragment fragment = new KnowledgeContentFragment();
        fragment.setArguments(args);
        args.putInt(ID, id);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_knowledge_content, container, false);
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
        knowledgerecyclerview.setLayoutManager(linearLayoutManager);
        //设置加载进度的颜色变化值
        swipeRefreshData.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        cid = getArguments().getInt(ID);

        //设置下拉刷新的监听器
        swipeRefreshData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
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
        mApiServerImp.KnowLedgeContentListImp(0, cid, new OnResultClick<BaseHttpBean>() {
            @Override
            public void success(BaseHttpBean baseHttpBean) {

                if (swipeRefreshData.isRefreshing()) {
                    swipeRefreshData.setRefreshing(false);
                }
                if (baseHttpBean != null) {
                    if (baseHttpBean.getErrorCode() == 0) {
                        homeList = (ArticleBean) baseHttpBean.getData();
                        beanList = (ArrayList<ArticleBean.DatasBean>) homeList.getDatas();
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

    private void setData(final boolean isFresh, final List<ArticleBean.DatasBean> mData) {
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

    private void initAdapter() {
        listAdapter = new MyCollectArticleAdapter();
        knowledgerecyclerview.setAdapter(listAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
