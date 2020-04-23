package com.crazy.gy.mvc.model;

/**
 * Created on 2020/4/22 17:35
 *
 * @auther super果
 * @annotation
 */
public interface HomeModel {
    void getHomeList(int page,boolean isRefresh,OnHomeListener mlistener);

}
