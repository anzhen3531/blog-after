package com.anzhen.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname : Permission
 * @Date : 21/08/01 22:15
 * @Created : anzhen
 * @Description :
 * @目的: 映射权限表
 */

@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = -55455731078448652L;
    // id
    private Integer id;

    // 权限名称
    private String permissionName;

    // 权限标记
    private String perRemarks;

    // 权限url
    private String url;


}
