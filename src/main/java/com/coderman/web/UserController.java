package com.coderman.web;

import com.coderman.common.JsonData;
import com.coderman.exception.BizException;
import com.coderman.model.User;
import com.coderman.service.UserService;
import com.coderman.util.BeanValidator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @RequestMapping(value = "/list.do",method = RequestMethod.GET)
    @ResponseBody
    public JsonData list(@RequestParam(value = "page") Integer page,
                         @RequestParam(value = "size") Integer size){
        PageHelper.startPage(page,size);
        if(page==4){
            throw new BizException("page=4 不合法");
        }
        List<User> users=userService.listAll();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return JsonData.success(pageInfo);
    }


    @RequestMapping(value = "/upload.do",method = RequestMethod.POST)
    @ResponseBody
    public JsonData upload(MultipartFile file){
        System.out.println(file);
        return JsonData.success(file.getOriginalFilename());
    }

    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    @ResponseBody
    public JsonData login(@RequestBody User user){
        BeanValidator.check(user);
        if("zhang".equals(user.getUsername())){
            throw new BizException("用户名不存在");
        }
        if("yu".equals(user.getUsername())){
            int a=9/0;
        }
        return JsonData.success(user);
    }
}
