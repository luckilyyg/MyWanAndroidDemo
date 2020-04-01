package com.crazy.gy.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.gy.R;
import com.crazy.gy.entity.HomeListBean;
import com.crazy.gy.mvp.holder.HomePageHolder;

import java.util.List;

/**
 * Created on 2020/3/31 11:15
 *
 * @auther super果
 * @annotation
 */
public class HomePageAdapter extends BaseQuickAdapter<HomeListBean.HomeListDetail, HomePageHolder> {
    public HomePageAdapter(int layoutResId, @Nullable List<HomeListBean.HomeListDetail> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(HomePageHolder helper, HomeListBean.HomeListDetail item) {
        helper.getView(R.id.tv_tag).setVisibility(View.GONE);
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_content, item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_author, item.getAuthor());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_time, item.getNiceDate());
        }
        if (!TextUtils.isEmpty(item.getChapterName())) {
            String classifyName = item.getSuperChapterName() + " / " + item.getChapterName();
            helper.setText(R.id.tv_type, classifyName);
        }
        if (item.getSuperChapterName().contains(mContext.getString(R.string.project))) {
            helper.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_tag, mContext.getString(R.string.project));
            helper.setTextColor(R.id.tv_tag,mContext.getResources().getColor(R.color.green));
            helper.setBackgroundRes(R.id.tv_tag,R.drawable.drawable_shape_green);
        } else if (item.getSuperChapterName().contains(mContext.getString(R.string.hot))) {
            helper.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_tag, mContext.getString(R.string.hot));
            helper.setTextColor(R.id.tv_tag,mContext.getResources().getColor(R.color.red));
            helper.setBackgroundRes(R.id.tv_tag,R.drawable.drawable_shape_red);
        }
        helper.addOnClickListener(R.id.tv_type);
        helper.addOnClickListener(R.id.image_collect);
        helper.setImageResource(R.id.image_collect, item.isCollect() ? R.drawable.icon_collect : R.drawable.icon_no_collect);
    }
}
