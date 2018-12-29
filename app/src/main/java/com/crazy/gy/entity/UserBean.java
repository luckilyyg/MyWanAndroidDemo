package com.crazy.gy.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 作者：Administrator
 * 时间：2018/12/28
 * 功能：
 *  * "data": {
 *  * "collectIds": [],
 *  * "email": "",
 *  * "icon": "",
 *  * "id": 5760,
 *  * "password": "123456",
 *  * "type": 0,
 *  * "username": "13799862666"
 *  * },
 *  * "errorCode": 0,
 *  * "errorMsg": ""
 */
public class UserBean implements Serializable {
    private int id;
    private String username;
    private String password;
    private String icon;
    private int type;
    private List<Integer> collectIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return icon == null ? "" : icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Integer> getCollectIds() {
        if (collectIds == null) {
            return new ArrayList<>();
        }
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }
}
