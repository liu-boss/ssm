package com.coderman.mapper;

import com.coderman.model.Dept;

import java.util.List;

public interface DeptMapper {
    int deleteByPrimaryKey(Long id);
    int insert(Dept record);
    int insertSelective(Dept record);
    Dept selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(Dept record);
    int updateByPrimaryKey(Dept record);
    List<Dept> selectAll();
}
