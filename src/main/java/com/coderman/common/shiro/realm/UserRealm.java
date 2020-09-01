package com.coderman.common.shiro.realm;

import com.coderman.common.ProjectConstant;
import com.coderman.common.shiro.CurrentUser;
import com.coderman.model.Menu;
import com.coderman.model.Role;
import com.coderman.model.User;
import com.coderman.service.MenuService;
import com.coderman.service.RoleService;
import com.coderman.service.UserService;
import com.coderman.util.HttpUtil;
import com.coderman.util.ShiroContextHolder;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
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

    @Autowired
    private SessionDAO sessionDAO;

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
        /**************** session start  同一个账号只能一人登入 *********************/
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for(Session session:sessions){
            if(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)!=null){
                SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                CurrentUser principal = (CurrentUser) simplePrincipalCollection.getPrimaryPrincipal();
                if(username.equals(principal.getUsername())){
                    //设置session立即失效，即将其踢出系统
                    session.setTimeout(0);
                    sessionDAO.delete(session);
                    break;
                }
            }
        }
        /**************** session end*********************/
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
        HttpServletRequest httpServletRequest = ShiroContextHolder.getHttpServletRequest();
        currentUser.setHost(HttpUtil.getIpAddr(httpServletRequest));
        currentUser.setLocation(HttpUtil.getCityInfo(httpServletRequest));
        return new SimpleAuthenticationInfo(currentUser,user.getPassword(),salt,getName());
    }
}
