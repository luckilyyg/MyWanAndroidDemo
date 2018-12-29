package com.crazy.gy.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crazy.gy.R;
import com.crazy.gy.entity.HomeListBean;

import java.util.List;

/**
 * 作者：Administrator
 * 时间：2018/12/28
 * 功能：
 */
public class HomeListAdapter extends BaseQuickAdapter<HomeListBean.HomeListDetail, BaseViewHolder> {

    public HomeListAdapter() {
        super(R.layout.homefragment_item, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeListBean.HomeListDetail item) {
        helper.setImageResource(R.id.img_authorheadpic, R.drawable.icon_user);
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_publishdate, item.getNiceDate());
        helper.setText(R.id.tv_contenttitle, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.tvChapterName, item.getChapterName());
    }
}
