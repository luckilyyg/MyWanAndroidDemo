package com.crazy.gy.mvp.presenter;

import com.crazy.gy.entity.TodoDesBean;
import com.crazy.gy.entity.TodoListBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.AddTodoContract;
import com.crazy.gy.mvp.contract.TodoContract;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxHttp.RxHttp;
import com.crazy.gy.net.RxHttp.RxRetrofit;
import com.crazy.gy.net.RxRequest.ApiServer;

import io.reactivex.functions.Consumer;

/**
 * Created on 2020/4/13 14:16
 *
 * @auther super果
 * @annotation
 */
public class AddTodoPresenter extends BasePresenter<AddTodoContract.View> implements AddTodoContract.Presenter {
    private AddTodoContract.View view;

    public AddTodoPresenter(AddTodoContract.View view) {
        this.view = view;
    }


    @Override
    public void addTodo(String title, String content, String date) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.addToDoNew(title, content, date, "0"), new Consumer<BaseHttpBean<TodoDesBean>>() {
            @Override
            public void accept(BaseHttpBean<TodoDesBean> datasBeanBaseResp) throws Exception {
                if (datasBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                    view.addTodoErr(datasBeanBaseResp.getErrorMsg());
                } else if (datasBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                    view.addTodoOk((TodoDesBean) datasBeanBaseResp.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.addTodoErr(throwable.getMessage());
            }
        });


    }
}
