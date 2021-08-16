package com.anzhen.mapper;

import com.anzhen.entity.Role;

import java.util.List;

/**
 * @Classname : RoleMapper.xml
 * @Date : 2021.6.14 14:34
 * @Created : anzhen
 * @Description :
 * @目的:
 */

public interface RoleMapper {

    // 查询角色
    Role queryRole(int  id);

    // 查询用户列表  通过id 查询对应的角色
    List<Role> queryRoleList();

    // 修改角色
    int updateRole(Role role);

    // 删除角色
    int deleteRoleById(int id);

    List<Role> queryRoleByUserId(int id);

    List<Role> queryRoleByPermissionId(int id);

}
