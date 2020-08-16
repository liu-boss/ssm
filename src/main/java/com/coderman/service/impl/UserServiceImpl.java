package com.coderman.service.impl;

import com.coderman.common.JsonData;
import com.coderman.exception.ParamException;
import com.coderman.mapper.UserMapper;
import com.coderman.model.User;
import com.coderman.service.UserService;
import com.coderman.util.BeanValidator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @Author zhangyukang
 * @Date 2020/8/16 17:23
 * @Version 1.0
 **/
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 用户登入
     * @param user
     * @return
     * @throws ParamException
     */
    @Override
    public void login(User user) throws ParamException{
        BeanValidator.check(user);
        String username = user.getUsername();
        String password = user.getPassword();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        String exceptionMsg=null;
        try {
            subject.login(token);
        }catch (IncorrectCredentialsException e){
            exceptionMsg="登入密码错误";
        }catch (LockedAccountException e){
            exceptionMsg="用户已被锁定";
        }catch (UnknownAccountException e){
            exceptionMsg="用户不存在";
        }catch (ExcessiveAttemptsException e){
            exceptionMsg="请过十分钟再试";
        }
        //统一抛出之定义异常
        if(null!=exceptionMsg){
            logger.error("user:{},e={}",user,exceptionMsg);
            throw new ParamException(exceptionMsg);
        }
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
