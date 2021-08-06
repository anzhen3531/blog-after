package com.anzhen.service.impl;

import com.anzhen.entity.Permission;
import com.anzhen.entity.Role;
import com.anzhen.mapper.PermissionMapper;
import com.anzhen.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname : PermissionServiceImpl
 * @Date : 21/08/01 22:46
 * @Created : anzhen
 * @Description :
 * @目的:
 */

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    PermissionMapper permissionMapper;

    @Override
    public List<Permission> queryAll() {
        return permissionMapper.queryAll();
    }

    @Override
    public List<Permission> queryPermissionsByRoleId(int id) {
        return permissionMapper.queryPermissionsByRoleId(id);
    }
}
