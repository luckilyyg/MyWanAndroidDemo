package com.crazy.gy.mvp.contract;


import com.crazy.gy.entity.DemoDetailListBean;
import com.crazy.gy.mvp.base.contract.BasePre;
import com.crazy.gy.mvp.base.contract.BaseView;

/**
 * @packageName: cn.white.ymc.wanandroidmaster.ui.demo.demolist
 * @fileName: DemoDetailListContract
 * @date: 2018/8/17  11:05
 * @author: ymc
 * @QQ:745612618
 */

public class DemoDetailListContract {

    public interface View extends BaseView {

        /**
         * 获取 项目列表成功
         * @param beans
         * @param isRefresh
         */
        void getDemoListOK(DemoDetailListBean beans, boolean isRefresh);

        /**
         * 获取 项目失败
         * @param err
         */
        void getDemoListErr(String err);
    }

    public interface Presenter extends BasePre<View> {

        /**
         * 获取 项目列表
         * @param page
         * @param id
         */
        void getDemoList(int page, int id);

        /**
         * 刷新
         */
        void autoRefresh();

        /**
         * 加载更多
         */
        void loadMore();

    }

}
