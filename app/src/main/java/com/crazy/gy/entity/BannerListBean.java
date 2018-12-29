package com.crazy.gy.entity;

import java.io.Serializable;

/**
 * 作者：Administrator
 * 时间：2018/4/17
 * 功能：
 */
public class BannerListBean implements Serializable {
    /**
     * {
     "data": [{
     "desc": "一起来做个App吧",
     "id": 10,
     "imagePath": "http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",
     "isVisible": 1,
     "order": 0,
     "title": "一起来做个App吧",
     "type": 0,
     "url": "http://www.wanandroid.com/blog/show/2"
     }, {
     "desc": "",
     "id": 5,
     "imagePath": "http://www.wanandroid.com/blogimgs/acc23063-1884-4925-bdf8-0b0364a7243e.png",
     "isVisible": 1,
     "order": 3,
     "title": "微信文章合集",
     "type": 1,
     "url": "http://www.wanandroid.com/blog/show/6"
     }],
     "errorCode": 0,
     "errorMsg": ""
     }
      */

    private String desc;
    private  int id;
    private String imagePath;
    private  int isVisible;
    private  int order;
    private String title;
    private int type;
    private String url;

    public String getDesc() {
        return desc == null ? "" : desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath == null ? "" : imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
