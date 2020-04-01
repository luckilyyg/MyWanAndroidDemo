package com.crazy.gy.mvp.presenter;

import com.crazy.gy.entity.HotBean;
import com.crazy.gy.entity.UserBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.HotContract;
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
 * 最热网站 presenter 层 实现
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.home.hot
 * @fileName: HotPresenter
 * @date: 2018/8/24  13:42
 * @author: ymc
 * @QQ:745612618
 */

public class HotPresenter extends BasePresenter<HotContract.View> implements HotContract.Presenter {

    private HotContract.View view;

    public HotPresenter(HotContract.View view) {
        this.view = view;
    }

    @Override
    public void getHotList() {

        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getHotList(), new Consumer<BaseHttpBean<List<HotBean>>>() {
            @Override
            public void accept(BaseHttpBean listBaseResp) throws Exception {
                if (listBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                    view.getHotResultErr(listBaseResp.getErrorMsg());
                } else if (listBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                    view.getHotResultOK((List<HotBean>) listBaseResp.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.getHotResultErr(throwable.getMessage());
            }
        });
    }
}
