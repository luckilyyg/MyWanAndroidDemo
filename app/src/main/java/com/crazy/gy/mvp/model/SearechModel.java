package com.crazy.gy.mvp.model;

import android.content.Context;
import android.text.TextUtils;

import com.crazy.gy.mvp.util.ConstantUtil;
import com.crazy.gy.mvp.util.Sharedpreferences_Utils;

import java.util.Arrays;
import java.util.List;


/**
 * 搜索记录 model 层
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.home.search
 * @fileName: SearechModel
 * @date: 2018/8/27  14:26
 * @author: ymc
 * @QQ:745612618
 */

public class SearechModel implements ISearechModelImpl {

    /**
     * 保存 搜索 历史
     *
     * @param context
     * @param historyList
     */
    @Override
    public void saveHistory(Context context, List<String> historyList) {
        //保存之前先清空之前的存储
        Sharedpreferences_Utils.getInstance(context).remove(ConstantUtil.SEARCH_HISTORY);
        //存储
        StringBuilder sb = new StringBuilder();
        if (historyList.size() > 0) {
            for (String s : historyList) {
                sb.append(s).append(",");
            }
            sb.delete(sb.length() - 1, sb.length());
            Sharedpreferences_Utils.getInstance(context).setString(ConstantUtil.SEARCH_HISTORY, sb.toString().trim());
        }
    }

    @Override
    public void getHistory(Context context, List<String> historyList) {
        historyList.clear();
        String histories = Sharedpreferences_Utils.getInstance(context).getString(ConstantUtil.SEARCH_HISTORY);
        if (!TextUtils.isEmpty(histories)) {
            String[] history = histories.split(",");
            historyList.addAll(Arrays.asList(history));
        }
    }

}
