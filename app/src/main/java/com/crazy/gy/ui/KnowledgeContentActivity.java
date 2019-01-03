package com.crazy.gy.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazy.gy.R;
import com.crazy.gy.adapter.KnowledgePageAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KnowledgeContentActivity extends AppCompatActivity {
    @Bind(R.id.img_titleleft)
    ImageView imgTitleleft;
    @Bind(R.id.tv_titlecontent)
    TextView tvTitlecontent;
    @Bind(R.id.mTabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.mViewpager)
    ViewPager mViewpager;
    private ArrayList<String> names=new ArrayList<>();
    private ArrayList<Integer> ids=new ArrayList<>();
    private String knowledgename;
    private KnowledgePageAdapter pageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_content);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            names = getIntent().getStringArrayListExtra("names");
            ids = getIntent().getIntegerArrayListExtra("ids");
            knowledgename = getIntent().getStringExtra("knowledgename");
        }
        initView();
        initData();
        initAdapter();
    }

    private void initView() {
        imgTitleleft.setVisibility(View.VISIBLE);
        imgTitleleft.setImageResource(R.drawable.ic_left);
        tvTitlecontent.setText("知识体系");
    }

    private void initData() {
        pageAdapter=new KnowledgePageAdapter(getSupportFragmentManager(), names, ids);
        mViewpager.setAdapter(pageAdapter);
        mViewpager.setOffscreenPageLimit(1);
        mViewpager.setCurrentItem(0, false);
        mTabLayout.setupWithViewPager(mViewpager, true);
    }

    private void initAdapter() {

    }

    @OnClick(R.id.img_titleleft)
    public void onViewClicked() {
        KnowledgeContentActivity.this.finish();
    }
}
