package com.alex.shiro;

import com.alex.shiro.model.Permission;
import com.alex.shiro.model.Role;
import com.alex.shiro.model.User;
import com.alex.shiro.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 认证授权的核心类
 */
public class AuthReaml extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     *  授权用的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User)principalCollection.fromRealm(this.getClass().getCanonicalName()).iterator().next();
        List<String> permissionList = new ArrayList<>();
        List<String> roleList = new ArrayList<>();
        Set<Role> roleSet = user.getRoles();
        if(CollectionUtils.isNotEmpty(roleSet)) {
            for(Role role : roleSet) {
                roleList.add(role.getRname());
                Set<Permission> permissionSet = role.getPermissions();
                if (CollectionUtils.isNotEmpty(permissionSet)) {
                    for(Permission p : permissionSet) {
                        permissionList.add(p.getPname());
                    }
                }
            }
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionList);
        info.addRoles(roleList);
        return info;
    }

    /**
     * 认证登录用的
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 强转
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;
        String username = usernamePasswordToken.getUsername();
        User user = userService.findByUserName(username);
        System.out.println(user);
        //
        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
    }
}
