package com.coderman.service;

import com.coderman.common.shiro.CurrentUser;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/9/8 08:20
 * @Version 1.0
 **/
public interface OnlineService {
    /**
     * 获取当前的在线用户
     * @return
     */
    List<CurrentUser> list();


    /**
     * 踢出指定的在线用户
     * @param usernameList 用户名的拼串
     */
    void forceLogout(String usernameList);
}
