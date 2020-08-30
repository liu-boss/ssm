package com.coderman.common.shiro;

/**
 * 当前登入系统的用户
 * @Author zhangyukang
 * @Date 2020/8/26 23:06
 * @Version 1.0
 **/
public class CurrentUser {
    //用户名
    public String username;

    public Long id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
