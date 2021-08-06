package com.anzhen.service;

import com.anzhen.entity.Role;
import com.anzhen.entity.User;

/**
 * @Classname : UserService
 * @Date : 2021.4.27 9:22
 * @Created : anzhen
 * @Description :
 * @目的:
 */
public interface UserService {
    // 查询一个
    User queryUserById(int id);

    // 根据用户名和密码查询

    // 根据用户名查询
    User queryUserByName(String name);

    // 修改用户
    int updateUser(User user);

    // 删除用户
    int deleteUser(int id);

    // 获取角色
    Role queryRoleByUserId(int id);

}
