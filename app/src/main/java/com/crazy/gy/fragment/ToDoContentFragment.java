package com.crazy.gy.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.gy.R;
import com.crazy.gy.adapter.ToDoListAdapter;
import com.crazy.gy.entity.HomeListBean;
import com.crazy.gy.entity.TodoDesBean;
import com.crazy.gy.entity.TodoListBean;
import com.crazy.gy.entity.TodoSection;
import com.crazy.gy.net.RxCallback.OnResultClick;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxRequest.ApiServerImp;
import com.crazy.gy.util.ALog;
import com.crazy.gy.view.DialogText;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoContentFragment extends Fragment {
    private static final String KEY_IS_DONE = "is_done";
    @Bind(R.id.todorecyclerview)
    RecyclerView todorecyclerview;
    @Bind(R.id.refreshdata)
    SwipeRefreshLayout swipeRefreshData;
    private boolean isDone;
    private ApiServerImp mApiServerImp;
    private DialogText mDialog;
    private ToDoListAdapter listAdapter;
    private int PAGE_SIZE = 20;
    private int page = 1;
    private Handler handler = new Handler();
    List<TodoSection> todoSectionList = new ArrayList<>();
    private int donePosition = -1;
    private int deletePosition = -1;

    public ToDoContentFragment() {
        // Required empty public constructor
    }


    public static ToDoContentFragment newInstance(boolean isDone) {

        Bundle args = new Bundle();
        args.putBoolean(KEY_IS_DONE, isDone);
        ToDoContentFragment fragment = new ToDoContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_do_content, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isDone = bundle.getBoolean(KEY_IS_DONE);
        }
        initView();
        initDate(isDone);
        initAdapter();
        return view;
    }

    private void initView() {
        todorecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mApiServerImp = new ApiServerImp();
        mDialog = new DialogText(getActivity(), R.style.MyDialog);
        //设置加载进度的颜色变化值
        swipeRefreshData.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        //设置下拉刷新的监听器
        swipeRefreshData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        todoSectionList.clear();
                        initDate(isDone);
                    }
                }, 2000);
            }
        });
    }

    private void initDate(boolean isDone) {
        mApiServerImp.ToDoListImp(isDone, 1, new OnResultClick<BaseHttpBean>() {
            @Override
            public void success(BaseHttpBean baseHttpBean) {
                if (swipeRefreshData.isRefreshing()) {
                    swipeRefreshData.setRefreshing(false);
                }
                if (baseHttpBean != null) {
                    if (baseHttpBean.getErrorCode() == 0) {
                        TodoListBean mTodoListBean = (TodoListBean) baseHttpBean.getData();
                        todoSectionList = getTodoSectionData(mTodoListBean.getDatas());
                        setData(true, todoSectionList);
                    } else {
                        mDialog.show();
                        showInfo(baseHttpBean.getErrorMsg(), 0);
                    }
                }

            }

            @Override
            public void fail(Throwable throwable) {
                if (swipeRefreshData.isRefreshing()) {
                    swipeRefreshData.setRefreshing(false);
                }
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
                }
            }, 1000);
        }
    }

    /**
     * @param datas
     * @return
     */
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

    private void initAdapter() {
        listAdapter = new ToDoListAdapter(isDone);
        listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_complete://任务完成
                        donePosition = position;
                        doneTodoById(listAdapter.getData().get(position).t.getId());
                        break;
                    case R.id.item_delete://删除任务
                        deletePosition = position;
                        deleteTodoById(listAdapter.getData().get(position).t.getId());
                        break;
                }
            }
        });
        todorecyclerview.setAdapter(listAdapter);
    }

    private void setData(final boolean isFresh, final List<TodoSection> mData) {
        final int size = mData == null ? 0 : mData.size();
        if (isFresh) {
            listAdapter.setNewData(mData);

            if (size < PAGE_SIZE) {
                //第一页如果不够一页就不显示没有更多数据布局
                listAdapter.loadMoreEnd(isFresh);

            } else {
                listAdapter.loadMoreComplete();
            }
        } else {
            if (size > 0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (size < PAGE_SIZE) {
                            //第一页如果不够一页就不显示没有更多数据布局(比如只有十三条数据)
                            listAdapter.loadMoreEnd(isFresh);

                        } else {
                            listAdapter.loadMoreComplete();
                        }
                        listAdapter.addData(mData);
                    }
                }, 1000);

            }

        }


    }

    /**
     * 更新任务完成状态
     *
     * @param id
     */
    private void doneTodoById(int id) {
        mApiServerImp.UpdateDoneToDoImp(id, isDone ? 0 : 1, new OnResultClick<BaseHttpBean>() {
            @Override
            public void success(BaseHttpBean baseHttpBean) {
                if (baseHttpBean != null) {
                    if (baseHttpBean.getErrorCode() == 0) {
                        if (donePosition != -1) {
                            if (listAdapter.getData().get(donePosition - 1).isHeader && (listAdapter.getData().size() == donePosition + 2 || listAdapter.getData().get(donePosition + 1).isHeader)) {
                                listAdapter.getData().remove(donePosition - 1);
                                listAdapter.getData().remove(donePosition - 1);
                                listAdapter.notifyItemRangeRemoved(donePosition - 1, 2);
                            } else {
                                listAdapter.getData().remove(donePosition);
                                listAdapter.notifyItemRemoved(donePosition);
                            }

                        }
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
     * 删除
     *
     * @param id
     */
    private void deleteTodoById(int id) {
        mApiServerImp.DeleteTodo(id, new OnResultClick<BaseHttpBean>() {
            @Override
            public void success(BaseHttpBean baseHttpBean) {
                if (baseHttpBean != null) {
                    if (baseHttpBean.getErrorCode() == 0) {
                        if (listAdapter.getData().get(deletePosition - 1).isHeader && (listAdapter.getData().size() == deletePosition + 2 || listAdapter.getData().get(deletePosition + 1).isHeader)) {
                            listAdapter.getData().remove(deletePosition - 1);
                            listAdapter.getData().remove(deletePosition - 1);
                            listAdapter.notifyItemRangeRemoved(deletePosition - 1, 2);
                        } else {
                            listAdapter.getData().remove(deletePosition);
                            listAdapter.notifyItemRemoved(deletePosition);
                        }

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
