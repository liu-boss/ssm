package com.coderman.backend.monitor.service;

import com.coderman.backend.monitor.model.LoginLog;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/9/8 20:10
 * @Version 1.0
 **/
public interface LoginLogService {
    List<LoginLog> query(LoginLog loginLog);
    //异步保存
    @Async
    void add(LoginLog loginLog);
    void delete(String[] ids);
}
