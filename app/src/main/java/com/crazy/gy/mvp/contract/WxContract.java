package com.crazy.gy.mvp.contract;

import com.crazy.gy.entity.WxListBean;
import com.crazy.gy.mvp.base.contract.BasePre;
import com.crazy.gy.mvp.base.contract.BaseView;

import java.util.List;



/**
 * 微信公众号界面
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.wx
 * @fileName: WxContract
 * @date: 2018/11/1  14:16
 * @author: ymc
 * @QQ:745612618
 */

public class WxContract {

    public interface View extends BaseView {

        void getWxResultOK(List<WxListBean> demoBeans);

        void getWxResultErr(String info);
    }

    public interface Presenter extends BasePre<View> {

        void getWxTitleList();

    }

}
