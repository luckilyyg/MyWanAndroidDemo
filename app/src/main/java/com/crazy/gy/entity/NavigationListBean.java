package com.crazy.gy.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 2020/3/24 15:05
 *
 * @auther super果
 * @annotation
 */
public class NavigationListBean implements Serializable {
    /**
     * "articles": [],
     * "cid": 272,
     * "name": "常用网站"
     */

    private List<FeedArticleBean> articles;
    private int cid;
    private String name;

    public List<FeedArticleBean> getArticles() {
        return articles;
    }

    public void setArticles(List<FeedArticleBean> articles) {
        this.articles = articles;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "NavigationListBean{" +
                "articles=" + articles +
                ", cid=" + cid +
                ", name='" + name + '\'' +
                '}';
    }
}
