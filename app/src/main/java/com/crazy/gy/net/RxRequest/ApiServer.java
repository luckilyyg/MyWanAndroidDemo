package com.crazy.gy.net.RxRequest;


import com.crazy.gy.entity.BannerListBean;
import com.crazy.gy.entity.CollectBean;
import com.crazy.gy.entity.DemoDetailListBean;
import com.crazy.gy.entity.DemoTitleBean;
import com.crazy.gy.entity.HomeListBean;
import com.crazy.gy.entity.HotBean;
import com.crazy.gy.entity.HotKeyBean;
import com.crazy.gy.entity.KnowledgeDetailListBean;
import com.crazy.gy.entity.KnowledgeListBean;
import com.crazy.gy.entity.ArticleBean;
import com.crazy.gy.entity.NavigationListBean;
import com.crazy.gy.entity.ProjectContentListBean;
import com.crazy.gy.entity.ProjectListBean;
import com.crazy.gy.entity.TodoDesBean;
import com.crazy.gy.entity.TodoListBean;
import com.crazy.gy.entity.UserBean;
import com.crazy.gy.entity.WxListBean;
import com.crazy.gy.entity.WxPublicListBean;
import com.crazy.gy.net.RxHttp.BaseHttpBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 作者：Administrator
 * 时间：2018/4/16
 * 功能：
 */
public interface ApiServer {
    /**
     * 注册
     * //www.wanandroid.com/user/register
     */
    @POST("user/register")
    Flowable<BaseHttpBean<UserBean>> getRegister(@Query("username") String phone, @Query("password") String pwd, @Query("repassword") String repwd);

    /**
     * 登录
     * http://www.wanandroid.com/user/login
     */
    @POST("user/login")
    Flowable<BaseHttpBean<UserBean>> getLogin(@Query("username") String phone, @Query("password") String pwd);

    /**
     * 首页文章列表
     * http://www.wanandroid.com/article/list/0/json
     * /article/list/{page}/json
     */
    @GET("article/list/{page}/json/")
    Flowable<BaseHttpBean<HomeListBean>> getHomeList(@Path("page") int page);

    /**
     * 首页的轮播
     * http: //www.wanandroid.com/banner/json
     */
    @GET("banner/json/")
    Flowable<BaseHttpBean<List<BannerListBean>>> getBannerList();

    /**
     * 获取 热门词
     */
    @GET("/friend/json")
    Flowable<BaseHttpBean<List<HotBean>>> getHotList();

    /**
     * 知识体系
     * http://www.wanandroid.com/tree/json
     */
    @GET("tree/json/")
    Flowable<BaseHttpBean<List<KnowledgeListBean>>> getKnowLedgeList();

    /**
     * 单个知识体系列表
     */
    @GET("article/list/{page}/json")
    Flowable<BaseHttpBean<KnowledgeDetailListBean>> getSystemDetailList(@Path("page") int page, @Query("cid") int id);
    /**
     * 知识体系内容
     * http://www.wanandroid.com/article/list/0/json?cid=60
     *
     * @param page
     * @param cid
     * @return
     */
    @GET("article/list/{page}/json/")
    Flowable<BaseHttpBean<ArticleBean>> getKnowLedgeContentList(@Path("page") int page, @Query("cid") int cid);


    /**
     * 获取 微信公众号列表
     * @return
     */
    @GET("/wxarticle/chapters/json")
    Flowable<BaseHttpBean<List<WxListBean>>> getWXList();

    /**
     * 获取 微信公众号详细信息列表数据
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Flowable<BaseHttpBean<WxPublicListBean>> getWXDetailList(@Path("page") int page , @Path("id")int id);

    /**
     * 获取项目 列表
     */
    @GET("project/tree/json")
    Flowable<BaseHttpBean<List<DemoTitleBean>>> getDemoTitleList();
    /**
     * 获取 项目详细信息列表数据
     */
    @GET("project/list/{page}/json")
    Flowable<BaseHttpBean<DemoDetailListBean>> getDemoDetailList(@Path("page") int page , @Query("cid")int id);

