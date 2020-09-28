package com.coderman.backend.common.shiro.session;

import org.apache.shiro.session.mgt.SimpleSession;

/**
 * @Author zhangyukang
 * @Date 2020/9/28 11:57
 * @Version 1.0
 **/
public class OnlineSession extends SimpleSession {
    public static enum OnlineStatus {
        on_line("在线"), hidden("隐身"), force_logout("强制退出"),
        off_line("离线");
        private final String info;
        private OnlineStatus(String info) {
            this.info = info;
        }
        public String getInfo() {
            return info;
        }
    }
    private String browser; //用户浏览器类型
    private OnlineStatus status = OnlineStatus.on_line; //在线状态
    private String ip; //用户登录时系统IP
    private String location;
    private String OS;

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public OnlineStatus getStatus() {
        return status;
    }

    public void setStatus(OnlineStatus status) {
        this.status = status;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
