package com.coderman.util;

import com.coderman.common.shiro.CurrentUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @Author zhangyukang
 * @Date 2020/8/26 23:00
 * @Version 1.0
 **/
public class ShiroContextHolder {

    //当前登入用户
    public static CurrentUser getUser(){
        Subject subject = SecurityUtils.getSubject();
        return (CurrentUser) subject.getPrincipal();
    }

    public static Long getIdentity(){
        return getUser().getId();
    }
}
