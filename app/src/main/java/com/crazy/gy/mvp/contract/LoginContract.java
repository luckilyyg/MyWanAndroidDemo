package com.crazy.gy.mvp.contract;


import com.crazy.gy.entity.UserBean;
import com.crazy.gy.mvp.base.contract.BasePre;
import com.crazy.gy.mvp.base.contract.BaseView;

/**
 * login 登陆
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.login
 * @fileName: LoginContrat
 * @date: 2018/7/23  14:27
 * @author: ymc
 * @QQ:745612618
 */

public class LoginContract {

    public interface View extends BaseView {

        void loginOk(UserBean userInfo);

        void loginErr(String info);

    }

    public interface Presenter extends BasePre<View> {

        void login(String name, String password);

    }
}
