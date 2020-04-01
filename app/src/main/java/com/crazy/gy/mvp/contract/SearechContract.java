package com.crazy.gy.mvp.contract;

import android.content.Context;

import com.crazy.gy.entity.HotKeyBean;
import com.crazy.gy.mvp.base.contract.BasePre;
import com.crazy.gy.mvp.base.contract.BaseView;

import java.util.List;


/**
 *  搜索 界面契约类
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.home.search
 * @fileName: SearechContract
 * @date: 2018/8/24  15:24
 * @author: ymc
 * @QQ:745612618
 */

public class SearechContract {

    public interface View extends BaseView {

        void getHotListOk(List<HotKeyBean> beanList);

        void getHotListErr(String err);

    }

    public interface Presenter extends BasePre<View> {

        void getHotListResult();

        void saveHistory(Context context, List<String> historyList);

        void getHistoryList(Context context, List<String> historyList);

    }

}
