package com.anzhen.service.impl;

import com.anzhen.entity.Role;
import com.anzhen.entity.User;
import com.anzhen.mapper.UserMapper;
import com.anzhen.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Classname : UserServiceImpl
 * @Date : 2021.4.27 9:23
 * @Created : anzhen
 * @Description :
 * @目的:
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User queryUserById(int id) {
        return userMapper.queryUserById(id);
    }


    @Override
    public User queryUserByName(String name) {
        return userMapper.queryUserByName(name);
    }

    @Override
    public int updateUser(User user) {
        if (user.getId() != null){
            return userMapper.updateUser(user);
        }else {
            // 查询用户是否合法  不合法则 不插入返回错误信息
            if (queryUserByName(user.getName()) == null){
                // 设置默认权限
                return userMapper.insertUser(user);
            }
            else {
                // 返回错误信息
                return -1;
            }
        }
    }

    @Override
    public int deleteUser(int id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public Role queryRoleByUserId(int id) {
        return userMapper.queryRoleById(id);
    }

}
