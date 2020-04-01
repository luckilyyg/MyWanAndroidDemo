package com.crazy.gy.mvp.presenter;


import com.crazy.gy.entity.HomeListBean;
import com.crazy.gy.entity.HotKeyBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.SearechDetailContract;
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
 * 搜索详情界面
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.home.search.searechdetail
 * @fileName: SearechDetailPresenter
 * @date: 2018/8/28  13:14
 * @author: ymc
 * @QQ:745612618
 */

public class SearechDetailPresenter extends BasePresenter<SearechDetailContract.View> implements SearechDetailContract.Presenter {

    private SearechDetailContract.View view;

    private int currentPage;

    private boolean hasRefresh = true;

    public SearechDetailPresenter(SearechDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void autoRefresh(String key) {
        hasRefresh = true;
        currentPage  = 0;
        getSearechResult(currentPage,key);
    }

    @Override
    public void loadMore(String key) {
        hasRefresh = false;
        currentPage ++;
        getSearechResult(currentPage,key);
    }

    @Override
    public void getSearechResult(int page, String key) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getSearechResults(page,key), new Consumer<BaseHttpBean<HomeListBean>>() {
            @Override
            public void accept(BaseHttpBean listBaseResp) throws Exception {
                if (listBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                    view.getSearechResultErr(listBaseResp.getErrorMsg());
                } else if (listBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                    view.getSearechResultOk((HomeListBean) listBaseResp.getData(), hasRefresh);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.getSearechResultErr(throwable.getMessage());
            }
        });

    }

}
