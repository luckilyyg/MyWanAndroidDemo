package com.crazy.gy.mvp.contract;

import com.crazy.gy.entity.NavigationListBean;
import com.crazy.gy.mvp.base.contract.BasePre;
import com.crazy.gy.mvp.base.contract.BaseView;

import java.util.List;

/**
 * Created on 2020/4/16 11:05
 *
 * @auther super果
 * @annotation
 */
public class NavgationContract {
    public interface View extends BaseView {
        void getNavgationOk(List<NavigationListBean> navigationListBean);

        void getNavgationErr(String err);
    }

    public interface Per extends BasePre<NavgationContract.View> {
        void getNavgationListData();
    }
}
