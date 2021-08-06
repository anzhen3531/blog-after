package com.anzhen.security.customResult;

import cn.hutool.json.JSONObject;
import com.anzhen.security.utils.TokenService;
import com.anzhen.service.auth.UserDetail;
import com.anzhen.service.UserService;
import com.anzhen.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname : CustomizeAuthenticationSuccessHandler
 * @Date : 21/08/01 16:17
 * @Created : anzhen
 * @Description :
 * @目的:  登录成功处理类
 */

@Component
@Slf4j
public class CustomizeAuthenticationLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Resource
    UserService userService;

    @Resource
    TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.info("用户登录成功  进入登录成返回类  ");
        String name = authentication.getName();
        // 生成token
        UserDetail principal = (UserDetail) authentication.getPrincipal();// 获取当前全部信息
        System.out.println(principal);
        String token = tokenService.createToken(principal);
        System.out.println(token);
        // 将类的信息 和 token 返回给前端

        System.out.println(principal);

        principal.setToken(token);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userinfo", principal.getUser());
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        System.out.println(new JSONObject(Result.success(result)).toString());
        httpServletResponse.getWriter().write(new JSONObject(Result.success(result)).toString());

    }
}
