package com.anzhen.service.auth;

import com.anzhen.entity.Permission;
import com.anzhen.entity.Role;
import com.anzhen.entity.User;
import com.anzhen.security.utils.UrlGrantedAuthority;
import com.anzhen.service.PermissionService;
import com.anzhen.service.RoleService;
import com.anzhen.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname : UserDetailsService
 * @Date : 21/08/01 14:54
 * @Created : anzhen
 * @Description :
 * @目的:
 */

@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {


    @Resource
    UserService userService;

    @Resource
    RoleService roleService;

    @Resource
    PermissionService permissionService;


    @SneakyThrows
    @Override
    // 加载用户名和密码
    // 处理登录逻辑
    public UserDetails loadUserByUsername(String username) {

        log.info( "使用UserServiceDetail中验证" + username);

        User user = userService.queryUserByName(username);

        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        log.info("登录进入此地");
        UserDetail userDetail  = new UserDetail();
        userDetail.setUser(user);
        // 通过用户名获取
        List<Role> roles = roleService.queryByUserId(user.getId());
        userDetail.setRoles(roles);
        // 遍历取值
        for (Role role: roles){
            // 通过用户名获取权限
            List<Permission> permissions = permissionService.queryPermissionsByRoleId(role.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            // 创建工具类   将权限读取出来  后期用于和总权限对比
            for (Permission permission: permissions){
                GrantedAuthority grantedAuthority = new UrlGrantedAuthority(permission.getUrl(), permission.getPermissionName());
                grantedAuthorities.add(grantedAuthority);
            }
            userDetail.setAuthorities(grantedAuthorities);
        }


        return  new UserDetail(userDetail.getUser(), userDetail.roles, userDetail.getAuthorities());
    }
}
