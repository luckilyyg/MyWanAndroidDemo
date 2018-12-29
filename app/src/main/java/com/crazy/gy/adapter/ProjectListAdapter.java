package com.crazy.gy.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crazy.gy.R;
import com.crazy.gy.entity.ProjectContentListBean;
import com.crazy.gy.entity.ProjectListBean;

import java.util.List;

/**
 * 作者：Administrator
 * 时间：2018/12/29
 * 功能：
 */
public class ProjectListAdapter extends BaseQuickAdapter<ProjectContentListBean.DatasBean, BaseViewHolder> {


    public ProjectListAdapter() {
        super(R.layout.projectfragment_item, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectContentListBean.DatasBean item) {
        helper.setText(R.id.tvTitle,item.getTitle());
        helper.setText(R.id.tvContent,item.getDesc());
        helper.setText(R.id.tvAuthor, item.getAuthor());
        helper.setText(R.id.tvNiceDate, item.getNiceDate());
        ImageView articleiv = helper.getView(R.id.articleiv);
        Glide.with(mContext).load(item.getEnvelopePic()).into(articleiv);

    }
}

