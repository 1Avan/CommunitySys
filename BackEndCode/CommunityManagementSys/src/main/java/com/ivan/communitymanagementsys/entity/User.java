package com.ivan.communitymanagementsys.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String password;
    private String name;
    private String sex;
    private String phone;
    private Long communityId;
    private Integer roleId;
    private Integer status;
}