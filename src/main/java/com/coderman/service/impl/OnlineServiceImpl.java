package com.coderman.service.impl;

import com.coderman.common.shiro.CurrentUser;
import com.coderman.exception.ParamException;
import com.coderman.service.OnlineService;
import com.coderman.util.ShiroContextHolder;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author zhangyukang
 * @Date 2020/9/8 08:23
 * @Version 1.0
 **/
@Service
public class OnlineServiceImpl implements OnlineService {

    @Autowired
    private SessionDAO sessionDAO;

    @Override
    public List<CurrentUser> list() {
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
        return new ArrayList<>(userList);
    }

    @Override
    public void forceLogout(String usernameList) {
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
    }
}
