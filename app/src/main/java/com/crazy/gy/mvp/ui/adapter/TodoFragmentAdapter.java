package com.crazy.gy.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.crazy.gy.mvp.ui.fragment.TodoFragment;

import java.util.List;

/**
 * Created on 2020/4/10 15:48
 *
 * @auther super果
 * @annotation
 */
public class TodoFragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments;
    TodoFragment fragment;
    public TodoFragmentAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if(fragments==null){
            return 0;
        }else{
            return fragments.size();
        }
    }

    /**
     * 获取当前显示的fragment
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        fragment = (TodoFragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public TodoFragment getCurrentFragment() {
        return fragment;
    }
}
