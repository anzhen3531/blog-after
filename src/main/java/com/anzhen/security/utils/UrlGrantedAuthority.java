package com.anzhen.security.utils;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Classname : UrlGrantedAuthority
 * @Date : 21/08/06 11:34
 * @Created : anzhen
 * @Description :
 * @目的:  自定义url 工具类
 */
public class UrlGrantedAuthority  implements GrantedAuthority {

    private String url ;
    private String permissionName;

    public UrlGrantedAuthority() {
    }

    public UrlGrantedAuthority(String url, String permissionName) {
        this.url = url;
        this.permissionName = permissionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public String getAuthority() {
        return this.permissionName;
    }
}
