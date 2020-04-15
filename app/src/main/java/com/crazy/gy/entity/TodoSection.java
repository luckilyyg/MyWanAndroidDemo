package com.crazy.gy.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 作者：wangwnejie on 2018/8/9 14:33
 * 邮箱：wangwenjie1303@stnts.com
 */
public class TodoSection extends SectionEntity<TodoDesBean> {

    public TodoSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public TodoSection(TodoDesBean todoBean) {
        super(todoBean);
    }

    @Override
    public String toString() {
        return "TodoSection{" +
                "isHeader=" + isHeader +
                ", t=" + t +
                ", header='" + header + '\'' +
                '}';
    }
}
