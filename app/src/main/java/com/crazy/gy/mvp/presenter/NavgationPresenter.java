package com.crazy.gy.mvp.presenter;

import com.crazy.gy.entity.BannerListBean;
import com.crazy.gy.entity.NavigationListBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.HomeContract;
import com.crazy.gy.mvp.contract.NavgationContract;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxHttp.RxHttp;
import com.crazy.gy.net.RxHttp.RxRetrofit;
import com.crazy.gy.net.RxRequest.ApiServer;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created on 2020/4/16 11:08
 *
 * @auther super果
 * @annotation
 */
public class NavgationPresenter extends BasePresenter<NavgationContract.View> implements NavgationContract.Per {

    private NavgationContract.View view;

    public NavgationPresenter(NavgationContract.View view) {
        this.view = view;
    }

    @Override
    public void attachView(NavgationContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void getNavgationListData() {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getNavigationList(), new Consumer<BaseHttpBean<List<NavigationListBean>>>() {
            @Override
            public void accept(BaseHttpBean baseHttpBean) throws Exception {
                if (baseHttpBean.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                    view.getNavgationErr(baseHttpBean.getErrorMsg());
                } else if (baseHttpBean.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                    view.getNavgationOk((List<NavigationListBean>) baseHttpBean.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.getNavgationErr(throwable.getMessage());
            }
        });
    }


}
