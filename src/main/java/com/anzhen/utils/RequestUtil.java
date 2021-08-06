package com.anzhen.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname : RequestUtil
 * @Date : 21/08/02 15:35
 * @Created : anzhen
 * @Description :
 * @目的:
 */
@UtilityClass
public class RequestUtil {

    public HttpServletRequest getRequest(){
        return getRequestAttributes().getRequest();
    }

    public ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }
}