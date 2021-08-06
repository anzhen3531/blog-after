package com.anzhen.service;

import com.anzhen.entity.Role;

import java.util.List;

/**
 * @Classname : RoleService
 * @Date : 2021.6.14 14:32
 * @Created : anzhen
 * @Description :
 * @目的:
 */
public interface RoleService {

    // 查询一个
    Role queryUserById(int id);

    // 根据用户名查询
    List<Role>  queryRoleList();

    // 修改用户
    int updateUser(Role role);

    // 删除用户
    int deleteUser(int id);

    // 根据用户id 查询角色
    List<Role> queryByUserId(int id);

}
