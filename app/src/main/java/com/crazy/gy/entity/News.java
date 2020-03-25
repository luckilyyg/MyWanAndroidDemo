package com.crazy.gy.entity;

/**
 * Created on 2020/3/17 15:26
 *
 * @auther super果
 * @annotation
 */
public class News {

    private String content;

    public News(String content) {
        this.content = content;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
