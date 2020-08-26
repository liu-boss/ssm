package com.coderman.util;

import org.apache.shiro.crypto.hash.SimpleHash;

public class MD5Util {

    public static String encryptPassword(String password, String salt) {
        SimpleHash hash = new SimpleHash("md5", password, salt , 1024);
        return hash.toHex();
    }
}
