package com.coderman.service.impl;

import com.coderman.common.ProjectConstant;
import com.coderman.dto.form.MenuParam;
import com.coderman.exception.ParamException;
import com.coderman.mapper.MenuMapper;
import com.coderman.model.Dept;
import com.coderman.model.Menu;
import com.coderman.service.MenuService;
import com.coderman.util.TreeObject;
import com.coderman.util.TreeUtil;
import com.coderman.vo.MenuTreeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author zhangyukang
 * @Date 2020/8/30 10:30
 * @Version 1.0
 **/
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<TreeObject> getUserMenus(String username) {
        List<TreeObject> treeVOList = new ArrayList<>();
        List<Menu> menuList = menuMapper.getUserMenus(username);
        for (Menu menu : menuList) {
            MenuTreeVO menuTreeVO = new MenuTreeVO();
            BeanUtils.copyProperties(menu, menuTreeVO);
            treeVOList.add(menuTreeVO);
        }
        return TreeUtil.buildTree(treeVOList);
    }

    @Override
    public List<TreeObject> tree() {
        List<TreeObject> voList = new ArrayList<>();
        List<Menu> menuList = menuMapper.selectAll();
        for (Menu menu : menuList) {
            MenuTreeVO menuTreeVO = new MenuTreeVO();
            BeanUtils.copyProperties(menu, menuTreeVO);
            voList.add(menuTreeVO);
        }
        return TreeUtil.buildTree(voList);
    }

    @Override
    public void add(MenuParam menuParam) throws ParamException {
        if (menuParam.getParentId() == null) {
            menuParam.setParentId(0L);
        }
        if (menuParam.getOrderNum() == null) {
            menuParam.setOrderNum(0L);
        }
        final String buttonType = ProjectConstant.MenuType.BUTTON.getValue();
        final String menuType = ProjectConstant.MenuType.MENU.getValue();
        if (!menuParam.getType().equalsIgnoreCase(menuType) && !menuParam.getType().equalsIgnoreCase(buttonType)) {
            throw new ParamException("类型不存在");
        }
        if (menuMapper.checkByMenuName(menuParam.getMenuName(), null) > 0) { //校验菜单名可用性
            throw new ParamException("菜单名称已被占用");
        }
        Menu parentMenu = menuMapper.selectByPrimaryKey(menuParam.getParentId());
        if (parentMenu == null) {
            throw new ParamException("父菜单不存在");
        }

        Menu menu = new Menu();
        if (menuParam.getType().equals(buttonType)) {
            //button
            BeanUtils.copyProperties(menuParam, menu);
            menu.setIcon(null);
            menu.setUrl(null);
            menu.setCreateTime(new Date());
            menu.setModifyTime(new Date());
            menu.setOrderNum(0L);
        } else {
            //menu
            BeanUtils.copyProperties(menuParam, menu);
            menu.setCreateTime(new Date());
            menu.setModifyTime(new Date());
        }
        menuMapper.insert(menu);
    }

    @Override
    public Menu getById(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(MenuParam menuParam) {
        final String buttonType = ProjectConstant.MenuType.BUTTON.getValue();
        final String menuType = ProjectConstant.MenuType.MENU.getValue();
        if (!menuParam.getType().equalsIgnoreCase(menuType) && !menuParam.getType().equalsIgnoreCase(buttonType)) {
            throw new ParamException("类型不存在");
        }
        if (menuParam.getParentId() == null) {
            menuParam.setParentId(0L);
        }
        if(menuParam.getOrderNum()==null){
            menuParam.setOrderNum(0L);
        }

        if (menuMapper.checkByMenuName(menuParam.getMenuName(), menuParam.getId()) > 0) { //校验名称可用性
            throw new ParamException("菜单名称已被占用");
        }
        //查找父菜单
        if (menuParam.getParentId() != 0L) {
            Menu parent = menuMapper.selectByPrimaryKey(menuParam.getParentId());
            if (parent == null) {
                throw new ParamException("父菜单不存在");
            }
        }
        //添加的父节点不能是本身和其子节点
        if(menuParam.getParentId().intValue()==menuParam.getId().intValue()){
            throw new ParamException("添加的父菜单不能是本身");
        }
        //获取该节点下的所有子节点
        HashSet<Long> childIdList =new HashSet<>();
        getChildIdList(menuParam.getId(),childIdList);
        if(childIdList.contains(menuParam.getParentId())){
            throw new ParamException("添加的父菜单不能是子当前节点的子菜单");
        }
        Menu menu;
        if (menuParam.getType().equals(buttonType)) {
            menu= new Menu();
            //button
            BeanUtils.copyProperties(menuParam, menu);
            menu.setIcon(null);
            menu.setUrl(null);
            menu.setModifyTime(new Date());
            menu.setOrderNum(0L);
        } else {
            //menu
            menu= new Menu();
            BeanUtils.copyProperties(menuParam, menu);
            menu.setModifyTime(new Date());
        }
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void delete(Long id) {
        if (menuMapper.checkParentMenu(id) > 0) {
            throw new ParamException("有子菜单无法删除");
        }else {
            menuMapper.deleteByPrimaryKey(id);
        }
    }
    @Override
    public List<Menu> findUserPermissions(String userName) {
        return menuMapper.findUserPermissions(userName);
    }

    /**
     * 获取该节点下的所有子节点
     * @param id
     * @param result
     */
    private void getChildIdList(Long id,HashSet<Long> result) {
        Set<Long> nextIdList = menuMapper.selectAllNextIdList(id);
        result.addAll(nextIdList);
        if(nextIdList!=null&&nextIdList.size()>0){
            for (Long next : nextIdList) {
                getChildIdList(next,result);
            }
        }
    }
}
