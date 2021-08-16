package com.anzhen.security.component;

import com.alibaba.fastjson.JSON;
import com.anzhen.entity.Permission;
import com.anzhen.entity.Role;
import com.anzhen.security.customResult.CustomizeAuthenticationEntryPoint;
import com.anzhen.security.utils.TokenService;
import com.anzhen.service.auth.UserDetail;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Constants;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Auther: anzhen
 * @Date: 2021/8/15
 * @Description: anzhenBlog
 * @purpose:  自定义决策器   鉴权决策器
 */
@Component
@Slf4j
public class UrlAccessDecisionManager implements AccessDecisionManager {


    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection)
            throws AccessDeniedException, InsufficientAuthenticationException {

        // 可以从token Service 中读取
        // 遍历角色

        log.info("进入  AccessDecisionManager 中 ");
        if (authentication instanceof  AnonymousAuthenticationToken){
            throw new AccountExpiredException("账号错误 ");
        }else {
            UserDetail principal = (UserDetail) authentication.getPrincipal();
            System.out.println("info  principal " + principal);
            List<Role> roles = principal.getRoles();
            for (ConfigAttribute ca : collection) {
                System.out.println(ca.getAttribute());
                System.out.println(roles.get(0).getRole());
                if (ca.getAttribute().equals(roles.get(0).getRole())){
                    return;
                }
            }
        }
        throw new AccessDeniedException("请联系管理员分配权限！");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
