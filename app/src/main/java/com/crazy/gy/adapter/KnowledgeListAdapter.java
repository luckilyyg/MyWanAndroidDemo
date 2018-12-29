package com.crazy.gy.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crazy.gy.R;
import com.crazy.gy.entity.KnowledgeListBean;

import java.util.List;

/**
 * 作者：Administrator
 * 时间：2018/12/29
 * 功能：
 */
public class KnowledgeListAdapter extends BaseQuickAdapter<KnowledgeListBean, BaseViewHolder> {

    public KnowledgeListAdapter() {
        super(R.layout.knowledgefragment_item, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeListBean item) {
        helper.setText(R.id.tvTitle, item.getName());
        StringBuffer childrenname = new StringBuffer();
        for (KnowledgeListBean.Children children : item.getChildren()) {
            childrenname.append(children.getName() + "      ");
        }
        helper.setText(R.id.tvContent, childrenname.toString());
    }
}
