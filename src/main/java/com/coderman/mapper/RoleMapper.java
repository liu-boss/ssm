package com.coderman.mapper;

import com.coderman.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);
    int insert(Role record);
    int insertSelective(Role record);
    Role selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(Role record);
    int updateByPrimaryKey(Role record);
    List<Role> query(Role role); //模糊查询
    void delete(String[] ids); //删除用户
    int checkByRoleName(@Param("roleName") String roleName, @Param("id") Long id); //校验角色名
    List<Role> listAll();

}
