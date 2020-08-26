package com.coderman.service.impl;

import com.coderman.exception.ParamException;
import com.coderman.mapper.UserMapper;
import com.coderman.model.User;
import com.coderman.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhangyukang
 * @Date 2020/8/26 17:09
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private Logger logger=LoggerFactory.getLogger(this.getClass());

    @Override
    public User findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public void login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        String exceptionMessage=null;
        try {
            subject.login(token);
        }catch (UnknownAccountException e){
            exceptionMessage="账户不存在";
        }catch (CredentialsException e){
            exceptionMessage="密码不匹配";
        }catch (ExcessiveAttemptsException e){
            exceptionMessage="账户已被限制登入,请10分钟后再试";
        }catch (AuthenticationException e) {
            logger.warn("user login fail: user=>{},message=>{}",username,e);
        }
        if(null!=exceptionMessage){
            throw new ParamException(exceptionMessage);
        }
    }
}
