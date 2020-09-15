package com.coderman.backend.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author zhangyukang
 * @Date 2020/9/10 09:34
 * @Version 1.0
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Operate {

    String operateModule() default ""; //所属模块

    String operateDesc() default "";  // 操作说明

}
