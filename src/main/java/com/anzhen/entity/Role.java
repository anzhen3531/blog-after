package com.anzhen.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @Classname : Role
 * @Date : 2021.6.14 10:33
 * @Created : anzhen
 * @Description :
 * @目的:
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = -91881003056749924L;

    private int id;

    private String role;

    private String roleName;


}
