package com.anzhen.mapper;

import com.anzhen.entity.Role;
import com.anzhen.entity.User;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.LinkedHashMap;

/**
 * @Classname : UserMapper
 * @Date : 2021.4.27 9:13
 * @Created : anzhen
 * @Description :
 * @目的:
 */

public interface UserMapper {

    public User queryUserById(int id);

    // 根据 账号和密码进行查询  验证是否成功
    // 根据用户名和密码查询
    User queryUserByName(String name, String password);

    // 根据用户名查询
    User queryUserByName(String name);

    // 修改用户
    int updateUser(User user);

    // 删除用户
    int deleteUser(int id);

    // 添加用户
    int insertUser(User user);

    Role queryRoleById(int id);

    LinkedHashMap<String, Object> selectUserPermissionById(@Param("id") int id);
}
