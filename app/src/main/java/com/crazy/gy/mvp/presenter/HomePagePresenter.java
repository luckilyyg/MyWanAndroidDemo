package com.crazy.gy.mvp.presenter;

import com.crazy.gy.entity.BannerListBean;
import com.crazy.gy.entity.HomeListBean;
import com.crazy.gy.entity.UserBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.HomeContract;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxHttp.RxHttp;
import com.crazy.gy.net.RxHttp.RxRetrofit;
import com.crazy.gy.net.RxRequest.ApiServer;

import java.util.List;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页 presenter 层
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.home
 * @fileName: HomePagePresenter
 * @date: 2018/8/6  16:41
 * @author: ymc
 * @QQ:745612618
 */

public class HomePagePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Per {

    private HomeContract.View view;
    private boolean isRefresh = true;
    private int currentPage;

    public HomePagePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void attachView(HomeContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        currentPage = 0;
        getBanner();
        getHomepageListData(currentPage);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getHomepageListData(currentPage);
    }

    /**
     * 获取 banner 信息
     */
    @Override
    public void getBanner() {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getBannerList(), new Consumer<BaseHttpBean<List<BannerListBean>>>() {
            @Override
            public void accept(BaseHttpBean baseHttpBean) throws Exception {
                if (baseHttpBean.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                    view.getBannerErr(baseHttpBean.getErrorMsg());
                } else if (baseHttpBean.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                    view.getBannerOk((List<BannerListBean>) baseHttpBean.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.getBannerErr(throwable.getMessage());
            }
        });
    }

    /**
     * 获取首页 信息
     *
     * @param page
     */
    @Override
    public void getHomepageListData(int page) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getHomeList(page), new Consumer<BaseHttpBean<HomeListBean>>() {
            @Override
            public void accept(BaseHttpBean homePageArticleBeanBaseResp) throws Exception {
                if (homePageArticleBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                    view.getHomepageListErr(homePageArticleBeanBaseResp.getErrorMsg());
                } else if (homePageArticleBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                    view.getHomepageListOk((HomeListBean) homePageArticleBeanBaseResp.getData(), isRefresh);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.getHomepageListErr(throwable.getMessage());
            }
        });
    }

    @Override
    public void loginUser(String username, String password) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getLogin(username, password), new Consumer<BaseHttpBean<UserBean>>() {
            @Override
            public void accept(BaseHttpBean baseResp) throws Exception {
                if (baseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                    view.loginOk((UserBean) baseResp.getData());
                } else if (baseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                    view.loginErr(baseResp.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.loginErr(throwable.getMessage());
            }
        });

    }




}
