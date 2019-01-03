package com.crazy.gy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.gy.R;
import com.crazy.gy.adapter.MyCollectArticleAdapter;
import com.crazy.gy.entity.ArticleBean;
import com.crazy.gy.net.RxCallback.OnResultClick;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxRequest.ApiServerImp;
import com.crazy.gy.util.ALog;
import com.crazy.gy.view.DialogText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyCollectionsActivity extends AppCompatActivity {

    @Bind(R.id.img_titleleft)
    ImageView imgTitleleft;
    @Bind(R.id.tv_titlecontent)
    TextView tvTitlecontent;
    @Bind(R.id.collectsrecyclerview)
    RecyclerView collectsrecyclerview;
    @Bind(R.id.refreshdata)
    SwipeRefreshLayout swipeRefreshData;
    private ApiServerImp mApiServerImp;
    private DialogText mDialog;
    private Context mContext;
    private LinearLayoutManager linearLayoutManager;
    private int PAGE_SIZE = 15;
    private int page = 0;
    private ArrayList<ArticleBean.DatasBean> beanList;
    private MyCollectArticleAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collections);
        ButterKnife.bind(this);
        mContext = MyCollectionsActivity.this;
        initView();
        initData();
        initAdapter();
        initEvent();
    }


    private void initView() {
        mApiServerImp = new ApiServerImp();
        mDialog = new DialogText(mContext, R.style.MyDialog);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        collectsrecyclerview.setLayoutManager(linearLayoutManager);
        imgTitleleft.setVisibility(View.VISIBLE);
        imgTitleleft.setImageResource(R.drawable.ic_left);
        tvTitlecontent.setText("我的收藏");

        //设置加载进度的颜色变化值
        swipeRefreshData.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
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
        mApiServerImp.CollectArticleList(0, new OnResultClick<BaseHttpBean>() {
            @Override
            public void success(BaseHttpBean baseHttpBean) {
                if (swipeRefreshData.isRefreshing()) {
                    swipeRefreshData.setRefreshing(false);
                }
                if (baseHttpBean != null) {
                    if (baseHttpBean.getErrorCode() == 0) {
                        ArticleBean homeList = (ArticleBean) baseHttpBean.getData();
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
                } else {
                    mDialog.show();
                    showInfo(throwable.getMessage(), 0);
                }
            }
        });

    }

    private void initAdapter() {
        listAdapter = new MyCollectArticleAdapter();
        listAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MyCollectionsActivity.this, WebViewActivity.class);
                intent.putExtra("url", beanList.get(position).getLink());
                startActivity(intent);
            }
        });
        collectsrecyclerview.setAdapter(listAdapter);
    }

    private void loadMore() {
        page++;
        mApiServerImp.CollectArticleList(page, new OnResultClick<BaseHttpBean>() {
            @Override
            public void success(BaseHttpBean baseHttpBean) {
                if (baseHttpBean != null) {
                    if (baseHttpBean.getErrorCode() == 0) {
                        ArticleBean articleList = (ArticleBean) baseHttpBean.getData();
                        List beanLists = (ArrayList<ArticleBean.DatasBean>) articleList.getDatas();
                        setData(false, beanLists);
                    } else {
                        mDialog.show();
                        showInfo(baseHttpBean.getErrorMsg(), 0);
                    }
                }
            }

            @Override
            public void fail(Throwable throwable) {
                ALog.e(throwable.getMessage());
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

    private void initEvent() {
        imgTitleleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCollectionsActivity.this.finish();
            }
        });
    }
}
