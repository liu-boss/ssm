package com.coderman.backend.monitor.controller;

import com.coderman.backend.common.EasyUIData;
import com.coderman.backend.common.JsonData;
import com.coderman.backend.common.ProjectConstant;
import com.coderman.backend.common.aop.Operate;
import com.coderman.backend.common.shiro.CurrentUser;
import com.coderman.backend.exception.ParamException;
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
     * @return
     */
    @RequestMapping(value = "/list.do",method = RequestMethod.POST)
    @ResponseBody
    public EasyUIData<CurrentUser> list() {
        Set<CurrentUser> userList=new HashSet<>();
        Collection<Session> activeSessions = sessionDAO.getActiveSessions();
        for (Session activeSession : activeSessions) {
            //最新访问时间
            Date lastAccessTime = activeSession.getLastAccessTime();
            //开始访问时间
            Date startTime= activeSession.getStartTimestamp();
            //如果没有过期,并且登入系统
            if(activeSession.getAttributeKeys().contains(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)){

                SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection) activeSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);

                CurrentUser principal = (CurrentUser) simplePrincipalCollection.getPrimaryPrincipal();
                principal.setLastAccessTime(lastAccessTime);
                principal.setStartTime(startTime);
                //会话过期时间
                long timeout = activeSession.getTimeout();
                principal.setTimeout(timeout/1000/60);
                SimpleSession simpleSession= (SimpleSession) activeSession;
                boolean expired = simpleSession.isExpired();
                //是否过期
                principal.setExpired(expired);
                userList.add(principal);
            }
        }
        return new EasyUIData<>(userList.size(), new ArrayList<>(userList));
    }

    /**
     * 踢出用户
     * @param usernameList
     * @return
     */
    @Operate(operateModule = ProjectConstant.MONITOR_MODULE,operateDesc = "踢出用户")
    @RequiresPermissions({"system:online:forceLogout"})
    @RequestMapping(value = "/forceLogout.do",method = RequestMethod.POST)
    @ResponseBody
    public JsonData forceLogout(@RequestParam("nameList") String  usernameList) {
        String[] names = usernameList.split(",");
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        if(names.length>0){
            for (String username : names) {
                if(ShiroContextHolder.getUser().getUsername().equals(username)){
                    throw new ParamException("您无法将自己踢出系统!");
                }else {
                    for(Session session:sessions){
                        Object attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                        if(attribute!=null){
                            SimplePrincipalCollection simplePrincipalCollection =
                                    (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                            CurrentUser principal = (CurrentUser) simplePrincipalCollection.getPrimaryPrincipal();
                            if(username.equals(principal.getUsername())){
                                //设置session立即失效，即将其踢出系统
                                session.setTimeout(0);
                                sessionDAO.delete(session);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return JsonData.success();
    }

}
