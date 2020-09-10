package com.coderman.monitor.service.impl;

import com.coderman.monitor.mapper.LoginLogMapper;
import com.coderman.monitor.model.LoginLog;
import com.coderman.monitor.service.LoginLogService;
import com.coderman.util.HttpUtil;
import com.coderman.util.ShiroContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
        loginLog.setLoginTime(new Date());
        HttpServletRequest request = ShiroContextHolder.getHttpServletRequest();
        loginLog.setIp(HttpUtil.getIpAddr(request));
        loginLog.setLocation(HttpUtil.getCityInfo(request));
        loginLogMapper.insert(loginLog);
    }

    @Override
    public void delete(String[] ids) {
        loginLogMapper.delete(ids);
    }
}
