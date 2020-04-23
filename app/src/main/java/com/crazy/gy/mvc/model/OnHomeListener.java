package com.crazy.gy.mvc.model;

import com.crazy.gy.entity.HomeListBean;

/**
 * Created on 2020/4/22 17:34
 *
 * @auther super果
 * @annotation
 */
public interface OnHomeListener {
    void getHomepageListOk(HomeListBean dataBean, boolean isRefresh);

    void getHomepageListErr(String info);
}
