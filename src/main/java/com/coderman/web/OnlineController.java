package com.coderman.web;

import com.coderman.common.EasyUIData;
import com.coderman.common.JsonData;
import com.coderman.common.shiro.CurrentUser;
import com.coderman.exception.ParamException;
import com.coderman.util.ShiroContextHolder;
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
@RequestMapping(value = "/system/online")
public class OnlineController {

    @Autowired
    private SessionDAO sessionDAO;

    //当前在线用户
    @RequestMapping(value = "/list.do",method = RequestMethod.POST)
    @ResponseBody
    public EasyUIData<CurrentUser> list() {
        Set<CurrentUser> userList=new HashSet<>();
        Collection<Session> activeSessions = sessionDAO.getActiveSessions();
        for (Session activeSession : activeSessions) {
            //如果没有过期,并且登入系统
            if(activeSession.getAttributeKeys().contains(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)){
                SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection)
                        activeSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                CurrentUser principal = (CurrentUser) simplePrincipalCollection.getPrimaryPrincipal();
                Date lastAccessTime = activeSession.getLastAccessTime();
                Date startTime= activeSession.getStartTimestamp();
                principal.setLastAccessTime(lastAccessTime);
                principal.setStartTime(startTime);
                long timeout = activeSession.getTimeout();
                principal.setTimeout(timeout/1000/60);
                SimpleSession simpleSession= (SimpleSession) activeSession;
                boolean expired = simpleSession.isExpired();
                principal.setExpired(expired);
                userList.add(principal);
            }
        }
        return new EasyUIData<>(userList.size(),new ArrayList<>(userList));
    }

    //踢出系统
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
