package com.anzhen.service.impl;

import com.anzhen.entity.Role;
import com.anzhen.mapper.RoleMapper;
import com.anzhen.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname : RoleServiceImpl
 * @Date : 2021.6.14 14:33
 * @Created : anzhen
 * @Description :
 * @目的:
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;


    @Override
    public Role queryUserById(int id) {
        return roleMapper.queryRole(id);
    }

    @Override
    public List<Role> queryRoleList() {
        return roleMapper.queryRoleList();
    }

    @Override
    public int updateUser(Role role) {
        return 0;
    }

    @Override
    public int deleteUser(int id) {
        return roleMapper.deleteRoleById(id);
    }

    @Override
    public List<Role> queryByUserId(int id) {
        return roleMapper.queryRoleByUserId(id);
    }
}
