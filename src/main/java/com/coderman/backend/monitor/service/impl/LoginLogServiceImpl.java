package com.coderman.backend.monitor.service.impl;

import com.coderman.backend.monitor.mapper.LoginLogMapper;
import com.coderman.backend.monitor.model.LoginLog;
import com.coderman.backend.monitor.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/9/8 20:12
 * @Version 1.0
 **/
@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public List<LoginLog> query(LoginLog loginLog) {
        return loginLogMapper.query(loginLog);
    }

    @Override
    public void add(LoginLog loginLog) {
        loginLogMapper.insert(loginLog);
    }

    @Override
    public void delete(String[] ids) {
        loginLogMapper.delete(ids);
    }
}
