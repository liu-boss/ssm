package com.coderman.exception.custom;

import com.coderman.exception.BizException;

/**
 * @Author zhangyukang
 * @Date 2020/9/29 20:32
 * @Version 1.0
 **/
public class UserException extends BizException {

    public UserException(String message) {
        super(message);
    }
}
