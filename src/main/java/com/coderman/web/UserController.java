package com.coderman.web;

import com.coderman.entity.User;
import com.coderman.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/6/22 18:06
 * @Version 1.0
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                       @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        PageHelper.startPage(page,size);
        List<User> list = userService.list();
        User user = userService.findById(100);
        logger.info("user={}",user);
        return new PageInfo<>(list);
    }
}
