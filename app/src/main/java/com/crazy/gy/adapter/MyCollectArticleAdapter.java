package com.crazy.gy.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crazy.gy.R;
import com.crazy.gy.entity.ArticleBean;

/**
 * 作者：Administrator
 * 时间：2019/1/2
 * 功能：
 */
public class MyCollectArticleAdapter extends BaseQuickAdapter<ArticleBean.DatasBean, BaseViewHolder> {

    public MyCollectArticleAdapter() {
        super(R.layout.mycollectsarticle_item, null);

    }


    @Override
    protected void convert(BaseViewHolder helper, ArticleBean.DatasBean item) {
        helper.setImageResource(R.id.img_authorheadpic, R.drawable.icon_user);
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_publishdate, item.getNiceDate());
        helper.setText(R.id.tv_contenttitle, item.getTitle());

        if (TextUtils.isEmpty(item.getsuperChapterName())) {
            helper.setText(R.id.tvChapterName, item.getChapterName());
        } else {
            helper.setText(R.id.tvChapterName, item.getsuperChapterName() + " / " + item.getChapterName());
        }
    }
}
