package com.crazy.gy.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.crazy.gy.R;
import com.crazy.gy.entity.TodoDesBean;
import com.crazy.gy.mvp.base.mBaseActivity;
import com.crazy.gy.mvp.ui.adapter.TodoFragmentAdapter;
import com.crazy.gy.mvp.ui.fragment.TodoFragment;
import com.crazy.gy.mvp.util.JumpUtil;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TodoMainActivity extends mBaseActivity {
    public static final int REQUEST_CODE_ADD_TODO = 1;
    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @Bind(R.id.todo_tab_layout)
    SlidingTabLayout todoTabLayout;
    @Bind(R.id.wx_view_pager)
    ViewPager wxViewPager;
    @Bind(R.id.normal_view)
    LinearLayout normalView;
    @Bind(R.id.float_button)
    FloatingActionButton floatButton;
    List<Fragment> fragmentList;
    TodoFragmentAdapter adapter;
    String[] todoTitle = {"待办清单", "完成清单"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_todo_main;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(toolbarCommon);
        getSupportActionBar().setTitle(R.string.todo);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(TodoFragment.newInstance(false));
        fragmentList.add(TodoFragment.newInstance(true));
        adapter = new TodoFragmentAdapter(getSupportFragmentManager(), fragmentList);
        wxViewPager.setAdapter(adapter);
        todoTabLayout.setViewPager(wxViewPager, todoTitle);
        adapter.notifyDataSetChanged();

    }

    /**
     * 创建 menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * menu 选择器
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_todo:
//                JumpUtil.overlay(activity, AddTodoActivity.class);
                Intent intent = new Intent(this, AddTodoActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_TODO);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.float_button)
    public void onViewClicked() {
        TodoFragment todoFragment = new TodoFragment();
        todoFragment.scrollToTop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_TODO) {
            switch (resultCode) {
                case 2:
                    TodoDesBean todoDesBean = (TodoDesBean) data.getSerializableExtra("add_todo");
                    TodoFragment todoFragment = (TodoFragment) ((TodoFragmentAdapter) wxViewPager.getAdapter()).getItem(0);
                    todoFragment.updateAddTodoData(todoDesBean);
                    break;
                default:
                    break;
            }
        }
    }

    public void updateDoneOrCancelData(TodoDesBean todoDesBean, int postition) {
        TodoFragment todoFragment = (TodoFragment) ((TodoFragmentAdapter) wxViewPager.getAdapter()).getItem(postition);
        todoFragment.updateAddTodoData(todoDesBean);
    }
}
