package com.anzhen.security.customResult;

import cn.hutool.json.JSONObject;
import com.anzhen.utils.result.Result;
import com.anzhen.utils.result.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname : CustomizeAuthenticationFailureHandler
 * @Date : 21/08/01 16:22
 * @Created : anzhen
 * @Description :
 * @目的:  处理登录失败的时候
 */

@Component
@Slf4j
public class CustomizeAuthenticationLoginFailureHandler implements AuthenticationFailureHandler {


    @Resource
    ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//返回json数据

        log.info("进入错误判断");
        JSONObject result = null;
        if (e instanceof AccountExpiredException) {
            //账号过期
            result = new JSONObject(Result.error(ResultCodeEnum.USER_ACCOUNT_EXPIRED));
        } else if (e instanceof BadCredentialsException) {
            //密码错误
            result =  new JSONObject(Result.error(ResultCodeEnum.NAME_PASSWORD_ERROR));
        } else if (e instanceof CredentialsExpiredException) {
            result =  new JSONObject(Result.error(ResultCodeEnum.USERNAME_NOTFIND));
        }else{
            //其他错误
            result =  new JSONObject(Result.error(ResultCodeEnum.USERNAME_NOTFIND));
        }
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(result.toString());
    }
}
