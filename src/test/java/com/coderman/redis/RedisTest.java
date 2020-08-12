package com.coderman.redis;

import com.coderman.model.User;
import com.coderman.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jws.soap.SOAPBinding;
import java.util.Date;

/**
 * @Author zhangyukang
 * @Date 2020/8/12 08:36
 * @Version 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/application-redis.xml"})
public class RedisTest {

    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    public RedisUtil redisUtil;

    @Test
    public void test(){
        System.out.println(redisTemplate);
    }

    @Test
    public void test2(){
        boolean ok = redisUtil.set("item", "test");
        Object result = redisUtil.get("item");
        System.out.println(ok);
        System.out.println(result);
    }

}
