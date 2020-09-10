package com.coderman.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 控制页面路由
 *
 * @Author zhangyukang
 * @Date 2020/8/16 16:35
 * @Version 1.0
 **/
@Controller
public class ViewController {

    /***************************** 监控管理 **************************************/

    //操作日志
    @RequiresPermissions({"monitor:operateLog:view"})
    @RequestMapping(value = "/monitor/operateLogPage.do", method = RequestMethod.GET)
    public String operateLogPage() {
        return "monitor/operateLog";
    }


    //登入信息
    @RequiresPermissions({"monitor:loginLog:view"})
    @RequestMapping(value = "/monitor/loginLogPage.do", method = RequestMethod.GET)
    public String loginLogPage() {
        return "monitor/loginLog";
    }

    //数据源监控
    @RequiresPermissions({"monitor:druid:view"})
    @RequestMapping(value = "/monitor/druidPage.do", method = RequestMethod.GET)
    public String druidPage() {
        return "redirect:/druid/index.html";
    }


    //在线管理
    @RequiresPermissions({"monitor:online:view"})
    @RequestMapping(value = "/monitor/onlinePage.do", method = RequestMethod.GET)
    public String onlinePage() {
        return "monitor/online";
    }


    /*****************************系统管理页面***********************************/

    //菜单管理
    @RequiresPermissions({"system:menu:view"})
    @RequestMapping(value = "/system/menuPage.do", method = RequestMethod.GET)
    public String menuPage() {
        return "system/menu";
    }

    //部门管理
    @RequiresPermissions({"system:dept:view"})
    @RequestMapping(value = "/system/deptPage.do", method = RequestMethod.GET)
    public String deptPage() {
        return "system/dept";
    }

    //角色管理
    @RequiresPermissions({"system:role:view"})
    @RequestMapping(value = "/system/rolePage.do", method = RequestMethod.GET)
    public String rolePage() {
        return "system/role";
    }

    //用户管理
    @RequiresPermissions({"system:user:view"})
    @RequestMapping(value = "/system/userPage.do", method = RequestMethod.GET)
    public String userPage() {
        return "system/user";
    }

    /********************************* 公共页面 ***************************************/

    //登入页面
    @RequestMapping(value = "/system/loginPage.do", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    //后台主页面
    @RequestMapping(value = "/system/mainPage.do", method = RequestMethod.GET)
    public String mainPage() {
        return "main";
    }

    //未授权页
    @RequestMapping(value = "/system/unauthorizedPage.do", method = RequestMethod.GET)
    public String unauthorizedPage() {
        return "error/unauthorized";
    }

}
