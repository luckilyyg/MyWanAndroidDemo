package com.crazy.gy.mvp.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.crazy.gy.R;
import com.crazy.gy.entity.TodoDesBean;
import com.crazy.gy.mvp.base.mBaseActivity;
import com.crazy.gy.mvp.contract.AddTodoContract;
import com.crazy.gy.mvp.presenter.AddTodoPresenter;
import com.crazy.gy.mvp.util.toast.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTodoActivity extends mBaseActivity implements AddTodoContract.View {

    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @Bind(R.id.todo_name)
    TextInputEditText todoName;
    @Bind(R.id.todo_des)
    TextInputEditText todoDes;
    @Bind(R.id.todo_date)
    TextView todoDate;
    @Bind(R.id.save_todo)
    Button saveTodo;
    private AddTodoPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_todo;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(toolbarCommon);
        getSupportActionBar().setTitle(getString(R.string.add_todo));
        toolbarCommon.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        todoDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));

        presenter = new AddTodoPresenter(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @OnClick({R.id.todo_date, R.id.save_todo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.todo_date:
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        todoDate.setText(String.format("%d-%d-%d", year, month + 1, dayOfMonth));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
                break;
            case R.id.save_todo:
                todoName.setError(null);
                if (TextUtils.isEmpty(todoName.getText())) {
                    todoName.setError(getString(R.string.input_todo_name_toast));
                    todoName.setFocusable(true);
                    todoName.setFocusableInTouchMode(true);
                    todoName.requestFocus();
                    return;
                }

                presenter.addTodo(todoName.getText().toString(), todoDes.getText().toString(), todoDate.getText().toString());

                break;
        }
    }

    @Override
    public void addTodoOk(TodoDesBean todoDesBean) {
        ToastUtil.show(activity, getString(R.string.add_todo_success));
        Intent intent = new Intent();
        intent.putExtra("add_todo", todoDesBean);
        setResult(2, intent);
        finish();
    }

    @Override
    public void addTodoErr(String err) {
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

    }
}
