package com.coderman.util;

import com.coderman.model.User;

/**
 * 保存 web上下文的信息
 *
 * @Author zhangyukang
 * @Date 2020/8/5 20:04
 * @Version 1.0
 **/
public class WebUtil {

    private static ThreadLocal<User> currentUserHolder = new ThreadLocal<>();

    public static User getUser() {
        return currentUserHolder.get();
    }

    public static void setUser(User user) {
        currentUserHolder.set(user);
    }

    public static Integer getSubject() {
        return getUser().getId();
    }

    public static String getUsername() {
        return getUser().getUsername();
    }

    public static void remove() {
        currentUserHolder.remove();
    }

}
