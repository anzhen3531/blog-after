package com.anzhen.security;

import cn.hutool.core.util.ObjectUtil;
import com.anzhen.security.utils.TokenService;
import com.anzhen.security.utils.UrlGrantedAuthority;
import com.anzhen.service.auth.UserDetail;
import com.anzhen.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @Classname : CommonHasPermission
 * @Date : 21/08/02 15:34
 * @Created : anzhen
 * @Description :
 * @目的:
 */
@Component("pm")
@Slf4j
public class CommonHasPermission {

    @Autowired
    private TokenService tokenService;


    public boolean hasPermission(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        UserDetail commonUser = tokenService.getCommonUser(RequestUtil.getRequest());
        if (ObjectUtil.isEmpty(commonUser)) {
            return false;
        }

        log.info("走鉴权服务");
        // 经行路径匹配
        for (GrantedAuthority authority : commonUser.getAuthorities()) {
            UrlGrantedAuthority urlGrantedAuthority = (UrlGrantedAuthority) authority;
            if (permission.matches(urlGrantedAuthority.getUrl())){
                return true;
            }
        }

        return false;
    }

}
