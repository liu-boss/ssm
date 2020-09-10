package com.coderman.system.mapper;

import com.coderman.system.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

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
    List<Role> findUserRole(String userName);
    void clearMenuList(Long id); //清除角色-菜单关联
    void roleAuthorization(@Param("roleId") Long id,@Param("menuIdList") Long[] menuIdList); //添加角色-菜单关联
    Set<Long> queryMenusById(String roleId); //查询角色拥有的权限集合
}
