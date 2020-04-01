package com.crazy.gy.mvp.presenter;

import com.crazy.gy.entity.KnowledgeListBean;
import com.crazy.gy.entity.WxListBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.WxContract;
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
 * 微信公众号 presenter
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.wx
 * @fileName: WxPresenter
 * @date: 2018/11/1  15:46
 * @author: ymc
 * @QQ:745612618
 */

public class WxPresenter extends BasePresenter<WxContract.View> implements WxContract.Presenter{

    private WxContract.View view;

    public WxPresenter(WxContract.View view) {
        this.view = view;
    }

    @Override
    public void getWxTitleList() {

        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getWXList(), new Consumer<BaseHttpBean<List<WxListBean>>>() {
            @Override
            public void accept(BaseHttpBean listBaseResp) throws Exception {
                if(listBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS){
                    view.getWxResultOK((List<WxListBean>) listBaseResp.getData());
                }else if(listBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR){
                    view.getWxResultErr(listBaseResp.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.getWxResultErr(throwable.getMessage());
            }
        });
    }
}
