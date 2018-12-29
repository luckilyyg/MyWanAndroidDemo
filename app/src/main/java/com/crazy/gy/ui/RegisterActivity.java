package com.crazy.gy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crazy.gy.R;
import com.crazy.gy.net.RxCallback.OnResultClick;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxRequest.ApiServerImp;
import com.crazy.gy.util.ALog;
import com.crazy.gy.view.DialogText;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterActivity extends AppCompatActivity {
    @Bind(R.id.tb_register_bar)
    TitleBar tbRegisterBar;
    @Bind(R.id.et_register_phone)
    EditText etRegisterPhone;
    @Bind(R.id.et_register_password1)
    EditText etRegisterPassword1;
    @Bind(R.id.et_register_password2)
    EditText etRegisterPassword2;
    @Bind(R.id.btn_register_commit)
    Button btnRegisterCommit;
    private Context mContext;
    private ApiServerImp mApiServerImp;
    private DialogText mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext = RegisterActivity.this;
        tbRegisterBar.setOnTitleBarListener(new OnTitleBarListener() {

            @Override
            public void onLeftClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }

            @Override
            public void onTitleClick(View v) {
            }

            @Override
            public void onRightClick(View v) {
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

    /**
     * 注册
     */
    @OnClick(R.id.btn_register_commit)
    public void onViewClicked() {
        String userName = etRegisterPhone.getText().toString();
        String psw = etRegisterPassword1.getText().toString();
        String repsw = etRegisterPassword2.getText().toString();
        if (etRegisterPhone.getText().toString().isEmpty() || etRegisterPassword1.getText().toString().isEmpty() || etRegisterPassword2.getText().toString().isEmpty()) {
            mDialog.show();
            showInfo("手机号或密码不能为空", 0);
        } else {
            mDialog.show();
            mDialog.setText("正在注册...", "");
            register(userName, psw, repsw);
        }

    }

    /**
     * @param userName
     * @param psw
     * @param repsw
     */
    private void register(String userName, String psw, String repsw) {
        mApiServerImp.Register(userName, psw, repsw, new OnResultClick<BaseHttpBean>() {
            @Override
            public void success(BaseHttpBean baseHttpBean) {
                if (baseHttpBean != null) {
                    if (baseHttpBean.getErrorCode() == 0) {
                        ALog.e(baseHttpBean.toString());
                        mDialog.show();
                        showInfo("注册成功", 1);
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
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                }
            }, 1000);
        }
    }
}
