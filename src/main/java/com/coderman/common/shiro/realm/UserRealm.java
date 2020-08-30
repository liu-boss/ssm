package com.coderman.common.shiro.realm;

import com.coderman.common.shiro.CurrentUser;
import com.coderman.common.ProjectConstant;
import com.coderman.model.Menu;
import com.coderman.model.Role;
import com.coderman.model.User;
import com.coderman.service.MenuService;
import com.coderman.service.RoleService;
import com.coderman.service.UserService;
import com.coderman.util.ShiroContextHolder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author zhangyukang
 * @Date 2020/8/16 17:27
 * @Version 1.0
 **/
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        CurrentUser user = ShiroContextHolder.getUser();
        String userName = user.getUsername();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取用户角色集
        List<Role> roleList = this.roleService.findUserRole(userName);
        Set<String> roleSet = roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
        List<Menu> permissionList = this.menuService.findUserPermissions(userName);
        Set<String> permissionSet = permissionList.stream().map(Menu::getPerms).collect(Collectors.toSet());
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        logger.info("用户认证:{}",username);
        User user=userService.findByUsername(username);
        if(null==user){
            throw new UnknownAccountException();
        }else if (user.getStatus().equalsIgnoreCase(ProjectConstant.LOCKED)){
            throw new LockedAccountException();
        }
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        //当前登入用户
        CurrentUser currentUser = new CurrentUser();
        currentUser.setId(user.getId());
        currentUser.setUsername(user.getUsername());
        return new SimpleAuthenticationInfo(currentUser,user.getPassword(),salt,getName());
    }
}
