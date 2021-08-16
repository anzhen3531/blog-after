package com.anzhen.security.component;

import com.alibaba.fastjson.JSON;
import com.anzhen.entity.Permission;
import com.anzhen.entity.Role;
import com.anzhen.mapper.PermissionMapper;
import com.anzhen.mapper.RoleMapper;
import com.anzhen.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: anzhen
 * @Date: 2021/8/15
 * @Description: anzhenBlog
 * @purpose:  获取url 所需要的角色和权限
 */
@Component
@Slf4j
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {


    @Resource
    PermissionMapper permissionMapper;


    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    // 获取权限
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        log.info("进入资源管理");
        // 如果不是需要授权的接口统一都不处理
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        System.out.println(requestUrl);
        List<Permission> permissions = permissionMapper.queryAll();
        System.out.println(permissions);

        for (Permission permission : permissions) {
            // 后一个是需要匹配的参数
            List<String> roles = new LinkedList<>();
            if (antPathMatcher.match(permission.getUrl(), requestUrl)){
                log.info("匹配成功");
                // 同url 查询Role 然后交予决策器
                List<Role> role = permissionMapper.queryRoleByUrl(permission.getUrl());
                roles.add(role.get(0).getRole());
                return SecurityConfig.createList(roles.toArray(new String[roles.size()]));
            }
        }
        return SecurityConfig.createList("ROLE_FAIL");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    // 支持
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
