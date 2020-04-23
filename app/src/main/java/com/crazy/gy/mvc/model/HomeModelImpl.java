package com.crazy.gy.mvc.model;

import com.crazy.gy.entity.HomeListBean;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxHttp.RxHttp;
import com.crazy.gy.net.RxHttp.RxRetrofit;
import com.crazy.gy.net.RxRequest.ApiServer;

import io.reactivex.functions.Consumer;

/**
 * Created on 2020/4/22 17:36
 *
 * @auther super果
 * @annotation
 */
public class HomeModelImpl implements HomeModel {
//    private boolean isRefresh = true;




    @Override
    public void getHomeList(final int page,final boolean isRefresh,final OnHomeListener mlistener) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getHomeList(page), new Consumer<BaseHttpBean<HomeListBean>>() {
            @Override
            public void accept(BaseHttpBean homePageArticleBeanBaseResp) throws Exception {
                if (homePageArticleBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                    mlistener.getHomepageListErr(homePageArticleBeanBaseResp.getErrorMsg());
                } else if (homePageArticleBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                    if (isRefresh) {
                        mlistener.getHomepageListOk((HomeListBean) homePageArticleBeanBaseResp.getData(), isRefresh);
                    }else {
                        mlistener.getHomepageListOk((HomeListBean) homePageArticleBeanBaseResp.getData(), isRefresh);
                    }

                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                mlistener.getHomepageListErr(throwable.getMessage());
            }
        });
    }

}
