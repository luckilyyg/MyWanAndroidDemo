package com.crazy.gy.net.RxRequest;

import com.crazy.gy.entity.BannerListBean;
import com.crazy.gy.entity.HomeListBean;
import com.crazy.gy.entity.KnowledgeListBean;
import com.crazy.gy.entity.ProjectContentListBean;
import com.crazy.gy.entity.ProjectListBean;
import com.crazy.gy.entity.TodoDesBean;
import com.crazy.gy.entity.TodoListBean;
import com.crazy.gy.entity.UserBean;
import com.crazy.gy.net.RxCallback.OnResultClick;
import com.crazy.gy.net.RxHttp.BaseHttpBean;
import com.crazy.gy.net.RxHttp.RxHttp;
import com.crazy.gy.net.RxHttp.RxRetrofit;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 作者：Administrator
 * 时间：2018/4/16
 * 功能：
 */
public class ApiServerImp {


    /**
     * 注册
     *
     * @param username
     * @param psw
     * @param repsw
     * @param result
     */
    public void Register(String username, String psw, String repsw, final OnResultClick<BaseHttpBean> result) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getRegister(username, psw, repsw), new Consumer<BaseHttpBean<UserBean>>() {
            @Override
            public void accept(BaseHttpBean baseHttpBean) throws Exception {
                result.success(baseHttpBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                result.fail(throwable);
            }
        });
    }

    /**
     * 登录
     *
     * @param username
     * @param psw
     * @param result
     */
    public void Login(String username, String psw, final OnResultClick<BaseHttpBean> result) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getLogin(username, psw), new Consumer<BaseHttpBean<UserBean>>() {
            @Override
            public void accept(BaseHttpBean baseHttpBean) throws Exception {
                result.success(baseHttpBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                result.fail(throwable);
            }
        });
    }

    /**
     * 首页轮播
     *
     * @param result
     */
    public void BannerListImp(final OnResultClick<BaseHttpBean> result) {
        //MD5加密
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getBannerList(), new Consumer<BaseHttpBean<List<BannerListBean>>>() {
            @Override
            public void accept(BaseHttpBean baseHttpBean) throws Exception {
                result.success(baseHttpBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                result.fail(throwable);
            }
        });
    }

    /**
     * 首页文章列表
     *
     * @param result
     */
    public void HomeListImp(int page, final OnResultClick<BaseHttpBean> result) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getHomeList(page), new Consumer<BaseHttpBean<HomeListBean>>() {
            @Override
            public void accept(BaseHttpBean baseHttpBean) throws Exception {
                result.success(baseHttpBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                result.fail(throwable);
            }
        });
    }

    /**
     * 知识体系
     *
     * @param result
     */
    public void KnowLedgeListImp(final OnResultClick<BaseHttpBean> result) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getKnowLedgeList(), new Consumer<BaseHttpBean<List<KnowledgeListBean>>>() {
            @Override
            public void accept(BaseHttpBean baseHttpBean) throws Exception {
                result.success(baseHttpBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                result.fail(throwable);
            }
        });
    }

    /***
     * 项目
     * @param result
     */
    public void ProjectListImp(final OnResultClick<BaseHttpBean> result) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getProjectList(), new Consumer<BaseHttpBean<List<ProjectListBean>>>() {
            @Override
            public void accept(BaseHttpBean baseHttpBean) throws Exception {
                result.success(baseHttpBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                result.fail(throwable);
            }
        });
    }


    /**
     * 项目内容
     *
     * @param result
     */
    public void ProjectContentListImp(int curpage, int id, final OnResultClick<BaseHttpBean> result) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.getProjectContentList(curpage, id), new Consumer<BaseHttpBean<ProjectContentListBean>>() {
            @Override
            public void accept(BaseHttpBean baseHttpBean) throws Exception {
                result.success(baseHttpBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                result.fail(throwable);
            }
        });
    }

    /**
     * 待完成、已完成列表
     *
     * @param isDone
     * @param page
     * @param result
     */
    public void ToDoListImp(boolean isDone, int page, final OnResultClick<BaseHttpBean> result) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(isDone ? mLoginServer.getDoneList(page) : mLoginServer.getNotDoList(page), new Consumer<BaseHttpBean<TodoListBean>>() {
            @Override
            public void accept(BaseHttpBean baseHttpBean) throws Exception {
                result.success(baseHttpBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                result.fail(throwable);
            }
        });
    }


    /**
     * 新增一条Todo
     *
     * @param title
     * @param content
     * @param date
     * @param result
     */
    public void AddTodoImp(String title, String content, String date, String type, final OnResultClick<BaseHttpBean> result) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.addToDo(title, content, date, type), new Consumer<BaseHttpBean>() {
            @Override
            public void accept(BaseHttpBean baseHttpBean) throws Exception {
                result.success(baseHttpBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                result.fail(throwable);
            }
        });
    }


    /**
     * 更新完成状态Todo
     *
     * @param id
     * @param status
     * @param result
     */
    public void UpdateDoneToDoImp(int id, int status, final OnResultClick<BaseHttpBean> result) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.updateDoneToDo(id, status), new Consumer<BaseHttpBean<TodoDesBean>>() {
            @Override
            public void accept(BaseHttpBean baseHttpBean) throws Exception {
                result.success(baseHttpBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                result.fail(throwable);
            }
        });
    }


    /**
     * 删除Todo
     *
     * @param id
     */
    public void DeleteTodo(int id, final OnResultClick<BaseHttpBean> result) {
        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
        RxHttp.sendRequest(mLoginServer.deleteToDo(id), new Consumer<BaseHttpBean>() {
            @Override
            public void accept(BaseHttpBean baseHttpBean) throws Exception {
                result.success(baseHttpBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //网络请求失败
                result.fail(throwable);
            }
        });
    }

    /**
     * 导航数据
     *
     * @param result
     */
//    public void NaviList(final OnResultClick<BaseHttpBean> result) {
//        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
//        RxHttp.sendRequest(mLoginServer.getNaviList(), new Consumer<BaseHttpBean>() {
//            @Override
//            public void accept(BaseHttpBean baseHttpBean) throws Exception {
//                result.success(baseHttpBean);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                //网络请求失败
//                result.fail(throwable);
//            }
//        });
//    }

    /**
     * 取消收藏
     *
     * @param id
     * @param originId
     * @param result
     */
//    public void RemoveCollectArticle(int id, int originId, final OnResultClick<BaseHttpBean> result) {
//        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
//        RxHttp.sendRequest(mLoginServer.getRemoveCollectArticle(id, originId), new Consumer<BaseHttpBean>() {
//            @Override
//            public void accept(BaseHttpBean baseHttpBean) throws Exception {
//                result.success(baseHttpBean);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                //网络请求失败
//                result.fail(throwable);
//            }
//        });
//    }

    /**
     * 收藏文章
     *
     * @param id
     * @param result
     */
//    public void AddCollectArticle(int id, final OnResultClick<BaseHttpBean> result) {
//        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
//        RxHttp.sendRequest(mLoginServer.getAddCollectArticle(id), new Consumer<BaseHttpBean>() {
//            @Override
//            public void accept(BaseHttpBean baseHttpBean) throws Exception {
//                result.success(baseHttpBean);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                //网络请求失败
//                result.fail(throwable);
//            }
//        });
//    }

    /**
     * 未完成 Todo 列表
     */
//    public void GetListNotDoList(int page, final OnResultClick<BaseHttpBean> result) {
//        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
//        RxHttp.sendRequest(mLoginServer.getListNotDoList(page), new Consumer<BaseHttpBean<TodoListBean>>() {
//            @Override
//            public void accept(BaseHttpBean baseHttpBean) throws Exception {
//                result.success(baseHttpBean);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                //网络请求失败
//                result.fail(throwable);
//            }
//        });
//    }

    /**
     * 已完成 Done 列表
     */
//    public void GetListDoneList(int page, final OnResultClick<BaseHttpBean> result) {
//        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
//        RxHttp.sendRequest(mLoginServer.getListDoneList(page), new Consumer<BaseHttpBean<TodoListBean>>() {
//            @Override
//            public void accept(BaseHttpBean baseHttpBean) throws Exception {
//                result.success(baseHttpBean);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                //网络请求失败
//                result.fail(throwable);
//            }
//        });
//    }

    /**
     * 封装待完成、已完成列表
     *
     * @param isDone
     * @param page
     * @param result
     */
//    public void GetList(boolean isDone, int page, final OnResultClick<BaseHttpBean> result) {
//        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
//        RxHttp.sendRequest(isDone ? mLoginServer.getListDoneList(page) : mLoginServer.getListNotDoList(page), new Consumer<BaseHttpBean<TodoListBean>>() {
//            @Override
//            public void accept(BaseHttpBean baseHttpBean) throws Exception {
//                result.success(baseHttpBean);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                //网络请求失败
//                result.fail(throwable);
//            }
//        });
//    }

    /**
     * 新增一条Todo
     *
     * @param title
     * @param content
     * @param date
     * @param result
     */
//    public void AddTodo(String title, String content, String date, String type, final OnResultClick<BaseHttpBean> result) {
//        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
//        RxHttp.sendRequest(mLoginServer.addtodo(title, content, date, type), new Consumer<BaseHttpBean>() {
//            @Override
//            public void accept(BaseHttpBean baseHttpBean) throws Exception {
//                result.success(baseHttpBean);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                //网络请求失败
//                result.fail(throwable);
//            }
//        });
//    }

    /**
     * 仅更新完成状态Todo
     *
     * @param id
     * @param status
     * @param result
     */
//    public void DoneTodo(int id, int status, final OnResultClick<BaseHttpBean> result) {
//        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
//        RxHttp.sendRequest(mLoginServer.donetodo(id, status), new Consumer<BaseHttpBean<TodoDesBean>>() {
//            @Override
//            public void accept(BaseHttpBean baseHttpBean) throws Exception {
//                result.success(baseHttpBean);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                //网络请求失败
//                result.fail(throwable);
//            }
//        });
//    }

    /**
     * 删除
     *
     * @param id
     */
//    public void DeleteTodo(int id, final OnResultClick<BaseHttpBean> result) {
//        ApiServer mLoginServer = RxRetrofit.getInstance().create(ApiServer.class);
//        RxHttp.sendRequest(mLoginServer.deletetodo(id), new Consumer<BaseHttpBean>() {
//            @Override
//            public void accept(BaseHttpBean baseHttpBean) throws Exception {
//                result.success(baseHttpBean);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                //网络请求失败
//                result.fail(throwable);
//            }
//        });
//    }


}