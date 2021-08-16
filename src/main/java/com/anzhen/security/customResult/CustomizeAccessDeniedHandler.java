package com.anzhen.security.customResult;

import cn.hutool.json.JSONObject;
import com.anzhen.utils.result.Result;
import com.anzhen.utils.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname : CustomizeAccessDecisionManager
 * @Date : 21/08/01 22:57
 * @Created : anzhen
 * @Description :  权限不足返回
 * @目的:
 */
@Component
@Slf4j
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        log.info("进入 授权失败的页面");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(new JSONObject(Result.error(ResultCodeEnum.NO_RIGHT_TO_ACCESS)).toString());
    }
}
