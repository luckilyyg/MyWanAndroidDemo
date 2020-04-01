package com.crazy.gy.mvp.contract;

import com.crazy.gy.entity.KnowledgeListBean;
import com.crazy.gy.mvp.base.contract.BasePre;
import com.crazy.gy.mvp.base.contract.BaseView;

import java.util.List;



/**
 *  接口契约类
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.fragment.system
 * @fileName: SystemContract
 * @date: 2018/8/13  13:33
 * @author: ymc
 * @QQ:745612618
 */

public class SystemContract {

    public  interface View extends BaseView {

        void getSystemListOk(List<KnowledgeListBean> dataBean);

        void getSystemListErr(String info);
    }

    public  interface Presenter extends BasePre<View> {

        void autoRefresh();

        void getSystemList();

    }

}
