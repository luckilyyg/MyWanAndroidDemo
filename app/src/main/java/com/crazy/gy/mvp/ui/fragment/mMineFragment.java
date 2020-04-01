package com.crazy.gy.mvp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crazy.gy.R;
import com.crazy.gy.mvp.base.BaseFragment;
import com.crazy.gy.mvp.ui.activity.AboutMeActivity;
import com.crazy.gy.mvp.ui.activity.CollectionListActivity;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.crazy.gy.mvp.util.JumpUtil;
import com.crazy.gy.mvp.util.Sharedpreferences_Utils;
import com.crazy.gy.mvp.util.toast.ToastUtil;
import com.crazy.gy.ui.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class mMineFragment extends BaseFragment {


    @Bind(R.id.image_head)
    CircleImageView imageHead;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.image_collect)
    ImageView imageCollect;
    @Bind(R.id.view_collect)
    RelativeLayout viewCollect;
    @Bind(R.id.image_todo)
    ImageView imageTodo;
    @Bind(R.id.view_todo)
    RelativeLayout viewTodo;
    @Bind(R.id.image_about)
    ImageView imageAbout;
    @Bind(R.id.view_about)
    RelativeLayout viewAbout;
    @Bind(R.id.tv_logout)
    TextView tvLogout;
    @Bind(R.id.normal_view)
    LinearLayout normalView;
    private boolean haslogin;
    private String userName;

    public mMineFragment() {
        // Required empty public constructor
    }

    public static mMineFragment getInstance() {
        return new mMineFragment();
    }


    @Override
    public int getLayoutResID() {
        return R.layout.fragment_m_mine;
    }

    @Override
    protected void initUI() {
        super.initUI();
        Glide.with(activity).load(R.drawable.icon_head).into(imageHead);
    }

    @Override
    protected void initData() {
        haslogin = Sharedpreferences_Utils.getInstance(activity).getBoolean(ConstantUtil.ISLOGIN);
        userName = Sharedpreferences_Utils.getInstance(activity).getString(ConstantUtil.USERNAME);
        tvUsername.setText(haslogin ? userName : getString(R.string.click_head_login));
        imageHead.setEnabled(!haslogin);
    }

    @OnClick({R.id.view_collect, R.id.view_todo, R.id.view_about, R.id.image_head, R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_collect:
                JumpUtil.overlay(activity, CollectionListActivity.class);
                break;
            case R.id.view_todo:
                ToastUtil.show(activity, getString(R.string.todo_err_msg));
                break;
            case R.id.view_about:
                JumpUtil.overlay(activity, AboutMeActivity.class);
                break;
            case R.id.image_head:
                JumpUtil.overlay(activity, LoginActivity.class);
                break;
            case R.id.tv_logout:
                ToastUtil.show(activity, getString(R.string.logout_ok));
                Sharedpreferences_Utils.getInstance(activity).clear();
                initData();
                break;
            default:
                break;
        }
    }

}
