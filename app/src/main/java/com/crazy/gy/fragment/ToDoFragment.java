package com.crazy.gy.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazy.gy.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoFragment extends Fragment {


    @Bind(R.id.tv_titlecontent)
    TextView tvTitlecontent;
    @Bind(R.id.tab)
    TabLayout tab;
    @Bind(R.id.add_order)
    ImageView addOrder;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private String[] titles = {"待办清单", "已完成清单"};
    private List<Fragment> list;
    private MyAdapter adapter;

    public ToDoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        initAdapter();
        return view;
    }

    private void initView() {
        tvTitlecontent.setText("清单");
        list = new ArrayList<>();
        list.add(ToDoContentFragment.newInstance(false));
        list.add(ToDoContentFragment.newInstance(true));
        //ViewPager的适配器
        adapter = new MyAdapter(getActivity().getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        tab.setupWithViewPager(viewpager);
    }

    private void initData() {

    }

    private void initAdapter() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        //重写这个方法，将设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


    @OnClick(R.id.add_order)
    public void onViewClicked() {
        AddOneToDoFragment.newInstance().show(getActivity().getSupportFragmentManager(), "add");
    }
}
