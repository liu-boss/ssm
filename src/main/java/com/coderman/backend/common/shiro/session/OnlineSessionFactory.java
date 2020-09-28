package com.coderman.backend.common.shiro.session;

import com.coderman.backend.util.HttpUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author zhangyukang
 * @Date 2020/9/28 11:59
 * @Version 1.0
 **/
public class OnlineSessionFactory implements SessionFactory {

    @Override
    public Session createSession(SessionContext initData) {
        OnlineSession session = new OnlineSession();
        if (initData instanceof WebSessionContext) {
            WebSessionContext sessionContext = (WebSessionContext) initData;
            HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();
            if (request != null) {
                session.setHost(HttpUtil.getIpAddr(request));
                session.setBrowser(HttpUtil.getOsOrBrowser(1));
                session.setIp(HttpUtil.getIpAddr(request));
                session.setLocation(HttpUtil.getCityInfo(request));
            }
        }
        return session;
    }
}
