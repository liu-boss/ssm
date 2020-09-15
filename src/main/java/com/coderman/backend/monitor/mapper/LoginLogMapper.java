package com.coderman.backend.monitor.mapper;

import com.coderman.backend.monitor.model.LoginLog;

import java.util.List;

public interface LoginLogMapper {
    int deleteByPrimaryKey(Long id);
    int insert(LoginLog record);
    int insertSelective(LoginLog record);
    LoginLog selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(LoginLog record);
    int updateByPrimaryKey(LoginLog record);
    List<LoginLog> query(LoginLog loginLog);
    void delete(String[] ids);
}
