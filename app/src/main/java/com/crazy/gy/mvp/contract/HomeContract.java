package com.crazy.gy.mvp.contract;

import com.crazy.gy.entity.BannerListBean;
import com.crazy.gy.entity.HomeListBean;
import com.crazy.gy.entity.UserBean;
import com.crazy.gy.mvp.base.contract.BasePre;
import com.crazy.gy.mvp.base.contract.BaseView;

import java.util.List;


/**
 * 首页 契约类
 *
 */

public class HomeContract {

    public interface View extends BaseView {

        void getHomepageListOk(HomeListBean dataBean, boolean isRefresh);

        void getHomepageListErr(String info);

        void getBannerOk(List<BannerListBean> bannerBean);

        void getBannerErr(String info);

        void loginOk(UserBean userInfo);

        void loginErr(String err);

    }

    public interface Per extends BasePre<View> {
        /**
         * 刷新 列表
         */
        void autoRefresh();

        /**
         * 加載更多
         */
        void loadMore();

        /**
         * 获取 轮播信息
         */
        void getBanner();

        /**
         * 获取 首页 页数数据
         *
         * @param page
         */
        void getHomepageListData(int page);

        /**
         * 帐号 登录
         */
        void loginUser(String username, String password);


    }

}
