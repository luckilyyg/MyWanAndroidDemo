package com.crazy.gy.mvp.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.crazy.gy.R;
import com.crazy.gy.mvp.base.mBaseActivity;
import com.crazy.gy.mvp.ui.fragment.DemoFragment;
import com.crazy.gy.mvp.ui.fragment.SystemFragment;
import com.crazy.gy.mvp.ui.fragment.WxFragment;
import com.crazy.gy.mvp.ui.fragment.mHomeFragment;
import com.crazy.gy.mvp.ui.fragment.mMineFragment;
import com.crazy.gy.mvp.util.BottomNavigationViewHelper;
import com.crazy.gy.mvp.util.JumpUtil;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class mMainActivity extends mBaseActivity {
    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @Bind(R.id.frame_layout)
    FrameLayout frameLayout;
    @Bind(R.id.float_button)
    FloatingActionButton floatButton;
    @Bind(R.id.navigation)
    BottomNavigationView navigation;
    private List<Fragment> fragmentList;
    private int lastIndex;
    private long mExitTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_m_main;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    floatButton.setVisibility(View.VISIBLE);
                    selectFragment(0);
                    return true;
                case R.id.navigation_system:
                    floatButton.setVisibility(View.VISIBLE);
                    selectFragment(1);
                    return true;
                case R.id.navigation_demo:
                    floatButton.setVisibility(View.VISIBLE);
                    selectFragment(3);
                    return true;
                case R.id.navigation_mine:
                    floatButton.setVisibility(View.GONE);
                    selectFragment(4);
                    return true;
                case R.id.navigation_wx:
                    floatButton.setVisibility(View.VISIBLE);
                    selectFragment(2);
                    return true;
                default:
                    break;
            }
            return false;
        }
    };


    @Override
    protected void initData() {
        initFragment();
        selectFragment(0);
        requestPermission();
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(toolbarCommon);
        getSupportActionBar().setTitle(R.string.app_name);
    }

    /**
     * 设置默认选中fragment
     *
     * @param index 碎片fragment
     */
    private void selectFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = fragmentList.get(index);
        Fragment lastFragment = fragmentList.get(lastIndex);
        lastIndex = index;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.frame_layout, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void initView() {
        // 将item 设置为不移动
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // 设置为蓝色背景
        floatButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.theme)));
    }

    @OnClick(R.id.float_button)
    void click(View view) {
        switch (view.getId()) {
            case R.id.float_button:
                scrollToTop();
                break;
            default:
                break;
        }
    }

    /**
     * 滚动置顶
     */
    private void scrollToTop() {
        switch (lastIndex) {
            case 0:
                mHomeFragment homeFragment = (mHomeFragment) fragmentList.get(0);
                homeFragment.scrollToTop();
                break;
            case 1:
                SystemFragment systemFragment = (SystemFragment) fragmentList.get(1);
                systemFragment.scrollToTop();
                break;
            case 2:
                WxFragment wxFragment = (WxFragment) fragmentList.get(2);
                wxFragment.scrollChildToTop();
                break;
            case 3:
                DemoFragment demoFragment = (DemoFragment) fragmentList.get(3);
                demoFragment.scrollChildToTop();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化碎片
     */
    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(mHomeFragment.getInstance());
        fragmentList.add(SystemFragment.getInstance());
        fragmentList.add(WxFragment.getInstance());
        fragmentList.add(DemoFragment.getInstance());
        fragmentList.add(mMineFragment.getInstance());
    }


    /**
     * 创建 menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
            case R.id.main_menu_hot:
                JumpUtil.overlay(activity, HotActivity.class);
                break;
            case R.id.main_menu_search:
                JumpUtil.overlay(activity, SearechActivity.class);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {

            Toast.makeText(activity, getString(R.string.exit_system), Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
            return false;
        } else {

            finish();
            return true;
        }
    }


    private void requestPermission() {
//        AndPermission.with(this)
//                .permission(Manifest.permission.READ_PHONE_STATE,
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                // 准备方法，和 okhttp 的拦截器一样，在请求权限之前先运行改方法，已经拥有权限不会触发该方法
//                .rationale(new Rationale() {
//                    @Override
//                    public void showRationale(Context context, List<String> permissions, RequestExecutor executor) {
//                        // 此处可以选择显示提示弹窗
//                        executor.execute();
//                    }
//                })
//                .onGranted(new Action() {
//                    @Override
//                    public void onAction(List<String> permissions) {
//
//                        Toast.makeText(activity, "用户给权限啦", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .onDenied(new Action() {
//                    @Override
//                    public void onAction(List<String> permissions) {
//                        if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
//                            // 打开权限设置页
//                            AndPermission.permissionSetting(activity).execute();
//                            return;
//                        }
//
//                        Toast.makeText(activity, "用户拒绝权限", Toast.LENGTH_SHORT).show();
//                    }
//                }).start();
    }
}
