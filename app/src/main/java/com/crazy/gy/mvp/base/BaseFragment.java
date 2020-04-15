package com.crazy.gy.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazy.gy.App;
import com.crazy.gy.R;
import com.crazy.gy.entity.TodoDesBean;
import com.crazy.gy.mvp.base.contract.BaseView;
import com.crazy.gy.mvp.util.network.NetUtil;
import com.crazy.gy.mvp.util.network.NetWorkBroadcastReceiver;
import com.ldoublem.loadingviewlib.LVChromeLogo;

import butterknife.ButterKnife;


/**
 * fragment 基础类
 * @packageName: cn.white.ymc.wanandroidmaster.base
 * @fileName: BaseFragment
 * @date: 2018/7/27  9:39
 * @author: ymc
 * @QQ:745612618
 */

public abstract class BaseFragment extends Fragment implements BaseView, NetWorkBroadcastReceiver.NetEvent{
    protected Activity activity;
    public static NetWorkBroadcastReceiver.NetEvent eventFragment;
    private int netMobile;
    public static String TAG;

    /**
     * 处理页面加载中、页面加载失败、页面没数据
     */
    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;
    public static final int EMPTY_STATE = 3;

    private View mErrorView;
    private View mLoadingView;
    private View mEmptyView;
    private LVChromeLogo lvChromeLogo;
    private ViewGroup mNormalView;
    private TextView tvMsg;
    /**
     * 当前状态
     */
    private int currentState = NORMAL_STATE;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Log.e(TAG, "onCreateView: " );
        return inflater.inflate(getLayoutResID(), container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Log.e(TAG, "onViewCreated: " );
        activity = getActivity();
        TAG = getClass().getSimpleName();//获取Activity名称
        ButterKnife.bind(this, view);
        initUI();
        initData();
    }

    /**
     * 网络改变的监听
     * @param netMobile
     */
    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }

    @Override
    public void onStart() {
        super.onStart();
//        Log.e(TAG, "onStart: " );
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.e(TAG, "onResume: " );
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.e(TAG, "onPause: " );
    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.e(TAG, "onStop: " );
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
//        Log.e(TAG, "onDestroy: " );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Log.e(TAG, "onDestroyView: " );
    }

    /**
     * 获取 布局信息
     * @return
     */
    public abstract int getLayoutResID();

    /**
     * 数据初始化
     */
    protected abstract void initData();

    /**
     * 初始化 ui 布局
     */
    protected void initUI(){
        if (getView() == null) {
            return;
        }
        mNormalView = getView().findViewById(R.id.normal_view);
        if (mNormalView == null) {
            throw new IllegalStateException("The subclass of RootActivity must contain a View named 'mNormalView'.");
        }
        if (!(mNormalView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException("mNormalView's ParentView should be a ViewGroup.");
        }
        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(activity, R.layout.view_loading, parent);
        View.inflate(activity, R.layout.view_error, parent);
        View.inflate(activity, R.layout.view_empty, parent);
        mLoadingView = parent.findViewById(R.id.loading_group);
        lvChromeLogo = mLoadingView.findViewById(R.id.lv_load);
        mErrorView = parent.findViewById(R.id.error_group);
        mEmptyView = parent.findViewById(R.id.empty_group);
        tvMsg = parent.findViewById(R.id.tv_err_msg);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        mNormalView.setVisibility(View.VISIBLE);
    }

    /**
     * 判断有无网络
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == NetUtil.NETWORK_WIFI) {
            return true;
        } else if (netMobile == NetUtil.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetUtil.NETWORK_NONE) {
            return false;
        }
        return false;
    }

    @Override
    public void showNormal() {
        if (currentState == NORMAL_STATE) {
            return;
        }
        hideCurrentView();
        currentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String err) {
        if (currentState == ERROR_STATE) {
            return;
        }
        hideCurrentView();
        currentState = ERROR_STATE;
        mErrorView.setVisibility(View.VISIBLE);
        tvMsg.setText(err);
    }

    @Override
    public void showLoading() {
        if (currentState == LOADING_STATE) {
            return;
        }
        hideCurrentView();
        currentState = LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
        lvChromeLogo.startAnim();
    }

    @Override
    public void showEmpty() {
        if (currentState == EMPTY_STATE) {
            return;
        }
        hideCurrentView();
        currentState = EMPTY_STATE;
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void reload() {

    }

    private void hideCurrentView() {
        switch (currentState) {
            case NORMAL_STATE:
                if (mNormalView == null) {
                    return;
                }
                mNormalView.setVisibility(View.GONE);
                break;
            case LOADING_STATE:
                lvChromeLogo.stopAnim();
                mLoadingView.setVisibility(View.GONE);
                break;
            case ERROR_STATE:
                mErrorView.setVisibility(View.GONE);
                break;
            case EMPTY_STATE:
                mEmptyView.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

}
