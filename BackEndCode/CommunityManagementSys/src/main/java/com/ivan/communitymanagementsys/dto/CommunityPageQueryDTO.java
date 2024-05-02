package com.ivan.communitymanagementsys.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommunityPageQueryDTO implements Serializable {
    private Integer pageSize;
    private Integer page;
    //社团名
    private String  communityName;
    //社团描述
    private String description;

}
