package com.anzhen.service;

import com.anzhen.entity.Permission;

import java.util.List;

/**
 * @Classname : PermissionService
 * @Date : 21/08/01 22:45
 * @Created : anzhen
 * @Description :
 * @目的:
 */


public interface PermissionService {

    List<Permission> queryAll();

    // 根据角色名查询
    List<Permission> queryPermissionsByRoleId(int  id);

}
