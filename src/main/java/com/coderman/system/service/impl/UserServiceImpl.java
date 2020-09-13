package com.coderman.system.service.impl;

import com.coderman.common.ProjectConstant;
import com.coderman.system.dto.form.UserAddParam;
import com.coderman.system.dto.form.UserUpdateParam;
import com.coderman.exception.ParamException;
import com.coderman.system.mapper.UserMapper;
import com.coderman.system.model.User;
import com.coderman.system.service.UserService;
import com.coderman.util.MD5Util;
import com.coderman.util.ShiroContextHolder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author zhangyukang
 * @Date 2020/8/26 17:09
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public List<User> query(User user) {
        return userMapper.query(user);
    }

    @Override
    public void add(UserAddParam userAddParam) {
        if (userMapper.checkByUsername(userAddParam.getUsername(), null) > 0) { //校验用户名可用性
            throw new ParamException("用户名已被占用");
        } else if (userMapper.checkByEmail(userAddParam.getEmail(), null) > 0) { //校验邮箱可用性
            throw new ParamException("邮箱已被占用");
        } else if (userMapper.checkMobile(userAddParam.getMobile(), null) > 0) { //校验电话号码可用性
            throw new ParamException("电话号码已被占用");
        } else {
            User user = new User();
            BeanUtils.copyProperties(userAddParam, user);
            user.setCreateTime(new Date());
            user.setModifyTime(new Date());
            user.setStatus(ProjectConstant.UN_LOCKED); //unlock status
            user.setSex(ProjectConstant.DEFAULT_SEX);
            user.setType(ProjectConstant.UserType.USER.getValue()); //用户类型:user
            RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();  //密码盐值
            String salt = randomNumberGenerator.nextBytes().toHex();
            user.setSalt(salt);
            user.setPassword(MD5Util.encryptPassword(userAddParam.getPassword(), salt));
            userMapper.insert(user);
            assignedRoleList(user.getId(), userAddParam.getRoleIdList());//分配角色
        }
    }

    /**
     * 用户分配角色
     *
     * @param id
     * @param roleIdList
     */
    private void assignedRoleList(Long id, Long[] roleIdList) {
        if (roleIdList != null && roleIdList.length != 0 && id != null) {
            //先清除用户-角色关联
            userMapper.clearRoleList(id);
            //添加用户-角色关联
            userMapper.assignedRoleList(id, roleIdList);
        }
    }

    @Override
    public void delete(String[] ids) throws ParamException {
        if (ids != null && ids.length > 0) {
            if (Arrays.asList(ids).contains(ShiroContextHolder.getIdentity().toString())) {
                throw new ParamException("不能删除当前登入用户");
            } else {
                //删除用户-角色关联
                for (String userId : ids) {
                    userMapper.clearRoleList(Long.parseLong(userId));
                }
                userMapper.delete(ids);
            }
        }
    }

    @Override
    public User getById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setPassword("");
        user.setSalt("");
        return user;
    }

    @Override
    public void update(UserUpdateParam updateParam) {
        Long id = updateParam.getId();
        if (userMapper.checkByUsername(updateParam.getUsername(), id) > 0) { //校验用户名可用性
            throw new ParamException("用户名已被占用");
        } else if (userMapper.checkByEmail(updateParam.getEmail(), id) > 0) { //校验邮箱可用性
            throw new ParamException("邮箱已被占用");
        } else if (userMapper.checkMobile(updateParam.getMobile(), id) > 0) { //校验电话号码可用性
            throw new ParamException("电话号码已被占用");
        } else {
            User user = new User();
            BeanUtils.copyProperties(updateParam, user);
            user.setModifyTime(new Date());
            userMapper.updateByPrimaryKeySelective(user);
            //更新用户-角色关联
            assignedRoleList(id, updateParam.getRoleIdList());
        }
    }

    @Override
    public Set<Long> queryRolesById(String userId) {
        return userMapper.queryRolesById(userId);
    }

    @Override
    public void resetPassword(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user==null){
            throw new ParamException("用户不存在");
        }else {
            RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
            String salt = randomNumberGenerator.nextBytes().toHex();
            String password = MD5Util.encryptPassword(ProjectConstant.DEFAULT_PASSWORD,salt);
            user.setPassword(password);
            user.setSalt(salt);
            user.setModifyTime(new Date());
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    @Override
    public void login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        String exceptionMessage = null;
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            exceptionMessage = "账户不存在";
        } catch (CredentialsException e) {
            exceptionMessage = "密码不匹配";
        } catch (ExcessiveAttemptsException e) {
            exceptionMessage = "账户已被限制登入,请1分钟后再试";
        } catch (LockedAccountException e) {
            exceptionMessage = "账号已被锁定,请联系管理员";
        } catch (AuthenticationException e) {
            logger.warn("user login fail: user=>{},message=>{}", username, e);
        }
        if (null != exceptionMessage) {
            throw new ParamException(exceptionMessage);
        } else {
            //更新登入时间
            User record = new User();
            record.setId(ShiroContextHolder.getIdentity());
            record.setLastLoginTime(new Date());
            userMapper.updateByPrimaryKeySelective(record);
        }
    }

}
