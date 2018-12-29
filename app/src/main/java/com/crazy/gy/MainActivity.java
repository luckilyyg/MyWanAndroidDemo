package com.crazy.gy;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.crazy.gy.adapter.MyPageAdapter;
import com.crazy.gy.fragment.HomeFragment;
import com.crazy.gy.fragment.KnowledgeFragment;
import com.crazy.gy.fragment.ProjectFragment;
import com.crazy.gy.fragment.ToDoFragment;
import com.crazy.gy.view.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.vp_homepage_content)
    NoScrollViewPager vpHomePager;
    @Bind(R.id.id_homepage_tablayout)
    TabLayout mBottomNavigationBar;
    private ArrayList<Fragment> mFragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initPager();
        initData();
        initAdapter();
    }

    private void initView() {

    }

    private void initPager() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new KnowledgeFragment());
        mFragmentList.add(new ProjectFragment());
        mFragmentList.add(new ToDoFragment());
        mBottomNavigationBar.addTab(mBottomNavigationBar.newTab());
        mBottomNavigationBar.addTab(mBottomNavigationBar.newTab());
        mBottomNavigationBar.addTab(mBottomNavigationBar.newTab());
        mBottomNavigationBar.addTab(mBottomNavigationBar.newTab());
        //缓存3个Fragment
        vpHomePager.setOffscreenPageLimit(3);
        //通过适配器把Fragment添加到主界面上
        vpHomePager.setAdapter(new MyPageAdapter(getSupportFragmentManager(), mFragmentList));
        //把tab和ViewPager关联起来,并会将文字清空
        mBottomNavigationBar.setupWithViewPager(vpHomePager);
        mBottomNavigationBar.getTabAt(0).setCustomView(getCustomView(0));
        mBottomNavigationBar.getTabAt(1).setCustomView(getCustomView(1));
        mBottomNavigationBar.getTabAt(2).setCustomView(getCustomView(2));
        mBottomNavigationBar.getTabAt(3).setCustomView(getCustomView(3));
        //默认设置显示页面
        vpHomePager.setCurrentItem(0);
    }
    private View getCustomView(int position) {
        switch (position) {
            case 0:
                return LayoutInflater.from(this).inflate(R.layout.fragment_home_tab, null);
            case 1:
                return LayoutInflater.from(this).inflate(R.layout.fragment_knowledge_tab, null);
            case 2:
                return LayoutInflater.from(this).inflate(R.layout.fragment_project_tab, null);
            case 3:
                return LayoutInflater.from(this).inflate(R.layout.fragment_todo_tab, null);

        }
        return null;
    }
    private void initData() {

    }

    private void initAdapter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
