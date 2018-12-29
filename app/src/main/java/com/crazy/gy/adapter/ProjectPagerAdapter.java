package com.crazy.gy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.crazy.gy.fragment.ProjectContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Administrator
 * 时间：2018/12/29
 * 功能：
 */
public class ProjectPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> titles;
    private List<Integer> ids;
    private List<ProjectContentFragment> articleFragments = new ArrayList<>();

    public ProjectPagerAdapter(FragmentManager fm, List<String> titles, List<Integer> ids) {
        super(fm);
        this.titles = titles;
        this.ids = ids;

        for (Integer id : ids) {
            articleFragments.add(ProjectContentFragment.newInstance(id));
        }
    }

    @Override
    public Fragment getItem(int i) {
        return articleFragments.get(i);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
