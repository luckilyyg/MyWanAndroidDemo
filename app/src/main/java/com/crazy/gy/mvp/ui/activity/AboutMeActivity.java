package com.crazy.gy.mvp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazy.gy.R;
import com.crazy.gy.mvp.base.mBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutMeActivity extends mBaseActivity {

    @Bind(R.id.article_toolbar)
    Toolbar toolbarCommon;
    @Bind(R.id.applayout)
    AppBarLayout applayout;
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.tv_content)
    TextView tvContent;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_me;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbarCommon);
        getSupportActionBar().setTitle(R.string.about_us);
        toolbarCommon.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvContent.setText(Html.fromHtml(getString(R.string.about_content)));
    }

    @Override
    protected void initData() {

    }
}
