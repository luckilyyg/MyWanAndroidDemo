package com.crazy.gy.mvp.presenter;

import com.crazy.gy.entity.KnowledgeListBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.SystemContract;
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
 * 体系 presenter 层
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.fragment.system
 * @fileName: SystemPresenter
 * @date: 2018/8/13  13:52
 * @author: ymc
 * @QQ:745612618
 */

public class SystemPresenter extends BasePresenter<SystemContract.View> implements SystemContract.Presenter {

    private SystemContract.View view;

    public SystemPresenter(SystemContract.View view) {
        this.view = view;
    }

    @Override
    public void autoRefresh() {
        getSystemList();
    }

    @Override
    public void getSystemList() {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getKnowLedgeList(), new Consumer<BaseHttpBean<List<KnowledgeListBean>>>() {
            @Override
            public void accept(BaseHttpBean listBaseResp) throws Exception {
                if (listBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                    view.getSystemListOk((List<KnowledgeListBean>) listBaseResp.getData());
                } else if (listBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                    view.getSystemListErr(listBaseResp.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.getSystemListErr(throwable.getMessage());
            }
        });
    }

}
