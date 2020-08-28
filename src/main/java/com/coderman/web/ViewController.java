package com.coderman.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 只控制页面路由
 * @Author zhangyukang
 * @Date 2020/8/16 16:35
 * @Version 1.0
 **/
@Controller
public class ViewController {

    //角色管理
    @RequestMapping(value = "/rolePage.do",method = RequestMethod.GET)
    public String rolePage(){ return "system/role";}

    //用户管理
    @RequestMapping(value = "/userPage.do",method = RequestMethod.GET)
    public String userPage(){ return "system/user"; }

    //登入页面
    @RequestMapping(value = "/loginPage.do",method = RequestMethod.GET)
    public String loginPage(){
        return "login";
    }

    //后台主页面
    @RequestMapping(value = "/mainPage.do",method = RequestMethod.GET)
    public String mainPage(){return "main";}

    //未授权页
    @RequestMapping(value = "/unauthorizedPage.do",method = RequestMethod.GET)
    public String unauthorizedPage(){return "error/unauthorized";}
}
