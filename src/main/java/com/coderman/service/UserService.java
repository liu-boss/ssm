package com.coderman.service;

import com.coderman.dto.form.UserAddParam;
import com.coderman.dto.form.UserUpdateParam;
import com.coderman.exception.ParamException;
import com.coderman.model.User;

import java.util.List;
import java.util.Set;

/**
 * @Author zhangyukang
 * @Date 2020/8/26 17:08
 * @Version 1.0
 **/
public interface UserService {
    User findByUsername(String username); //根据用户名查找
    void login(String username, String password); //用户登入
    List<User> query(User user); //模糊查询
    void add(UserAddParam addParam); //添加用户
    void delete(String[] ids) throws ParamException; //删除用户
    User getById(Long id);
    void update(UserUpdateParam updateParam); //更新用户
    Set<Long> queryRolesById(String userId); //用户角色id集合
    void resetPassword(Long userId); //重置用户密码
}
