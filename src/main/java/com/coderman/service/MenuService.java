package com.coderman.service;

import com.coderman.dto.form.MenuParam;
import com.coderman.model.Menu;
import com.coderman.util.TreeObject;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/8/30 10:28
 * @Version 1.0
 **/
public interface MenuService {
    List<TreeObject> getUserMenus(String username); //用户菜单
    List<TreeObject> tree(); //菜单和按钮树
    void add(MenuParam menuParam); //添加菜单
    Menu getById(Long id);
    void update(MenuParam deptParam);
    void delete(Long id);
    List<Menu> findUserPermissions(String userName); //用户权限集合
}
