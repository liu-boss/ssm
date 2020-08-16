package com.coderman.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 只控制页面路由
 * @Author zhangyukang
 * @Date 2020/8/16 16:35
 * @Version 1.0
 **/
@Controller
public class ViewController {
    @RequestMapping("/loginPage.do")
    public String loginPage(){return "login";}

    @RequestMapping("/mainPage.do")
    public String mainPage(){return "main";}
}
