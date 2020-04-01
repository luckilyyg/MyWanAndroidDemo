package com.crazy.gy.mvp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.crazy.gy.R;
import com.crazy.gy.entity.HotBean;
import com.crazy.gy.mvp.base.BaseResultActivity;
import com.crazy.gy.mvp.contract.HotContract;
import com.crazy.gy.mvp.presenter.HotPresenter;
import com.crazy.gy.mvp.util.ColorUtil;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.crazy.gy.mvp.util.JumpUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HotActivity extends BaseResultActivity implements HotContract.View{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;

    private HotPresenter presenter;
    private List<HotBean> beanList;
    private TagAdapter<HotBean> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot;
    }
    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.hot_title));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void initView() {
        super.initView();
        showLoading();
    }
    @Override
    protected void initData() {
        presenter = new HotPresenter(this);
        beanList = new LinkedList<>();
        presenter.getHotList();
    }

    @Override
    public void getHotResultOK(List<HotBean> hotBeans) {
        beanList.clear();
        beanList.addAll(hotBeans);
        adapter = new TagAdapter<HotBean>(hotBeans) {
            @Override
            public View getView(FlowLayout parent, int position, HotBean hotBean) {
                TextView textView = (TextView) getLayoutInflater().inflate(R.layout.textview_item_hot,null);
                textView.setText(hotBean.getName());
                textView.setTextColor(ColorUtil.getRandomColor());
                return textView;
            }
        };
        idFlowlayout.setAdapter(adapter);
        idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Bundle bundle = new Bundle();
                bundle.putString(ConstantUtil.HOME_DETAIL_TITLE, beanList.get(position).getName());
                bundle.putString(ConstantUtil.HOME_DETAIL_PATH, beanList.get(position).getLink());
                JumpUtil.overlay(activity, HomeDetailActivity.class, bundle);
                return false;
            }
        });
        showNormal();
    }

    @Override
    public void getHotResultErr(String err) {
        showError(err);
    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError(String err) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void reload() {
        showLoading();
        presenter.getHotList();
    }
}
