package com.crazy.gy.mvp.presenter;

import com.crazy.gy.entity.DemoTitleBean;
import com.crazy.gy.entity.WxListBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.DemoContract;
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
 * 项目 presenter 层
 *
 * Created by yangmingchuan on 2018/8/16.
 * Email:18768880074@163.com
 * cn.white.ymc.wanandroidmaster.ui.demo
 */

public class DemoPresenter extends BasePresenter<DemoContract.View> implements DemoContract.Presenter {

    private DemoContract.View view;

    public DemoPresenter(DemoContract.View view) {
        this.view = view;
    }

    @Override
    public void getDemoTitleList() {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getDemoTitleList(), new Consumer<BaseHttpBean<List<DemoTitleBean>>>() {
            @Override
            public void accept(BaseHttpBean listBaseResp) throws Exception {
                if(listBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS){
                    view.getDemoResultOK((List<DemoTitleBean>) listBaseResp.getData());
                }else if(listBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR){
                    view.getDemoResultErr(listBaseResp.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.getDemoResultErr(throwable.getMessage());
            }
        });
    }
}
