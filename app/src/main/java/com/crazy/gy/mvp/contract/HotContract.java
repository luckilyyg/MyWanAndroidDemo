package com.crazy.gy.mvp.contract;

import com.crazy.gy.entity.HotBean;
import com.crazy.gy.mvp.base.contract.BasePre;
import com.crazy.gy.mvp.base.contract.BaseView;

import java.util.List;


/**
 *  最热网站 契约类
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.home.hot
 * @fileName: HotContract
 * @date: 2018/8/23  17:02
 * @author: ymc
 * @QQ:745612618
 */

public class HotContract {

    public interface View extends BaseView {
        /**
         * 获取成功 失败
         */
         void getHotResultOK(List<HotBean> hotBeans);

         void getHotResultErr(String err);
    }

    public interface Presenter extends BasePre<View> {

        /**
         * 获取 最热网站 列表
         */
        void getHotList();

    }

}
