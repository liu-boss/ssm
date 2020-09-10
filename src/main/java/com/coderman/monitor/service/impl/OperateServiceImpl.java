package com.coderman.monitor.service.impl;

import com.coderman.monitor.mapper.OperateMapper;
import com.coderman.monitor.model.LoginLog;
import com.coderman.monitor.model.Operate;
import com.coderman.monitor.model.OperateWithBLOBs;
import com.coderman.monitor.service.OperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/9/10 11:55
 * @Version 1.0
 **/
@Service
public class OperateServiceImpl implements OperateService {

    @Autowired
    private OperateMapper operateMapper;

    @Override
    public void add(OperateWithBLOBs operate) {
        operateMapper.insert(operate);
    }

    @Override
    public List<OperateWithBLOBs> query(OperateWithBLOBs operate) {
        return operateMapper.query(operate);
    }

    @Override
    public void delete(String[] ids) {
        operateMapper.delete(ids);
    }

}