    /**
     * 项目
     * http://www.wanandroid.com/project/tree/json
     */
    @GET("project/tree/json")
    Flowable<BaseHttpBean<List<ProjectListBean>>> getProjectList();
    /**
     * 获取 我的收藏列表
     */
    @GET("lg/collect/list/{page}/json")
    Flowable<BaseHttpBean<CollectBean>> getCollectionList(@Path("page") int page);

    /**
     * 导航
     * https://www.wanandroid.com/navi/json
     * @return
     */
    @GET("navi/json")
    Flowable<BaseHttpBean<List<NavigationListBean>>> getNavigationList();
    /**
     * 项目内容
     * http://www.wanandroid.com/project/list/1/json?cid=294
     *
     * @param cid
     * @return
     */
    @GET("/project/list/{curpage}/json")
    Flowable<BaseHttpBean<ProjectContentListBean>> getProjectContentList(@Path("curpage") int curpage, @Query("cid") int cid);


    /**
     * 未完成 Todo 列表
     * http://www.wanandroid.com/lg/todo/listnotdo/类型/json/页码
     *
     * @param page
     * @return
     */
    @POST("/lg/todo/listnotdo/0/json/{page}")
    Flowable<BaseHttpBean<TodoListBean>> getNotDoList(@Path("page") int page);


    /**
     * 完成 Todo 列表
     * http://www.wanandroid.com/lg/todo/listdone/类型/json/页码
     *
     * @param page
     * @return
     */
    @POST("/lg/todo/listdone/0/json/{page}")
    Flowable<BaseHttpBean<TodoListBean>> getDoneList(@Path("page") int page);

    /**
     * 新增一条Todo
     * http://www.wanandroid.com/lg/todo/add/json
     *
     * @param title
     * @param content
     * @param date
     * @return
     */
    @POST("/lg/todo/add/json")
    Flowable<BaseHttpBean> addToDo(@Query("title") String title, @Query("content") String content, @Query("date") String date, @Query("type") String type);

    /**
     * 更新状态Todo
     * http://www.wanandroid.com/lg/todo/done/80/json
     *
     * @param id
     * @param status
     * @return
     */
    @POST("/lg/todo/done/{id}/json")
    Flowable<BaseHttpBean<TodoDesBean>> updateDoneToDo(@Path("id") int id, @Query("status") int status);

    /**
     * 删除Todo
     * http://www.wanandroid.com/lg/todo/delete/83/json
     *
     * @param id
     * @return
     */
    @POST("/lg/todo/delete/{id}/json")
    Flowable<BaseHttpBean> deleteToDo(@Path("id") int id);

    /**
     * 收藏文章
     *
     * @param id
     * @return
     */
    @POST("/lg/collect/{id}/json")
    Flowable<BaseHttpBean> collectArticle(@Path("id") int id);


    /**
     * 取消收藏
     *
     * @param id
     * @param originId http://www.wanandroid.com/lg/uncollect_originId/2333/json
     * @return
     */
    @POST("/lg/uncollect_originId/{id}/json")
    Flowable<BaseHttpBean> removeCollectArticle(@Path("id") int id);

    /**
     * 我的收藏
     *
     * @param page
     * @return
     */
    @GET("/lg/collect/list/{page}/json")
    Flowable<BaseHttpBean<ArticleBean>> getMyCollectList(@Path("page") int page);


    /**
     * 获取 搜索热词
     * @return
     */
    @GET("/hotkey/json")
    Flowable<BaseHttpBean<List<HotKeyBean>>> getHitKeyBean();

    /**
     *
     * 查询搜索结果
     * @param page
     * @param key
     * @return
     */
    @POST("/article/query/{page}/json")
    Flowable<BaseHttpBean<HomeListBean>> getSearechResults(@Path("page") int page ,@Query("k")String key);
    /**
     * 退出登录
     *
     * @return
     */
    @GET("/user/logout/json")
    Flowable<BaseHttpBean> logout();

}
