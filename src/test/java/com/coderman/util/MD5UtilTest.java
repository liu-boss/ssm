package com.coderman.util;

import org.junit.Test;

/**
 * @Author zhangyukang
 * @Date 2020/8/11 09:35
 * @Version 1.0
 **/
public class MD5UtilTest {

    @Test
    public void encrypt() {
        String password = MD5Util.encrypt("zhangyukang");
        System.out.println(password);
    }
}
