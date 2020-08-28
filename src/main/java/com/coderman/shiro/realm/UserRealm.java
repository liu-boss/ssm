package com.coderman.shiro.realm;

import com.coderman.common.CurrentUser;
import com.coderman.common.ProjectConstant;
import com.coderman.model.User;
import com.coderman.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author zhangyukang
 * @Date 2020/8/16 17:27
 * @Version 1.0
 **/
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
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

        CurrentUser currentUser = new CurrentUser();
        currentUser.setId(user.getId());
        currentUser.setUsername(user.getUsername());
        return new SimpleAuthenticationInfo(currentUser,user.getPassword(),salt,getName());
    }
}
