package com.crazy.gy.mvp.presenter;


import com.crazy.gy.entity.WxListBean;
import com.crazy.gy.entity.WxPublicListBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.WxDetailContract;
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
 * 微信公众号详情列表 presenter
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.wx.wxdetail
 * @fileName: WxDetailPresenter
 * @date: 2018/11/1  17:15
 * @author: ymc
 * @QQ:745612618
 */

public class WxDetailPresenter extends BasePresenter<WxDetailContract.View> implements WxDetailContract.Presenter{

    private WxDetailContract.View view;
    private int wxId = -1;
    private int wxPage = -1;
    private boolean isRefresh = true;

    public WxDetailPresenter(WxDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        if(wxId !=-1 && wxPage != -1){
            getWxPublicListResult(wxId,1);
        }
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        if(wxId !=-1 && wxPage != -1){
            wxPage ++;
            getWxPublicListResult(wxId,wxPage);
        }
    }

    @Override
    public void getWxPublicListResult(int id, int page) {
        this.wxId = id;
        this.wxPage = page;
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getWXDetailList(page,id), new Consumer<BaseHttpBean<WxPublicListBean>>() {
            @Override
            public void accept(BaseHttpBean datasBeanBaseResp) throws Exception {
                if(datasBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR){
                    view.getWxPublicErr(datasBeanBaseResp.getErrorMsg());
                }else if(datasBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS){
                    view.getWxPublicListOk((WxPublicListBean) datasBeanBaseResp.getData(),isRefresh);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.getWxPublicErr(throwable.getMessage());
            }
        });
    }
}
