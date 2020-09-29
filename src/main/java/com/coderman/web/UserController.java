package com.coderman.web;

import com.coderman.common.JsonData;
import com.coderman.exception.custom.UserException;
import com.coderman.model.User;
import com.coderman.service.UserService;
import com.coderman.util.BeanValidator;
import com.coderman.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhangyukang
 * @Date 2020/8/11 09:24
 * @Version 1.0
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger=LoggerFactory.getLogger(this.getClass());

    /**
     * 用户登入
     * @param user
     * @return
     * @throws UserException
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public JsonData login(@RequestBody User user) throws UserException {
        BeanValidator.check(user);
        User loginUser=userService.login(user.getUsername(),user.getPassword());
        //user对象转map
        BeanMap map = BeanMap.create(user);
        String token = JwtUtil.createToken(loginUser.getId(), map);
        logger.info("用户:{},成功登入,返回token:{}",loginUser,token);
        return JsonData.success(token);
    }
}
