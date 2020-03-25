package com.crazy.gy.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.gy.R;
import com.crazy.gy.adapter.HomeListAdapter;
import com.crazy.gy.entity.BannerListBean;
import com.crazy.gy.entity.HomeListBean;
import com.crazy.gy.net.RxCallback.OnResultClick;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxRequest.ApiServerImp;
import com.crazy.gy.ui.WebViewActivity;
import com.crazy.gy.util.ALog;
import com.crazy.gy.util.Sharedpreferences_Utils;
import com.crazy.gy.view.DialogText;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    @Bind(R.id.tv_titlecontent)
    TextView tvTitlecontent;
    @Bind(R.id.homerecyclerview)
    RecyclerView homerecyclerview;
    @Bind(R.id.refreshdata)
    SwipeRefreshLayout swipeRefreshData;
    private Handler handler = new Handler();
    private LinearLayoutManager linearLayoutManager;
    private HomeListAdapter listAdapter;
    private View bannerView;
    private Banner mBanner;
    private ApiServerImp mApiServerImp;
    private DialogText mDialog;
    private ArrayList<BannerListBean> bannerList;
    private ArrayList<HomeListBean.HomeListDetail> beanList;
    private ArrayList<String> pathList;
    private ArrayList<String> titleList;
    private int PAGE_SIZE = 15;
    private int page = 0;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView();
        initBannerData();
        initData();
        initAdapter();
        initItemClick();
        return view;
    }


    private void initView() {
        tvTitlecontent.setText("首页");
        mApiServerImp = new ApiServerImp();
        mDialog = new DialogText(getActivity(), R.style.MyDialog);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        homerecyclerview.setLayoutManager(linearLayoutManager);
        bannerView = LayoutInflater.from(getActivity()).inflate(R.layout.bannerview, null);
        mBanner = bannerView.findViewById(R.id.banner);
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


    private void initBannerData() {
        mApiServerImp.BannerListImp(new OnResultClick<BaseHttpBean>() {
            @Override
            public void success(BaseHttpBean baseHttpBean) {
                if (baseHttpBean != null) {
                    if (baseHttpBean.getErrorCode() == 0) {
                        bannerList = (ArrayList<BannerListBean>) baseHttpBean.getData();
                        if (bannerList.size() > 0) {

                            pathList = new ArrayList<>();
                            titleList = new ArrayList<>();

                            for (BannerListBean info : bannerList) {
                                if (!TextUtils.isEmpty(info.getImagePath())) {
                                    pathList.add(info.getImagePath());
                                } else {
                                    pathList.add("");
                                }

                                if (!TextUtils.isEmpty(info.getTitle())) {
                                    titleList.add(info.getTitle());
                                } else {
                                    titleList.add("");
                                }
                            }

                            if (pathList.size() > 0) {
                                setBanner(pathList, titleList);
                            }
                        }
                    }
                }
            }

            @Override
            public void fail(Throwable throwable) {

            }
        });
    }

    private void setBanner(final ArrayList<String> pathList, final ArrayList<String> titleList) {
        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR)
                .setBannerTitles(titleList)
                .setDelayTime(2000)
                .setImages(pathList).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load((String) path).into(imageView);
            }

        }).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //跳转到相应的网页
//                Intent intent = new Intent();
//                intent.setData(Uri.parse(bannerList.get(position).getUrl()));
//                intent.setAction(Intent.ACTION_VIEW);
//                startActivity(intent);
                //为了学习九宫格的图片展示，这里用来展示九宫格图片 新建一个activity
//                Intent intent = new Intent(getActivity(), ShowNineGrideActivity.class);
//                intent.putStringArrayListExtra("pathList", pathList);
//                startActivity(intent);

            }
        }).start();
    }


    private void initData() {
        Log.e(TAG, "initData: ");
        mApiServerImp.HomeListImp(page, new OnResultClick<BaseHttpBean>() {
            @Override
            public void success(BaseHttpBean baseHttpBean) {
                if (swipeRefreshData.isRefreshing()) {
                    swipeRefreshData.setRefreshing(false);
                }
                if (baseHttpBean != null) {
                    if (baseHttpBean.getErrorCode() == 0) {
                        Log.d("数据：", baseHttpBean.toString());
                        HomeListBean homeList = (HomeListBean) baseHttpBean.getData();
                        beanList = (ArrayList<HomeListBean.HomeListDetail>) homeList.getDatas();
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

    private void setData(final boolean isFresh, final List<HomeListBean.HomeListDetail> mData) {
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
        listAdapter = new HomeListAdapter();
        listAdapter.addHeaderView(bannerView);
        listAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", beanList.get(position).getLink());
                startActivity(intent);
            }
        });
        homerecyclerview.setAdapter(listAdapter);
    }

    private void initItemClick() {
        listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.ivCollect:

                        //判断有没有登录
                        int loginId = Sharedpreferences_Utils.getInstance().getInt("userId");
                        String val = loginId + "";
                        if (TextUtils.isEmpty(val)) {

                            mDialog.show();
                            showInfo("未登录", 0);


                        } else {

                            //判断当前有没有收藏


                            if (beanList.get(position).isCollect()) {


                                //取消收藏
                                mApiServerImp.RemoveCollectArticle(beanList.get(position).getId(), loginId, new OnResultClick<BaseHttpBean>() {
                                    @Override
                                    public void success(BaseHttpBean baseHttpBean) {
                                        if (baseHttpBean != null) {
                                            if (baseHttpBean.getErrorCode() == 0) {

                                                beanList.get(position).setCollect(!beanList.get(position).isCollect());
                                                listAdapter.notifyDataSetChanged();
                                            } else {
                                                mDialog.show();
                                                showInfo(baseHttpBean.getErrorMsg(), 0);
                                            }
                                        }
                                    }

                                    @Override
                                    public void fail(Throwable throwable) {
                                        mDialog.show();
                                        showInfo(throwable.getMessage(), 0);
                                    }
                                });
                            } else {

                                //收藏
                                mApiServerImp.CollectArticle(beanList.get(position).getId(), new OnResultClick<BaseHttpBean>() {
                                    @Override
                                    public void success(BaseHttpBean baseHttpBean) {
                                        if (baseHttpBean != null) {
                                            if (baseHttpBean.getErrorCode() == 0) {
                                                beanList.get(position).setCollect(!beanList.get(position).isCollect());
                                                listAdapter.notifyDataSetChanged();
                                            } else {
                                                mDialog.show();
                                                showInfo(baseHttpBean.getErrorMsg(), 0);
                                            }
                                        }
                                    }

                                    @Override
                                    public void fail(Throwable throwable) {
                                        mDialog.show();
                                        showInfo(throwable.getMessage(), 0);
                                    }
                                });
                            }

                        }
                        break;
                    case R.id.img_authorheadpic:
                        break;
                }
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 加载更多
     */
    private void loadMore() {
        page++;
        mApiServerImp.HomeListImp(page, new OnResultClick<BaseHttpBean>() {
            @Override
            public void success(BaseHttpBean baseHttpBean) {
                if (baseHttpBean != null) {
                    if (baseHttpBean.getErrorCode() == 0) {
                        HomeListBean articleList = (HomeListBean) baseHttpBean.getData();
                        List LoadbeanList = (ArrayList<HomeListBean.HomeListDetail>) articleList.getDatas();
                        setData(false, LoadbeanList);
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
}
