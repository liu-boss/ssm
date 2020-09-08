package com.coderman.web.system;

import com.coderman.common.EasyUIData;
import com.coderman.common.JsonData;
import com.coderman.common.shiro.CurrentUser;
import com.coderman.exception.ParamException;
import com.coderman.service.OnlineService;
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
    private OnlineService onlineService;

    /**
     * 当前在线用户
     * @return
     */
    @RequestMapping(value = "/list.do",method = RequestMethod.POST)
    @ResponseBody
    public EasyUIData<CurrentUser> list() {
        List<CurrentUser> list = onlineService.list();
        return new EasyUIData<>(list.size(), list);
    }

    /**
     * 踢出用户
     * @param usernameList
     * @return
     */
    @RequiresPermissions({"system:online:forceLogout"})
    @RequestMapping(value = "/forceLogout.do",method = RequestMethod.POST)
    @ResponseBody
    public JsonData forceLogout(@RequestParam("nameList") String  usernameList) {
        onlineService.forceLogout(usernameList);
        return JsonData.success();
    }

    /**
     * 踢出指定用户
     * @param username
     * @return
     */
    @RequiresPermissions({"system:online:forceLogoutByUsername"})
    @RequestMapping(value = "forceLogoutByUsername",method = RequestMethod.POST)
    public JsonData forceLogoutByUsername(@RequestParam("username") String username){
        onlineService.forceLogout(username);
        return JsonData.success();
    }
}
