package com.crazy.gy.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crazy.gy.R;
import com.crazy.gy.entity.UserBean;
import com.crazy.gy.mvp.base.mBaseActivity;
import com.crazy.gy.mvp.contract.LoginContract;
import com.crazy.gy.mvp.presenter.LoginPresenter;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.crazy.gy.mvp.util.Sharedpreferences_Utils;
import com.crazy.gy.mvp.util.toast.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class mLoginActivity extends mBaseActivity implements LoginContract.View {

    @Bind(R.id.toolbar_login)
    Toolbar toolbarLogin;
    @Bind(R.id.et_ensure_username)
    EditText etEnsureUsername;
    @Bind(R.id.et_ensure_password)
    EditText etEnsurePassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.login_group)
    RelativeLayout loginGroup;
    private String username, password;
    LoginPresenter presenter;

    @Override
    protected void initToolbar() {
        setSupportActionBar(toolbarLogin);
        getSupportActionBar().setTitle(getString(R.string.login));
        toolbarLogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_m_login;
    }

    @Override
    protected void initView() {
        presenter = new LoginPresenter(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void loginOk(UserBean userInfo) {
        ToastUtil.show(activity, getString(R.string.login_ok));
        //保存用户登录标记
        Sharedpreferences_Utils.getInstance(mLoginActivity.this).setString(ConstantUtil.USERNAME, userInfo.getUsername());
        Sharedpreferences_Utils.getInstance(mLoginActivity.this).setString(ConstantUtil.PASSWORD, password);
        Sharedpreferences_Utils.getInstance(mLoginActivity.this).setBoolean(ConstantUtil.ISLOGIN, true);
        startActivity(new Intent(mLoginActivity.this, mMainActivity.class));
        finish();
    }

    @Override
    public void loginErr(String info) {
        ToastUtil.show(activity, info);
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

    }

    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (check()) {
                    presenter.login(username, password);
                }
                break;
            case R.id.tv_register:
                break;
        }
    }

    private boolean check() {
        username = etEnsureUsername.getText().toString().trim();
        password = etEnsurePassword.getText().toString().trim();
        if (username.length() < 6 || password.length() < 6) {
            ToastUtil.show(context, getString(R.string.username_incorrect));
            return false;
        }
        return true;
    }
}
