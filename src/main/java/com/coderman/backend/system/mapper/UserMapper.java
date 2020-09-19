package com.coderman.backend.system.mapper;

import com.coderman.backend.system.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);
    int insert(User record);
    int insertSelective(User id);
    User selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(User record);
    int updateByPrimaryKey(User record);
    User selectByUsername(@Param("username") String username); //根据用户名查找用户
    List<User> query(User user); //模糊查询
    void delete(String[] ids); //删除用户
    int checkByUsername(@Param("username") String username,@Param("id") Long id); //校验用户名是否占用
    int checkByEmail(@Param("email") String email,@Param("id") Long id); //校验邮箱
    int checkMobile(@Param("mobile") String mobile,@Param("id") Long id);
    void assignedRoleList(@Param("userId") Long id,@Param("roleIdList") Long[] roleIdList); //关联用户和角色
    Set<Long> queryRolesById(String userId); //用户已有的角色set
}
