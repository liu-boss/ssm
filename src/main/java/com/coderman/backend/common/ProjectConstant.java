package com.coderman.backend.common;

/**
 * 系统常量
 * @Author zhangyukang
 * @Date 2020/8/26 20:29
 * @Version 1.0
 **/
public interface ProjectConstant {


    /** 默认密码*/
    String DEFAULT_PASSWORD="123456";
    /** 默认性别: 男*/
    String DEFAULT_SEX="0";

    /** 用户是否锁定*/
    String UN_LOCKED="1"; //未锁定
    String LOCKED="0"; //锁定

    /** 模块划分 **/
    String SYSTEM_MODULE="系统模块";
    String MONITOR_MODULE="监控模块";


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

    /** 权限类型*/
    enum MenuType{
        MENU("0","菜单"),
        BUTTON("1","按钮");
        private String value;
        private String desc;
        MenuType(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }
    }


}
