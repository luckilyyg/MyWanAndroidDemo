package com.crazy.gy.mvp.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.crazy.gy.R;

import butterknife.Bind;


/**
 * @packageName: cn.white.ymc.wanandroidmaster.ui.home.adapter
 * @fileName: HomePageAdaper
 * @date: 2018/8/8  14:31
 * @author: ymc
 * @QQ:745612618
 */

public class HomePageHolder extends BaseViewHolder {

    @Bind(R.id.tv_author)
    TextView mTvAuthor;
    @Bind(R.id.tv_type)
    TextView mTvType;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.image_collect)
    ImageView mImageCollect;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.tv_tag)
    TextView mTvTag;

    public HomePageHolder(View view) {
        super(view);
    }

}
