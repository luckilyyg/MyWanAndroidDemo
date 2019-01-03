package com.crazy.gy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.crazy.gy.MainActivity;
import com.crazy.gy.R;
import com.crazy.gy.entity.UserBean;
import com.crazy.gy.net.RxCallback.OnResultClick;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxRequest.ApiServerImp;
import com.crazy.gy.util.Sharedpreferences_Utils;
import com.crazy.gy.view.ClearEditText;
import com.crazy.gy.view.DialogText;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Toast.makeText(MainActivity.this, "userId:", Toast.LENGTH_LONG).show();
 * 账号：xiabibi
 * 密码：123456
 */
public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.tb_login_bar)
    TitleBar tbLoginBar;
    @Bind(R.id.et_login_phone)
    ClearEditText etLoginPhone;
    @Bind(R.id.et_login_password)
    ClearEditText etLoginPassword;
    @Bind(R.id.btn_login_commit)
    Button btnLoginCommit;
    private TitleBar mTitleBar;
    private Context mContext;
    private ApiServerImp mApiServerImp;
    private DialogText mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = LoginActivity.this;
        tbLoginBar.setOnTitleBarListener(new OnTitleBarListener() {

            @Override
            public void onLeftClick(View v) {
            }

            @Override
            public void onTitleClick(View v) {
            }

            @Override
            public void onRightClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        initView();
        initData();

    }

    private void initView() {
        mApiServerImp = new ApiServerImp();
        mDialog = new DialogText(mContext, R.style.MyDialog);
    }

    private void initData() {
    }

    @OnClick(R.id.btn_login_commit)
    public void onViewClicked() {
        String userName = etLoginPhone.getText().toString();
        String psw = etLoginPassword.getText().toString();
        if (etLoginPhone.getText().toString().isEmpty() || etLoginPassword.getText().toString().isEmpty()) {
            mDialog.show();
            showInfo("手机号或密码不能为空", 0);
        } else {
            mDialog.show();
            mDialog.setText("正在登录...", "");
            login(userName, psw);
        }
    }

    /**
     * @param userName
     * @param psw
     */
    private void login(String userName, String psw) {
        mApiServerImp.Login(userName, psw, new OnResultClick<BaseHttpBean>() {
            @Override
            public void success(BaseHttpBean baseHttpBean) {
                if (baseHttpBean != null) {
                    if (baseHttpBean.getErrorCode() == 0) {
                        int userId = ((UserBean) baseHttpBean.getData()).getId();
                        String niName = ((UserBean) baseHttpBean.getData()).getUsername();
                        Sharedpreferences_Utils.getInstance(mContext).setInt("userId", ((UserBean) baseHttpBean.getData()).getId());
                        Sharedpreferences_Utils.getInstance(mContext).setString("niName", ((UserBean) baseHttpBean.getData()).getUsername());
                        Sharedpreferences_Utils.getInstance(mContext).setString("userPassword", ((UserBean) baseHttpBean.getData()).getPassword());
                        showInfo("登录成功", 1);
                    } else {
                        showInfo(baseHttpBean.getErrorMsg(), 0);
                    }
                }
            }

            @Override
            public void fail(Throwable throwable) {
                showInfo(throwable.getMessage(), 0);
            }
        });
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
                    if (is == 1) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }
            }, 1000);
        }
    }
}
