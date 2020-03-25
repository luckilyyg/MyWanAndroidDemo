package com.crazy.gy.fragment;


import android.content.Intent;
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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.gy.R;
import com.crazy.gy.adapter.KnowledgeListAdapter;
import com.crazy.gy.entity.HomeListBean;
import com.crazy.gy.entity.KnowledgeListBean;
import com.crazy.gy.net.RxCallback.OnResultClick;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxRequest.ApiServerImp;
import com.crazy.gy.ui.KnowledgeContentActivity;
import com.crazy.gy.util.ALog;
import com.crazy.gy.view.DialogText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class KnowledgeFragment extends Fragment {
    @Bind(R.id.tv_titlecontent)
    TextView tvTitlecontent;
    @Bind(R.id.knowledgerecyclerview)
    RecyclerView knowledgerecyclerview;
    @Bind(R.id.refreshdata)
    SwipeRefreshLayout swipeRefreshData;
    private ApiServerImp mApiServerImp;
    private DialogText mDialog;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<KnowledgeListBean> beanList = new ArrayList<>();
    private KnowledgeListAdapter listAdapter;
    private int PAGE_SIZE = 15;
    private int page = 0;
    private Handler handler = new Handler();
    private List<KnowledgeListBean> listBean;
    private static final String TAG = "KnowledgeFragment";
    public KnowledgeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_knowledge, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        initAdapter();
        initEvent();
        return view;
    }

    private void initEvent() {
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArrayList<String> names = new ArrayList<>();
                ArrayList<Integer> ids = new ArrayList<>();
                KnowledgeListBean knowledge = listBean.get(position);
                String knowledgename = knowledge.getName();
                for (KnowledgeListBean.Children children : knowledge.getChildren()) {
                    names.add(children.getName());
                    ids.add(children.getId());
                }
                Intent intent = new Intent(getActivity(), KnowledgeContentActivity.class);
                intent.putStringArrayListExtra("names", names);
                intent.putIntegerArrayListExtra("ids", ids);
                intent.putExtra("knowledgename", knowledgename);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        tvTitlecontent.setText("知识体系");
        mApiServerImp = new ApiServerImp();
        mDialog = new DialogText(getActivity(), R.style.MyDialog);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        knowledgerecyclerview.setLayoutManager(linearLayoutManager);
        //设置加载进度的颜色变化值
        swipeRefreshData.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
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
        Log.e(TAG, "initData: " );
        mApiServerImp.KnowLedgeListImp(new OnResultClick<BaseHttpBean>() {
            @Override
            public void success(BaseHttpBean baseHttpBean) {
                if (swipeRefreshData.isRefreshing()) {
                    swipeRefreshData.setRefreshing(false);
                }
                if (baseHttpBean != null) {
                    if (baseHttpBean.getErrorCode() == 0) {
                        listBean = (List<KnowledgeListBean>) baseHttpBean.getData();
                        setData(true, listBean);
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
                } else {
                    mDialog.show();
                    showInfo(throwable.getMessage(), 0);
                }
            }
        });
    }

    private void setData(final boolean isFresh, final List<KnowledgeListBean> mData) {
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

    private void initAdapter() {
        listAdapter = new KnowledgeListAdapter();
        knowledgerecyclerview.setAdapter(listAdapter);
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
