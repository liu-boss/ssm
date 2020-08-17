package com.coderman.web;

import com.coderman.common.JsonData;
import com.coderman.exception.ParamException;
import com.coderman.model.User;
import com.coderman.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zhangyukang
 * @Date 2020/8/16 17:19
 * @Version 1.0
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    @ResponseBody
    public JsonData login(@RequestBody User user) throws ParamException {
        userService.login(user);
        return JsonData.success();
    }

    @RequestMapping(value = "/unauthorized.do",method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("user:unauthorized")
    public JsonData unauthorized(){
        return JsonData.success();
    }

    @RequestMapping(value = "/role.do",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles("admin")
    public JsonData role(){
        return JsonData.success();
    }

}
