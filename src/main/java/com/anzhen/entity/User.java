package com.anzhen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Classname : User
 * @Date : 2021.4.27 8:57
 * @Created : anzhen
 * @Description :
 * @目的:  表字段映射
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 448260653094233335L;


    private  Integer id;

    private  String name;

    private  String head;

    private  String password;

    private  String email;

    private  String notice;

    private  String slogan;

    private  String desc;

}
