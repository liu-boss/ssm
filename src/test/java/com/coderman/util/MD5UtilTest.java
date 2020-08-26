package com.coderman.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author zhangyukang
 * @Date 2020/8/11 09:35
 * @Version 1.0
 **/
public class MD5UtilTest {

    @Test
    public void encrypt() {
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        String salt = randomNumberGenerator.nextBytes().toHex();
        String password = MD5Util.encryptPassword("123456",salt);
        System.out.println(password);
        System.out.println(salt);
    }
}
