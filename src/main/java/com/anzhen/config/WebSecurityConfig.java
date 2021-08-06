package com.anzhen.config;

import com.anzhen.security.customResult.*;
import com.anzhen.security.filter.TokenFiler;
import com.anzhen.service.auth.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Classname : WebSecurityConfiog
 * @Date : 21/08/01 14:50
 * @Created : anzhen
 * @Description :
 * @目的:
 */

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Resource
//    匿名用户访问无权资源异常
    CustomizeAuthenticationEntryPoint commonAuthenticationEntryPoint;

    @Resource
//  处理登录失败的时候
    CustomizeAuthenticationLoginFailureHandler customizeAuthenticationLoginFailureHandler;

    @Resource
//   登录成功返回
    CustomizeAuthenticationLoginSuccessHandler customizeAuthenticationLoginSuccessHandler;

    @Resource
    CustomizeAccessDecisionManager accessDecisionManager;

    @Resource
//    请求登出处理类
    CustomizeLogoutSuccessHandler customizeLogoutSuccessHandler;

    @Resource
    UserDetailsServiceImpl userDetailsService;

    @Resource
    TokenFiler tokenFiler;




    /**
     * 配置设置，设置退出的地址和token
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 退出请求路径
                .logout().logoutUrl("/user/logout")
                .logoutSuccessHandler(customizeLogoutSuccessHandler)
                .and()
                // 登录请求路径   成功和失败的处理结果
                .formLogin().loginProcessingUrl("/user/login")
                .successHandler(customizeAuthenticationLoginSuccessHandler)
                .failureHandler(customizeAuthenticationLoginFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/user/login","/user/logout", "/swagger-ui.html#/**").permitAll()//设置login不进行权限控制
                // 解决了我跨域问题
                .antMatchers(HttpMethod.OPTIONS).permitAll()   // 允许跨域
                .anyRequest().authenticated()  // 其余接口都要走那个授权
                .and()
                .exceptionHandling()
                //   权限不足返回
                .accessDeniedHandler(accessDecisionManager)
                //  匿名用户访问无权资源异常
                .authenticationEntryPoint(commonAuthenticationEntryPoint)
                .and()
                .addFilterBefore(tokenFiler, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    /**
     * 注入自己的权限拦截器
     */
//    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler(){
//        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
//        handler.setPermissionEvaluator(new CustomPermissionEvaluator());
//        return handler;
//    }

    /**
     * 密码处理
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

//    /**
//     * 配置哪些请求不拦截
//     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/article/*","/category",  "/search/*","/queryBlogInfo/*","/blog/*");
     }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}


