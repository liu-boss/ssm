package com.coderman.backend.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.junit.Test;

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
        String username="zhangyukang";//管理员账户
        String password = MD5Util.encryptPassword("123456",salt);//管理员密码
        System.out.println("username:"+username);
        System.out.println("password:"+password);
        System.out.println("salt:"+salt);
    }
}
