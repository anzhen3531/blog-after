package com.anzhen.security.filter;

import cn.hutool.core.util.ObjectUtil;
import com.anzhen.security.utils.TokenService;
import com.anzhen.service.auth.UserDetail;
import com.anzhen.utils.TokenIgnoreUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname : TokenFiler
 * @Date : 21/08/02 9:18
 * @Created : anzhen
 * @Description :
 * @目的:  保证一个请求只执行一次token filter过滤器
 *
 *
 * 这个是令牌拦截器    拦截token中的令牌
 */

@Component
@Slf4j
public class TokenFiler extends OncePerRequestFilter {

    @Resource
    TokenService tokenService;

    @Resource
    TokenIgnoreUrl tokenIgnoreUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 由于是前后端分离 所以应该直接放行 op 请求 ， 然后判断后面的请求

        if (httpServletRequest.getRequestURI().contains("/user/login") || httpServletRequest.getRequestURI().contains("/user/logout")) {
            log.info("进入登录判断");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }


        UserDetail commonUser = tokenService.getCommonUser(httpServletRequest);
        if(ObjectUtil.isNotEmpty(commonUser)) {
            tokenService.validateToken(commonUser);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(commonUser, null, commonUser.getAuthorities());
            // 获取上下文存储  用户信息
            // 这里是先获取的 上下文对象 然后使用上下文对象设置 Authentication
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        log.info("存储进上下文对象是 【{}】" , SecurityContextHolder.getContext().getAuthentication() );
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}


