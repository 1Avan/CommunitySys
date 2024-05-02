package com.ivan.communitymanagementsys.dto;

import jakarta.validation.constraints.Null;
import lombok.Data;

import java.io.Serializable;
@Data
public class UserPageQueryDTO implements Serializable {
    //用户姓名
    private String name;
    //用户性别
    private String sex;
    //用户所属社团id
    private Long communityId;
    //页码
    private int page;
    //每页显示数据条数
    private int pageSize;
}
