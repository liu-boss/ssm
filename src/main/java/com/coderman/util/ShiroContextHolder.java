package com.coderman.util;

import com.coderman.common.shiro.CurrentUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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

    //用户名
    public static String getUsername(){
        return getUser().getUsername();
    }

    //用户ID
    public static Long getIdentity(){
        return getUser().getId();
    }

    //HttpServletRequest请求
    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(sra, true);
        assert sra != null;
        return sra.getRequest();
    }
}
