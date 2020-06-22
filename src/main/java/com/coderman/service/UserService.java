package com.coderman.service;

import com.coderman.entity.User;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/6/22 18:14
 * @Version 1.0
 **/
public interface UserService {

    List<User> list();

    User findById(Integer id);
}
