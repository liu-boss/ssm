package com.coderman.service.impl;

import com.coderman.exception.custom.UserException;
import com.coderman.mapper.UserMapper;
import com.coderman.model.User;
import com.coderman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/6/22 18:14
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public List<User> listAll() {
        return userMapper.listAll();
    }

    @Override
    public User login(String username, String password) {
        User user=userMapper.selectByName(username);
        if(user==null){
            throw new UserException("用户名不存在");
        }
        if(!password.equals(user.getPassword())){
            throw new UserException("登入密码错误");
        }
        user.setPassword(null);
        return user;
    }
}
