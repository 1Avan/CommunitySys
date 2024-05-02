package com.ivan.communitymanagementsys.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    //用户id（自增）
    private Long id;
    //用户名
    private String username;
    //密码
    private String password;
    //姓名
    private String name;
}
