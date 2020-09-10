package com.coderman.system.mapper;

import com.coderman.system.model.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface DeptMapper {
    int deleteByPrimaryKey(Long id);
    int insert(Dept record);
    int insertSelective(Dept record);
    Dept selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(Dept record);
    int updateByPrimaryKey(Dept record);
    int checkParentDept(Long id);
    int checkByDeptName(@Param("deptName") String deptName,@Param("id") Long id);
    List<Dept> selectAll();
    Set<Long> selectAllNextIdList(Long deptId); //获取该节点下一级的所有子节点集合
}
