package com.crazy.gy.mvp.presenter;

import android.content.Context;

import com.crazy.gy.entity.BannerListBean;
import com.crazy.gy.entity.HotKeyBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.SearechContract;
import com.crazy.gy.mvp.model.SearechModel;
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
 * 搜索界面 presenter 层
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.home.search
 * @fileName: SearechPresenter
 * @date: 2018/8/24  15:27
 * @author: ymc
 * @QQ:745612618
 */

public class SearechPresenter extends BasePresenter<SearechContract.View> implements SearechContract.Presenter {

    private SearechContract.View view;
    private SearechModel model;

    public SearechPresenter(SearechContract.View view) {
        this.view = view;
        this.model = new SearechModel();
    }

    /**
     * 获取 搜索 热度 词语结果
     */
    @Override
    public void getHotListResult() {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getHitKeyBean(), new Consumer<BaseHttpBean<List<HotKeyBean>>>() {
            @Override
            public void accept(BaseHttpBean listBaseResp) throws Exception {
                if (listBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                    view.getHotListOk((List<HotKeyBean>) listBaseResp.getData());
                } else if (listBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                    view.getHotListErr(listBaseResp.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.getHotListErr(throwable.getMessage());
            }
        });
    }

    /**
     * 保存 搜索历史
     *
     * @param context
     * @param historyList
     */
    @Override
    public void saveHistory(Context context, List<String> historyList) {
        model.saveHistory(context, historyList);
    }

    /**
     * 获取 搜索历史
     *
     * @param context
     * @param historyList
     */
    @Override
    public void getHistoryList(Context context, List<String> historyList) {
        model.getHistory(context, historyList);
    }


}
