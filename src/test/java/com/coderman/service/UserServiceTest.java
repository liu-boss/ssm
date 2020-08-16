package com.coderman.service;

import com.coderman.mapper.UserMapper;
import com.coderman.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/8/11 09:40
 * @Version 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/application-dao.xml", "classpath:spring/application-service.xml"})
public class UserServiceTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void listAll() {
        List<User> users = userMapper.listAll();
        System.out.println(users);
    }

    @Test
    public void listAll2(){
        List<User> users = userService.listAll();
        System.out.println(users);
    }
}
