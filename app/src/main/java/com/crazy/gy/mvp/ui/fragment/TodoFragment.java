package com.crazy.gy.mvp.ui.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.gy.R;

import com.crazy.gy.entity.TodoDesBean;
import com.crazy.gy.entity.TodoListBean;
import com.crazy.gy.entity.TodoSection;
import com.crazy.gy.mvp.base.BaseFragment;
import com.crazy.gy.mvp.contract.TodoContract;
import com.crazy.gy.mvp.presenter.TodoPresenter;
import com.crazy.gy.mvp.presenter.WxDetailPresenter;
import com.crazy.gy.mvp.ui.activity.TodoMainActivity;
import com.crazy.gy.mvp.ui.adapter.ToDoListAdapter;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.crazy.gy.mvp.util.toast.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodoFragment extends BaseFragment implements TodoContract.View, BaseQuickAdapter.OnItemChildClickListener {
    private static final String KEY_IS_DONE = "is_done";
    @Bind(R.id.rv_todo)
    RecyclerView rvTodo;
    @Bind(R.id.normal_view)
    SmartRefreshLayout normalView;
    private TodoPresenter presenter;
    private ToDoListAdapter adapter;
    private List<TodoSection> todoSectionList;
    private boolean isDone;
    private int donePosition = -1;
    private int deletePosition = -1;
    private View emptyView;

    public TodoFragment() {
        // Required empty public constructor
    }

    public static TodoFragment newInstance(boolean isDone) {

        Bundle args = new Bundle();
        args.putBoolean(KEY_IS_DONE, isDone);
        TodoFragment fragment = new TodoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initUI() {
        super.initUI();
        normalView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.onRefresh();
                refreshLayout.finishRefresh(1000);
            }
        });
        normalView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.onLoadMore();
                refreshLayout.finishLoadMore(1000);
            }
        });
        showLoading();
        rvTodo.setLayoutManager(new LinearLayoutManager(activity));
        emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.view_empty, null);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_todo;
    }

    @Override
    protected void initData() {
        presenter = new TodoPresenter(this);
        todoSectionList = new ArrayList<>();
        if (getArguments() != null) {
            isDone = getArguments().getBoolean(KEY_IS_DONE);
            presenter.getTodoList(isDone, 1);
        }
        adapter = new ToDoListAdapter(isDone, todoSectionList);
        rvTodo.setAdapter(adapter);
        adapter.setOnItemChildClickListener(this);
        adapter.notifyDataSetChanged();
    }

    /**
     * 回到顶部
     */
    public void scrollToTop() {
        rvTodo.smoothScrollToPosition(0);
    }


    private List<TodoSection> getTodoSectionData(List<TodoDesBean> datas) {
        List<TodoSection> todoSections = new ArrayList<>();
        LinkedHashSet<String> dates = new LinkedHashSet<>();
        for (TodoDesBean todoDesBean : datas) {
            dates.add(todoDesBean.getDateStr());//"dateStr":"2018-08-18"
        }
        for (String date : dates) {
            TodoSection todoSectionHead = new TodoSection(true, date);
            todoSections.add(todoSectionHead);
            for (TodoDesBean todoDesBean : datas) {
                if (TextUtils.equals(date, todoDesBean.getDateStr())) {
                    TodoSection todoSectionContent = new TodoSection(todoDesBean);
                    todoSections.add(todoSectionContent);
                }
            }
        }
        return todoSections;
    }

    @Override
    public void getTodoResultOK(TodoListBean todoListBeans, boolean hasRefresh) {
        showNormal();
        if (adapter == null) {
            return;
        }
        if (hasRefresh) {
            todoSectionList.clear();
            todoSectionList.addAll(getTodoSectionData(todoListBeans.getDatas()));
            adapter.replaceData(getTodoSectionData(todoListBeans.getDatas()));

            if (todoSectionList.size() == 0) {
                showEmpty();
            }

        } else {
            if (getTodoSectionData(todoListBeans.getDatas()).size() > 0) {
                todoSectionList.addAll(getTodoSectionData(todoListBeans.getDatas()));
                adapter.addData(getTodoSectionData(todoListBeans.getDatas()));
            } else {
                ToastUtil.show(activity, getString(R.string.load_more_no_data));
            }
        }


    }

    @Override
    public void getTodoResultErr(String err) {
        showError(err);
    }

    private void showDialog(final int position) {
        Log.e(TAG, "position: " + position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.delete_todo);
        builder.setMessage(R.string.sure_delete_todo);
        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletePosition = position;
                presenter.deleteTodoById(adapter.getData().get(position).t.getId());
            }
        });
        builder.show();
    }

    @Override
    public void deleteTodoOk() {
        if (deletePosition != -1) {
            if (adapter.getData().get(deletePosition - 1).isHeader) {
                if (adapter.getData().size() > deletePosition + 1) {
                    if (adapter.getData().get(deletePosition + 1).isHeader) {
                        adapter.getData().remove(deletePosition - 1);
                        adapter.getData().remove(deletePosition - 1);
                        adapter.notifyItemRangeRemoved(deletePosition - 1, 2);
                        noneData();
                    } else {
                        adapter.getData().remove(deletePosition);
                        adapter.notifyItemRemoved(deletePosition);
                    }
                } else {
                    if (adapter.getData().get(deletePosition - 1).isHeader) {
                        adapter.getData().remove(deletePosition - 1);
                        adapter.getData().remove(deletePosition - 1);
                        adapter.notifyItemRangeRemoved(deletePosition - 1, 2);
                        noneData();
                    }
                }


            } else {
                adapter.getData().remove(deletePosition);
                adapter.notifyItemRemoved(deletePosition);
            }
        }

        ToastUtil.show(activity, getString(R.string.delete_todo_success));
    }

    private void noneData() {
        if (adapter.getData().size() == 0) {
            showEmpty();
        }
    }

    @Override
    public void deleteTodoErr(String err) {
        showError(err);
    }

    @Override
    public void doneTodoOk(TodoDesBean todoDesBean) {
        if (donePosition != -1) {
            if (adapter.getData().get(donePosition - 1).isHeader) {
                if (adapter.getData().size() > donePosition + 1) {
                    if (adapter.getData().get(donePosition + 1).isHeader) {
                        adapter.getData().remove(donePosition - 1);
                        adapter.getData().remove(donePosition - 1);
                        adapter.notifyItemRangeRemoved(donePosition - 1, 2);
                        noneData();
                    } else {
                        adapter.getData().remove(donePosition);
                        adapter.notifyItemRemoved(donePosition);
                    }
                } else {
                    if (adapter.getData().get(donePosition - 1).isHeader) {
                        adapter.getData().remove(donePosition - 1);
                        adapter.getData().remove(donePosition - 1);
                        adapter.notifyItemRangeRemoved(donePosition - 1, 2);
                        noneData();
                    }
                }


            } else {
                adapter.getData().remove(donePosition);
                adapter.notifyItemRemoved(donePosition);
            }
        }

        ((TodoMainActivity) getActivity()).updateDoneOrCancelData(todoDesBean, isDone ? 0 : 1);
        ToastUtil.show(activity, getString(isDone ? R.string.notdo_todo_success : R.string.done_todo_success));
    }


    @Override
    public void doneTodoErr(String err) {
        showError(err);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapters, View view, int position) {
        switch (view.getId()) {
            case R.id.item_complete://任务完成
                donePosition = position;
                presenter.doneTodoById(adapter.getData().get(position).t.getId());
                break;
            case R.id.item_delete://删除任务
                showDialog(position);
                break;
            default:
                break;
        }
    }

    /**
     * 新增一条记录
     *
     * @param todoDesBean
     */
    public void updateAddTodoData(TodoDesBean todoDesBean) {
        List<TodoSection> todoSections = adapter.getData();
        for (int i = 0; i < todoSections.size(); i++) {
            TodoSection todoSection = todoSections.get(i);
            if (todoSection.isHeader && TextUtils.equals(todoSection.header, todoDesBean.getDateStr())) {
                TodoSection section = new TodoSection(todoDesBean);
                adapter.getData().add(i + 1, section);
                adapter.notifyItemInserted(i + 1);
                rvTodo.scrollToPosition(i + 1);
                return;
            }
        }
        showNormal();
        TodoSection sectionHead = new TodoSection(true, todoDesBean.getDateStr());
        adapter.getData().add(0, sectionHead);
        TodoSection section = new TodoSection(todoDesBean);
        adapter.getData().add(1, section);
        adapter.notifyItemRangeInserted(0, 2);
        adapter.notifyDataSetChanged();
        rvTodo.scrollToPosition(0);
    }
}
