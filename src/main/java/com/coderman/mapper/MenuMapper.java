package com.coderman.mapper;

import com.coderman.model.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface MenuMapper {
    int deleteByPrimaryKey(Long id);
    int insert(Menu record);
    int insertSelective(Menu record);
    Menu selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(Menu record);
    int updateByPrimaryKeyWithBLOBs(Menu record);
    int updateByPrimaryKey(Menu record);
    List<Menu> getUserMenus(String username); //获取用户菜单
    List<Menu> selectAll();
    int checkByMenuName(@Param("menuName") String menuName, @Param("id") Long id); //校验菜单名
    Set<Long> selectAllNextIdList(Long menuId);
    int checkParentMenu(Long id);
    List<Menu> findUserPermissions(String userName);
}
