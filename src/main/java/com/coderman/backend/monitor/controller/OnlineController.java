package com.coderman.backend.monitor.controller;

import com.coderman.backend.common.EasyUIData;
import com.coderman.backend.common.JsonData;
import com.coderman.backend.common.ProjectConstant;
import com.coderman.backend.common.aop.Operate;
import com.coderman.backend.common.shiro.CurrentUser;
import com.coderman.backend.common.shiro.session.OnlineSession;
import com.coderman.backend.exception.ParamException;
import com.coderman.backend.util.ShiroContextHolder;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
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
     *
     * @return
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.POST)
    @ResponseBody
    public EasyUIData<CurrentUser> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "rows", defaultValue = "20") Integer rows) {
        Set<CurrentUser> userList = new HashSet<>();
        Collection<Session> activeSessions = sessionDAO.getActiveSessions();
        for (Session activeSession : activeSessions) {
            buildOnlineList(userList, activeSession);
        }
        //分页
        List<CurrentUser> list = page(new ArrayList<>(userList), page, rows);
        return new EasyUIData<>(userList.size(), list);
    }

    /** 分页显示 */
    public static <T> List<T> page(List<T> dataList,int pageNo,int pageSize){
        int startNum = (pageNo-1)* pageSize+1 ;                     //起始截取数据位置
        if(startNum > dataList.size()){
            return null;
        }
        List<T> res = new ArrayList<>();
        int rum = dataList.size() - startNum;
        if(rum < 0){
            return null;
        }
        if(rum == 0){                                               //说明正好是最后一个了
            int index = dataList.size() -1;
            res.add(dataList.get(index));
            return res;
        }
        if(rum / pageSize >= 1){                                    //剩下的数据还够1页，返回整页的数据
            for(int i=startNum;i<startNum + pageSize;i++){          //截取从startNum开始的数据
                res.add(dataList.get(i-1));
            }
            return res;
        }else if(rum / pageSize == 0){                 //不够一页，直接返回剩下数据
            for(int j = startNum ;j<=dataList.size();j++){
                res.add(dataList.get(j-1));
            }
            return res;
        }else{
            return null;
        }
    }

    /**
     * 组装在线用户列表
     *
     * @param userList
     * @param activeSession
     */
    private void buildOnlineList(Set<CurrentUser> userList, Session activeSession) {
        OnlineSession onlineSession=null;
        if(activeSession instanceof OnlineSession){
             onlineSession= (OnlineSession) activeSession;
        }
        assert onlineSession != null;
        //最新访问时间
        final Date lastAccessTime = activeSession.getLastAccessTime();
        //开始访问时间
        final Date startTime = activeSession.getStartTimestamp();
        //sessionId
        final String sessionId = activeSession.getId().toString();
        //地理位置
        final String location = onlineSession.getLocation();
        //浏览器
        final String browser = onlineSession.getBrowser();
        //ip
        final String ip = onlineSession.getIp();
        //是否过期
        boolean expired = onlineSession.isExpired();
        CurrentUser currentUser=new CurrentUser();
        currentUser.setSessionId(sessionId);
        currentUser.setStartTime(startTime);
        currentUser.setLastAccessTime(lastAccessTime);
        currentUser.setHost(ip);
        currentUser.setExpired(expired);
        currentUser.setLocation(location);
        currentUser.setBrowser(browser);
        if (activeSession.getAttributeKeys().contains(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)) {
            //如果没有过期,并且登入系统
            SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection) activeSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            CurrentUser principal = (CurrentUser) simplePrincipalCollection.getPrimaryPrincipal();
            currentUser.setStatus(OnlineSession.OnlineStatus.on_line.getInfo());
            currentUser.setUsername(principal.getUsername());
        } else {
            //匿名session
            currentUser.setUsername("[游客:"+sessionId.substring(0,4)+"]");
            currentUser.setStatus(OnlineSession.OnlineStatus.off_line.getInfo());
        }
        userList.add(currentUser);
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
