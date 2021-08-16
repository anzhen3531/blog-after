package com.anzhen.security.customResult;

import cn.hutool.json.JSONObject;
import com.anzhen.utils.result.Result;
import com.anzhen.utils.result.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname : JwtAuthenticationEntryPoint
 * @Date : 21/08/01 14:37
 * @Created : anzhen
 * @Description :
 * @目的:  匿名用户访问无权资源异常
 */
@Slf4j
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    ObjectMapper objectMapper;


    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        log.info("进入返回状态");
        response.setContentType("application/json;charset=utf-8");
        System.out.println(authException.getMessage());
        if (authException instanceof AccountExpiredException){
            response.getWriter().write(new JSONObject(Result.error(ResultCodeEnum.LOGIN_IS_OVERDUE, "登录已失效")).toString());
        }else {
            response.getWriter().write(new JSONObject(Result.error(ResultCodeEnum.NOT_LOGIN, "没有登录账号")).toString());
        }
    }
}

