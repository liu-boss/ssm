package com.coderman.backend.monitor.service;

import com.coderman.backend.monitor.model.OperateWithBLOBs;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/9/10 11:54
 * @Version 1.0
 **/
public interface OperateService {
    //异步保存
    @Async
    void add(OperateWithBLOBs operate);
    List<OperateWithBLOBs> query(OperateWithBLOBs operate);
    void delete(String[] ids);
}
