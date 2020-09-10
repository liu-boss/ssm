package com.coderman.monitor.mapper;

import com.coderman.monitor.model.Operate;
import com.coderman.monitor.model.OperateWithBLOBs;

import java.util.List;

public interface OperateMapper {
    int deleteByPrimaryKey(Long id);
    int insert(OperateWithBLOBs record);
    int insertSelective(OperateWithBLOBs record);
    OperateWithBLOBs selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(OperateWithBLOBs record);
    int updateByPrimaryKeyWithBLOBs(OperateWithBLOBs record);
    int updateByPrimaryKey(Operate record);
    void delete(String[] ids);
    List<OperateWithBLOBs> query(OperateWithBLOBs operate);
}
