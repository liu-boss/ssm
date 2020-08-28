package com.coderman.common;

/**
 * 系统常量
 * @Author zhangyukang
 * @Date 2020/8/26 20:29
 * @Version 1.0
 **/
public interface ProjectConstant {

    /** 性别*/
    int SEX_MAN=0; //男
    int SEX_FEMALE=1; //女

    /** 用户是否锁定*/
    String UN_LOCKED="1"; //未锁定
    String LOCKED="0"; //锁定

    /** 用户类型 */
    enum UserType {
        ADMIN(0,"超级管理员"),
        USER(1,"普通用户");
        private int value;
        private String desc;
        UserType(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }
        public int getValue() {
            return value;
        }
    }
}
