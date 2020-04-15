package com.crazy.gy.mvp.presenter;


import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.HomeDetailContract;
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
 * 首页详细界面 presenter 层
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.home.homedetail
 * @fileName: HomePresenter
 * @date: 2018/8/10  13:16
 * @author: ymc
 * @QQ:745612618
 */

public class HomeDetailPresenter extends BasePresenter<HomeDetailContract.view> implements HomeDetailContract.presenter {

    private HomeDetailContract.view view;

    public HomeDetailPresenter(HomeDetailContract.view view) {
        this.view = view;
    }

    /**
     * 收藏文章
     * @param id
     */
    @Override
    public void collectArticle(int id) {

        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendNoRequest(mLoginServer.collectArticle(id), new Consumer<BaseHttpBean>() {
            @Override
            public void accept(BaseHttpBean baseResp) throws Exception {
                if(baseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR){
                    view.collectArticleErr(baseResp.getErrorMsg());
                }else if(baseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS){
                    view.collectArticleOK((String) baseResp.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.collectArticleErr(throwable.getMessage());
            }
        });
    }

    /**
     * 取消收藏文章
     * @param id
     */
    @Override
    public void cancelCollectArticle(int id) {

        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendNoRequest(mLoginServer.removeCollectArticle(id), new Consumer<BaseHttpBean>() {
            @Override
            public void accept(BaseHttpBean baseResp) throws Exception {
                if(baseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR){
                    view.cancelCollectArticleErr(baseResp.getErrorMsg());
                }else if(baseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS){
                    view.cancelCollectArticleOK((String) baseResp.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.cancelCollectArticleErr(throwable.getMessage());
            }
        });
    }
}
