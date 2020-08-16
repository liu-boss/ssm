package com.coderman.service;

import com.coderman.exception.ParamException;
import com.coderman.model.User;

/**
 * @Author zhangyukang
 * @Date 2020/8/16 17:23
 * @Version 1.0
 **/
public interface UserService {
    public void login(User user) throws ParamException;   //用户登入
    public User findByUsername(String username); //根据用户名查询
}
