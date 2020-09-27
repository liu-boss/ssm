package com.coderman.backend.monitor.controller;

import com.coderman.backend.common.EasyUIData;
import com.coderman.backend.common.JsonData;
import com.coderman.backend.common.ProjectConstant;
import com.coderman.backend.common.aop.Operate;
import com.coderman.backend.common.shiro.CurrentUser;
import com.coderman.backend.exception.ParamException;
import com.coderman.backend.util.HttpUtil;
import com.coderman.backend.util.ShiroContextHolder;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author zhangyukang
 * @Date 2020/8/31 19:37
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = "/backend/system/online")
public class OnlineController {

    @Autowired
    private SessionDAO sessionDAO;

    /**
     * 当前登入用户
     *
     * @return
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.POST)
    @ResponseBody
    public EasyUIData<CurrentUser> list() {
        Set<CurrentUser> userList = new HashSet<>();
        Collection<Session> activeSessions = sessionDAO.getActiveSessions();
        for (Session activeSession : activeSessions) {
            buildOnlineList(userList, activeSession);
        }
        return new EasyUIData<>(userList.size(), new ArrayList<>(userList));
    }

    /**
     * 组装在线用户列表
     *
     * @param userList
     * @param activeSession
     */
    private void buildOnlineList(Set<CurrentUser> userList, Session activeSession) {
        //最新访问时间
        final Date lastAccessTime = activeSession.getLastAccessTime();
        //开始访问时间
        final Date startTime = activeSession.getStartTimestamp();
        //sessionId
        final String sessionId = activeSession.getId().toString();

        SimpleSession simpleSession = (SimpleSession) activeSession;
        //是否过期
        boolean expired = simpleSession.isExpired();
        if (activeSession.getAttributeKeys().contains(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)) {
            //如果没有过期,并且登入系统
            SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection) activeSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            CurrentUser principal = (CurrentUser) simplePrincipalCollection.getPrimaryPrincipal();
            principal.setLastAccessTime(lastAccessTime);
            principal.setStartTime(startTime);
            long timeout = activeSession.getTimeout();
            principal.setTimeout(timeout / 1000 / 60);
            principal.setSessionId(sessionId);
            principal.setExpired(expired);
            principal.setOnline(true);
            userList.add(principal);

        } else {
            HttpServletRequest httpServletRequest = ShiroContextHolder.getHttpServletRequest();
            //匿名session
            CurrentUser currentUser = new CurrentUser();
            currentUser.setSessionId(sessionId);
            currentUser.setLastAccessTime(lastAccessTime);
            currentUser.setStartTime(startTime);
            currentUser.setExpired(expired);

            currentUser.setHost(HttpUtil.getIpAddr(httpServletRequest));
            currentUser.setLocation(HttpUtil.getCityInfo(httpServletRequest));
            currentUser.setUsername("[游客" + sessionId.substring(0, 4) + "]");
            currentUser.setOnline(false);
            userList.add(currentUser);
        }
    }

    /**
     * 踢出用户
     *
     * @param sessionIdList
     * @return
     */
    @Operate(operateModule = ProjectConstant.MONITOR_MODULE, operateDesc = "踢出用户")
    @RequiresPermissions({"system:online:forceLogout"})
    @RequestMapping(value = "/forceLogout.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData forceLogout(@RequestParam("sessionIdList") String sessionIdList) {
        String[] sessionIds = sessionIdList.split(",");
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        doForceLogout(sessionIds, sessions);
        return JsonData.success();
    }

    /**
     * 执行踢出操作
     * @param sessionIds
     * @param sessions
     */
    private void doForceLogout(String[] sessionIds, Collection<Session> sessions) {
        if (sessionIds.length > 0) {
            for (String sessionId : sessionIds) {
                final String currentSessionId = ShiroContextHolder.getUser().getSessionId();
                if (!"".equals(currentSessionId)&&currentSessionId.equals(sessionId)) {
                    throw new ParamException("您无法将自己踢出系统!");
                } else {
                    Session session = sessionDAO.readSession(sessionId);
                    if(session!=null){
                        sessionDAO.delete(session);
                    }
                }
            }
        }

    }

}
