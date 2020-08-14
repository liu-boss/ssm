package com.coderman.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

/**
 * @Author zhangyukang
 * @Date 2020/8/14 11:11
 * @Version 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/application-fastdfs.xml"})
public class FastdfsTest {

    @Autowired
    private FdfsUtil fdfsUtil;

    @Test
    public void test(){
        try {
            String url = fdfsUtil.uploadFile(new File("D:\\myworkspace\\api-seed\\simple_seed\\ssm\\pom.xml"));
            System.out.println(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
