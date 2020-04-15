package com.crazy.gy.mvp.contract;

import com.crazy.gy.entity.TodoDesBean;
import com.crazy.gy.entity.TodoListBean;
import com.crazy.gy.mvp.base.contract.BasePre;
import com.crazy.gy.mvp.base.contract.BaseView;

import java.util.List;


public class TodoContract {

    public interface View extends BaseView {
        /**
         * 获取成功 失败
         */
        void getTodoResultOK(TodoListBean todoListBeans, boolean hasRefresh);

        void getTodoResultErr(String err);


        /**
         * 删除成功
         */
        void deleteTodoOk();

        void deleteTodoErr(String err);

        /**
         * 更新任务成功
         */
        void doneTodoOk(TodoDesBean todoDesBean);

        void doneTodoErr(String err);
    }

    public interface Presenter extends BasePre<View> {

        void onRefresh();

        void onLoadMore();

        void getTodoList(boolean isDone, int page);

        void deleteTodoById(int id);

        void doneTodoById(int id);

    }

}
