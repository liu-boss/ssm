package com.coderman.service;

import com.coderman.model.User;

/**
 * @Author zhangyukang
 * @Date 2020/8/26 17:08
 * @Version 1.0
 **/
public interface UserService {
    User findByUsername(String username); //根据用户名查找
    void login(String username, String password); //用户登入
}
