package com.coderman.service.impl;

import com.coderman.dto.form.RoleParam;
import com.coderman.exception.ParamException;
import com.coderman.mapper.RoleMapper;
import com.coderman.model.Role;
import com.coderman.service.RoleService;
import com.coderman.util.ShiroContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
            roleAuthorization(role.getId(),roleParam.getMenuIdList());
        }
    }

    /**
     * 角色授权
     * @param id
     * @param menuIdList
     */
    private void roleAuthorization(Long id, Long[] menuIdList) {
        if (menuIdList != null && menuIdList.length != 0 && id != null) {
            //先清除角色-菜单关联
            roleMapper.clearMenuList(id);
            //添加角色-菜单关联
            roleMapper.roleAuthorization(id, menuIdList);
        }
    }

    @Override
    public void delete(String[] ids) throws ParamException {
        List<Role> userRole = roleMapper.findUserRole(ShiroContextHolder.getUser().getUsername());
        boolean containsCurRole=false;//当前用户的角色是否在要删除里
        if(ids!=null&&ids.length>0){
            for (Role role : userRole) {
                for (String id : ids) {
                    if (role.getId().toString().equals(id)) {
                        containsCurRole = true;
                        break;
                    }
                }
            }
            if(containsCurRole){
                throw new ParamException("当前用户拥有该角色,无法删除");
            }else {
                roleMapper.delete(ids);
                //删除角色-菜单关联
                for (String roleId : ids) {
                    roleMapper.clearMenuList(Long.parseLong(roleId));
                }
            }
        }
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
            //更新角色-菜单关联
            roleAuthorization(roleParam.getId(), roleParam.getMenuIdList());
        }
    }
    @Override
    public List<Role> listAll() {
        return roleMapper.listAll();
    }

    @Override
    public List<Role> findUserRole(String userName) {
        return roleMapper.findUserRole(userName);
    }

    @Override
    public Set<Long> queryMenusById(String roleId) {
        return roleMapper.queryMenusById(roleId);
    }

}
