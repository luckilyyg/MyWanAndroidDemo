package com.crazy.gy.mvp.presenter;

import com.crazy.gy.entity.TodoDesBean;
import com.crazy.gy.entity.TodoListBean;
import com.crazy.gy.entity.WxPublicListBean;
import com.crazy.gy.mvp.base.contract.BasePresenter;
import com.crazy.gy.mvp.contract.TodoContract;
import com.crazy.gy.mvp.contract.WxDetailContract;
import com.crazy.gy.mvp.util.ConstantUtil;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxHttp.RxHttp;
import com.crazy.gy.net.RxHttp.RxRetrofit;
import com.crazy.gy.net.RxRequest.ApiServer;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created on 2020/4/10 16:18
 *
 * @auther super果
 * @annotation
 */
public class TodoPresenter extends BasePresenter<TodoContract.View> implements TodoContract.Presenter {
    private TodoContract.View view;
    private boolean isRefresh = true;
    private int todoPage = -1;
    private boolean isDone;

    public TodoPresenter(TodoContract.View view) {
        this.view = view;
    }


    @Override
    public void onRefresh() {
        isRefresh = true;
        if (todoPage != -1) {
            getTodoList(isDone, 1);
        }
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        if (todoPage != -1) {
            todoPage++;
            getTodoList(isDone, todoPage);
        }
    }

    @Override
    public void getTodoList(boolean isDone, int page) {
        this.isDone = isDone;
        this.todoPage = page;
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(isDone ? mLoginServer.getDoneList(page) : mLoginServer.getNotDoList(page), new Consumer<BaseHttpBean<TodoListBean>>() {
            @Override
            public void accept(BaseHttpBean datasBeanBaseResp) throws Exception {
                if (datasBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                    view.getTodoResultErr(datasBeanBaseResp.getErrorMsg());
                } else if (datasBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                    view.getTodoResultOK((TodoListBean) datasBeanBaseResp.getData(), isRefresh);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.getTodoResultErr(throwable.getMessage());
            }
        });
    }

    @Override
    public void deleteTodoById(int id) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendNoRequest(mLoginServer.deleteToDo(id), new Consumer<BaseHttpBean>() {
            @Override
            public void accept(BaseHttpBean datasBeanBaseResp) throws Exception {
                if (datasBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                    view.deleteTodoErr(datasBeanBaseResp.getErrorMsg());
                } else if (datasBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                    view.deleteTodoOk();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.deleteTodoErr(throwable.getMessage());
            }
        });
    }


    @Override
    public void doneTodoById(int id) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.updateDoneToDo(id, isDone ? 0 : 1), new Consumer<BaseHttpBean<TodoDesBean>>() {
            @Override
            public void accept(BaseHttpBean datasBeanBaseResp) throws Exception {
                if (datasBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                    view.doneTodoErr(datasBeanBaseResp.getErrorMsg());
                } else if (datasBeanBaseResp.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                    view.doneTodoOk((TodoDesBean) datasBeanBaseResp.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                view.doneTodoErr(throwable.getMessage());
            }
        });
    }


}
