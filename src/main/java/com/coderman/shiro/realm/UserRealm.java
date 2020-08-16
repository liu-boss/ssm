package com.coderman.shiro.realm;

import com.coderman.model.User;
import com.coderman.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author zhangyukang
 * @Date 2020/8/16 17:27
 * @Version 1.0
 **/
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        User user=userService.findByUsername(username);
        if(null==user){
            throw new UnknownAccountException();
        }else if (Boolean.TRUE.equals(user.getLocked())){
            throw new LockedAccountException();
        }
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),salt,getName());
    }
}
