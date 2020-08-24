package com.coderman.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author zhangyukang
 * @Date 2020/8/24 19:26
 * @Version 1.0
 **/
@Controller
public class ViewController {

    @RequestMapping(value = "/userPage.do", method = RequestMethod.GET)
    public String userPage() { return "/user/list"; }

    @RequestMapping(value = "/mainPage.do", method = RequestMethod.GET)
    public String mainPage() { return "main"; }

    @RequestMapping(value = "/loginPage.do", method = RequestMethod.GET)
    public String loginPage() { return "login";
    }
}
