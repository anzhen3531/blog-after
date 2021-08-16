package com.anzhen.security.customResult;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.anzhen.security.utils.TokenService;
import com.anzhen.service.auth.UserDetail;
import com.anzhen.utils.DateUtil;
import com.anzhen.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname : CustomizeLogoutSuccessHandler
 * @Date : 21/08/01 16:27
 * @Created : anzhen
 * @Description :
 * @目的:  请求登出处理类
 */
@Component
@Slf4j
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        JSONObject result = new JSONObject(Result.success());

        // 清除redis中的token
        UserDetail commonUser = tokenService.getCommonUser(httpServletRequest);
        if (!ObjectUtil.isEmpty(commonUser)){
            // 移除token
            Boolean aBoolean = tokenService.deleteToken(commonUser.getToken());
            System.out.println(aBoolean  + " redis 是否清理完成token ");
            log.info("==== 用户【{}】在 {} 退出了系统" ,commonUser.getUsername(), DateUtil.nowString());
        }
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(result.toString());
    }
}
