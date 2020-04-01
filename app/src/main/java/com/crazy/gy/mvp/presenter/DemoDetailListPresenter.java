package com.crazy.gy.mvp.presenter;


import com.crazy.gy.entity.DemoDetailListBean;
import com.crazy.gy.entity.DemoTitleBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.DemoDetailListContract;
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
 * 项目
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.demo.demolist
 * @fileName: DemoDetailListPresenter
 * @date: 2018/8/17  13:02
 * @author: ymc
 * @QQ:745612618
 */

public class DemoDetailListPresenter extends BasePresenter<DemoDetailListContract.View> implements
            DemoDetailListContract.Presenter{

    DemoDetailListContract.View view;

    private int id = -1;
    private int page;
    private boolean isRefresh = true;

    public DemoDetailListPresenter(DemoDetailListContract.View view) {
        this.view = view;
    }

    /**
     * 获取项目 详细信息列表
     * @param page
     * @param id
     */
    @Override
    public void getDemoList(int page, int id) {
        this.id = id;
        this.page = page;
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getDemoDetailList(page,id), new Consumer<BaseHttpBean<DemoDetailListBean>>() {
            @Override
            public void accept(BaseHttpBean listBaseResp) throws Exception {
                if(listBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR){
                    view.getDemoListErr(listBaseResp.getErrorMsg());
                }else if(listBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS){
                    view.getDemoListOK((DemoDetailListBean) listBaseResp.getData(),isRefresh);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.getDemoListErr(throwable.getMessage());
            }
        });

    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        page = 1;
        if(id!=-1){
            getDemoList(page,id);
        }
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        page++;
        if(id!=-1){
            getDemoList(page,id);
        }
    }
}
