package com.crazy.gy.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crazy.gy.R;
import com.crazy.gy.entity.KnowledgeListBean;

import java.util.List;



/**
 * 体系界面列表 适配器
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.fragment.system.adapter
 * @fileName: SystemAdapter
 * @date: 2018/8/13  14:21
 * @author: ymc
 * @QQ:745612618
 */

public class SystemAdapter extends BaseQuickAdapter<KnowledgeListBean, BaseViewHolder> {

    public SystemAdapter(int layoutResId, @Nullable List<KnowledgeListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeListBean item) {
        helper.setText(R.id.tv_knowledge_title, item.getName());
        StringBuilder sb = new StringBuilder();
        for (KnowledgeListBean.Children childrenBean : item.getChildren()) {
            sb.append(childrenBean.getName()).append("      ");
        }
        helper.setText(R.id.tv_knowledge_content, sb.toString());
    }
}
