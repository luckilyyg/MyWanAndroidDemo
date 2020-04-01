package com.crazy.gy.mvp.presenter;


import com.crazy.gy.entity.CollectBean;
import com.crazy.gy.entity.DemoDetailListBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.CollectionListContract;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxHttp.RxHttp;
import com.crazy.gy.net.RxHttp.RxRetrofit;
import com.crazy.gy.net.RxRequest.ApiServer;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的收藏列表 presenter 层
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.mine.minelist
 * @fileName: CollectionListPresenter
 * @date: 2018/8/23  13:54
 * @author: ymc
 * @QQ:745612618
 */

public class CollectionListPresenter extends BasePresenter<CollectionListContract.View> implements CollectionListContract.Presenter{

    private CollectionListContract.View view;
    private int currentPage;
    private boolean hasRefresh = true;

    public CollectionListPresenter(CollectionListContract.View view) {
        this.view = view;
    }

    @Override
    public void onRefresh() {
        hasRefresh = true;
        getCollectionList(0);
    }

    @Override
    public void onLoadMore() {
        hasRefresh = false;
        currentPage ++;
        getCollectionList(currentPage);
    }

    @Override
    public void getCollectionList(int page) {
        this.currentPage = page;
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getCollectionList(page), new Consumer<BaseHttpBean<CollectBean>>() {
            @Override
            public void accept(BaseHttpBean listBaseResp) throws Exception {
                if(listBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS){
                    view.getCollectionListOK((CollectBean) listBaseResp.getData(),hasRefresh);
                }else if(listBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR){
                    view.getCollectionListErr(listBaseResp.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.getCollectionListErr(throwable.getMessage());
            }
        });
    }
}
