package com.crazy.gy.mvp.contract;


import com.crazy.gy.entity.WxPublicListBean;
import com.crazy.gy.mvp.base.contract.BasePre;
import com.crazy.gy.mvp.base.contract.BaseView;

/**
 * @packageName: cn.white.ymc.wanandroidmaster.ui.wx.wxdetail
 * @fileName: WxDetailContract
 * @date: 2018/11/1  16:41
 * @author: ymc
 * @QQ:745612618
 */

public class WxDetailContract {

   public interface View extends BaseView {

        void getWxPublicListOk(WxPublicListBean bean, boolean hasRefresh);

        void getWxPublicErr(String err);

    }

   public interface Presenter extends BasePre<View> {

        void onRefresh();

        void onLoadMore();

        void getWxPublicListResult(int id, int page);

    }
}
