package com.coderman.service;

import com.coderman.dto.form.RoleParam;
import com.coderman.exception.ParamException;
import com.coderman.model.Role;

import java.util.List;
import java.util.Set;

/**
 * @Author zhangyukang
 * @Date 2020/8/28 10:38
 * @Version 1.0
 **/
public interface RoleService {
    List<Role> query(Role user); //模糊查询
    void add(RoleParam roleParam); //添加角色
    void delete(String[] ids) throws ParamException; //删除角色
    Role getById(Long id);
    void update(RoleParam roleParam); //更新角色
    List<Role> listAll();
    List<Role> findUserRole(String userName); //根据用户名获取角色
    Set<Long> queryMenusById(String roleId);//查看角色的权限集合(id)
}
