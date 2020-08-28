package com.coderman.service.impl;

import com.coderman.dto.form.RoleParam;
import com.coderman.exception.ParamException;
import com.coderman.mapper.RoleMapper;
import com.coderman.model.Role;
import com.coderman.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/8/28 10:42
 * @Version 1.0
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> query(Role role) {
        return roleMapper.query(role);
    }

    @Override
    public void add(RoleParam roleParam) {
        if (roleMapper.checkByRoleName(roleParam.getRoleName(), null) > 0) { //校验
            throw new ParamException("角色名已被占用");
        }else {
            Role role = new Role();
            BeanUtils.copyProperties(roleParam,role);
            role.setCreateTime(new Date());
            role.setModifyTime(new Date());
            roleMapper.insert(role);
        }
    }

    @Override
    public void delete(String[] ids) throws ParamException {
        roleMapper.delete(ids);
    }

    @Override
    public Role getById(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(RoleParam roleParam) {
        Role record = new Role();
        if (roleMapper.checkByRoleName(roleParam.getRoleName(), roleParam.getId()) > 0) { //校验
            throw new ParamException("角色名已被占用");
        }else {
            record.setId(roleParam.getId());
            record.setRoleName(roleParam.getRoleName());
            record.setModifyTime(new Date());
            record.setRemark(roleParam.getRemark());
            roleMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public List<Role> listAll() {
        return roleMapper.listAll();
    }
}
