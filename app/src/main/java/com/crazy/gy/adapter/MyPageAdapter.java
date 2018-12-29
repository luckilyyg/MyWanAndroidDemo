package com.crazy.gy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 描述：
 * 作者：
 * 时间：
 */
public class MyPageAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragmentList;



    public MyPageAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return (mFragmentList == null) ? 0 : mFragmentList.size();
    }

}