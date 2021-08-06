package com.anzhen.service.auth;

import com.anzhen.entity.Role;
import com.anzhen.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Classname : UserDetails
 * @Date : 21/08/01 15:31
 * @Created : anzhen
 * @Description :
 * @目的:
 */
@Data
public class UserDetail implements UserDetails, Serializable {


    /**
     * 登陆时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;


    // 用户
    private User user;

    // 用户唯一标识
    private String token;

    //
    private Collection<? extends GrantedAuthority> Authorities;
    List<Role> roles;

    public UserDetail(User user, List<Role> roles , Collection<? extends GrantedAuthority> Authorities) {
        this.user = user;
        this.roles = roles;
        this.Authorities = Authorities;
    }

    public UserDetail() {
    }

    @Override
    // 权限表
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
