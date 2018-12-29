package com.crazy.gy.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazy.gy.R;
import com.crazy.gy.adapter.ProjectPagerAdapter;
import com.crazy.gy.entity.KnowledgeListBean;
import com.crazy.gy.entity.ProjectListBean;
import com.crazy.gy.net.RxCallback.OnResultClick;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxRequest.ApiServerImp;
import com.crazy.gy.view.DialogText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends Fragment {


    @Bind(R.id.tv_titlecontent)
    TextView tvTitlecontent;
    @Bind(R.id.mTabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.mViewpager)
    ViewPager mViewpager;
    private ApiServerImp mApiServerImp;
    private DialogText mDialog;
    private ProjectPagerAdapter projectPagerAdapter;

    public ProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        initAdapter();
        return view;
    }

    private void initView() {
        tvTitlecontent.setText("项目");
        mApiServerImp = new ApiServerImp();
        mDialog = new DialogText(getActivity(), R.style.MyDialog);
    }

    private void initData() {
        mApiServerImp.ProjectListImp(new OnResultClick<BaseHttpBean>() {
            @Override
            public void success(BaseHttpBean baseHttpBean) {
                if (baseHttpBean != null) {
                    if (baseHttpBean.getErrorCode() == 0) {
                        List<ProjectListBean> listBean = (List<ProjectListBean>) baseHttpBean.getData();
                        List<Integer> ids = new ArrayList<>();
                        List<String> names = new ArrayList<>();
                        if (listBean.size() > 0) {
                            for (ProjectListBean project : listBean) {
                                ids.add(project.getId());
                                names.add(project.getName());
                            }
                        }
                        projectPagerAdapter = new ProjectPagerAdapter(getChildFragmentManager(), names, ids);
                        mViewpager.setAdapter(projectPagerAdapter);
                        mViewpager.setOffscreenPageLimit(1);
                        mViewpager.setCurrentItem(0, false);
                        mTabLayout.setupWithViewPager(mViewpager, true);
                    } else {
                        mDialog.show();
                        showInfo(baseHttpBean.getErrorMsg(), 0);
                    }
                }
            }

            @Override
            public void fail(Throwable throwable) {
                mDialog.show();
                showInfo(throwable.getMessage(), 0);

            }
        });
    }

    private void initAdapter() {
    }

    /**
     * @param errorString
     * @param is
     */
    public void showInfo(final String errorString, final int is) {
        String isString = "";
        if (is == 1) {
            isString = "success";
        }
        if (is == 0) {
            isString = "fail";
        }
        if (mDialog != null) {
            mDialog.setText(errorString, isString);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();
                }
            }, 1000);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
