package com.crazy.gy.mvp.presenter;


import com.crazy.gy.entity.KnowledgeDetailListBean;
import com.crazy.gy.entity.KnowledgeListBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.SystemDetailListContract;
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
 * 体系 二级 列表 presenter 层
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.system.systemdetail.SystemDetailList
 * @fileName: SystemDetailListPresenter
 * @date: 2018/8/16  10:00
 * @author: ymc
 * @QQ:745612618
 */

public class SystemDetailListPresenter extends BasePresenter<SystemDetailListContract.View> implements SystemDetailListContract.Presenter{

    private SystemDetailListContract.View view;
    private int currentPage;
    private int id = -1;
    private boolean isRefresh = true;

    public SystemDetailListPresenter(SystemDetailListContract.View view) {
        this.view = view;
    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        if(id!=-1){
            currentPage = 0;
            getSystemDetailList(currentPage,id);
        }
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        if(id!=-1) {
            currentPage++;
            getSystemDetailList(currentPage, id);
        }
    }

    @Override
    public void getSystemDetailList(int page, int id) {
        this.id = id;
        this.currentPage = page;

        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getSystemDetailList(page,id), new Consumer<BaseHttpBean<KnowledgeDetailListBean>>() {
            @Override
            public void accept(BaseHttpBean systemDetailListBeanBaseResp) throws Exception {
                if(systemDetailListBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR){
                    view.getSystemDetailListResultErr(systemDetailListBeanBaseResp.getErrorMsg());
                }else if(systemDetailListBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS){
                    view.getSystemDetailListResultOK((KnowledgeDetailListBean) systemDetailListBeanBaseResp.getData(),isRefresh);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.getSystemDetailListResultErr(throwable.getMessage());
            }
        });

    }
}
