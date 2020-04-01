package com.crazy.gy.mvp.contract;

import com.crazy.gy.entity.DemoTitleBean;
import com.crazy.gy.mvp.base.contract.BasePre;
import com.crazy.gy.mvp.base.contract.BaseView;

import java.util.List;



/**
 * @packageName: cn.white.ymc.wanandroidmaster.ui.demo
 * @fileName: DemoContract
 * @date: 2018/8/16  17:13
 * @author: ymc
 * @QQ:745612618
 */

public class DemoContract {

    public interface View extends BaseView {

        void getDemoResultOK(List<DemoTitleBean> demoBeans);

        void getDemoResultErr(String info);
    }

    public interface Presenter extends BasePre<View> {

        void getDemoTitleList();

    }

}
