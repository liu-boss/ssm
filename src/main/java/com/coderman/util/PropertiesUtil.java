package com.coderman.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取配置文件的工具类
 * @Author zhangyukang
 * @Date 2020/7/18 14:50
 * @Version 1.0
 **/
public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties props;

    private PropertiesUtil(){}

    static {
        props = new Properties();
        try {
            props.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static String getProperty(String key) {
        String value = props.getProperty(key.trim());
        return value == null ? null : value.trim();
    }

    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value == null ? defaultValue : value.trim();
    }

}
