package com.crazy.gy.mvp.contract;

import com.crazy.gy.entity.TodoDesBean;
import com.crazy.gy.mvp.base.contract.BasePre;
import com.crazy.gy.mvp.base.contract.BaseView;

/**
 * Created on 2020/4/13 14:12
 *
 * @auther super果
 * @annotation
 */
public class AddTodoContract {

    public interface View extends BaseView {
        void addTodoOk(TodoDesBean todoDesBean);

        void addTodoErr(String info);
    }

    public interface Presenter extends BasePre<AddTodoContract.View> {

        void addTodo(String title, String content, String date);

    }
}
