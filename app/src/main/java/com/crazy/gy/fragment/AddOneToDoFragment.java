package com.crazy.gy.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crazy.gy.R;
import com.crazy.gy.net.RxCallback.OnResultClick;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxRequest.ApiServerImp;
import com.crazy.gy.util.ALog;
import com.crazy.gy.util.DateDialog;
import com.crazy.gy.view.DialogText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOneToDoFragment extends DialogFragment {


    @Bind(R.id.edt_title)
    EditText edtTitle;
    @Bind(R.id.tv_choosetime)
    TextView tvChoosetime;
    @Bind(R.id.mytodo)
    EditText mytodo;
    @Bind(R.id.btn_save)
    Button btnSave;
    private static AddOneToDoFragment dialogFramgent;
    private ApiServerImp mApiServerImp;
    private DialogText mDialog;

    public AddOneToDoFragment() {
        // Required empty public constructor
    }

    public static synchronized AddOneToDoFragment newInstance() {
        if (dialogFramgent == null) {
            dialogFramgent = new AddOneToDoFragment();
        }
        return dialogFramgent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_one_to_do, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        mApiServerImp = new ApiServerImp();
        mDialog = new DialogText(getActivity(), R.style.MyDialog);
    }

    private void initData() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.MyDialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_choosetime, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_choosetime:
                DateDialog dateDialog = new DateDialog(getActivity(), "选择时间日期", DateDialog.MODE_2, new DateDialog.InterfaceDateDialog() {
                    @Override
                    public void getTime(String date) {
                        tvChoosetime.setText(date);
                    }
                });
                dateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dateDialog.show();
                break;
            case R.id.btn_save:
                String title = edtTitle.getText().toString();
                String content = mytodo.getText().toString();
                String date = tvChoosetime.getText().toString();
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content) || TextUtils.isEmpty(date)) {
                    mDialog.show();
                    showInfo("标题、内容、时间不能为空", 0);
                } else {
                    mDialog.show();
                    mDialog.setText("正在保存...", "");
                    mApiServerImp.AddTodoImp(title, content, date, "0", new OnResultClick<BaseHttpBean>() {
                        @Override
                        public void success(BaseHttpBean baseHttpBean) {
                            if (baseHttpBean != null) {
                                if (baseHttpBean.getErrorCode() == 0) {
                                    showInfo("保存成功", 1);
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

                break;
        }
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
                        dialogFramgent =null;
                    }
                }
            }, 1000);
        }
    }
}
