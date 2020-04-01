package com.crazy.gy.mvp.presenter;

import com.crazy.gy.entity.UserBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.LoginContract;
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
 * 登陆 presenter 层
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.login
 * @fileName: LoginPresenter
 * @date: 2018/7/23  15:25
 * @author: ymc
 * @QQ:745612618
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void login(String name, String password) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getLogin(name, password), new Consumer<BaseHttpBean<UserBean>>() {
            @Override
            public void accept(BaseHttpBean baseResp) throws Exception {
                if (baseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                    view.loginOk((UserBean) baseResp.getData());
                } else if (baseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                    view.loginErr(baseResp.getErrorMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.loginErr(throwable.getMessage());
            }
        });
    }

}
