package com.coderman.util;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @Author zhangyukang
 * @Date 2020/9/16 09:57
 * @Version 1.0
 **/
public class JWTUtilTest {

    @Test
    public void createToken() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("role","admin");
        String token = JWTUtil.createToken("zhangyukang", map);
        System.out.println(token);
    }
}
