package com.anzhen.mapper;

import com.anzhen.entity.Permission;
import com.anzhen.entity.Role;

import java.util.List;

/**
 * @Classname : PermissionMapper
 * @Date : 21/08/01 22:37
 * @Created : anzhen
 * @Description :
 * @目的:
 */

public interface PermissionMapper {
    
    List<Permission> queryAll();

    List<Permission> queryPermissionsByRoleId(int id);

    List<Role> queryRoleByUrl(String url);
}
