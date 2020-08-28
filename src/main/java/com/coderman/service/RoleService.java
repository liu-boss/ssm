package com.coderman.service;

import com.coderman.dto.form.RoleParam;
import com.coderman.exception.ParamException;
import com.coderman.model.Role;

import java.util.List;

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

}
