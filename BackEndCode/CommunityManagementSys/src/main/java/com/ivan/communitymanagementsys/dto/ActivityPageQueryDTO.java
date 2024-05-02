package com.ivan.communitymanagementsys.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActivityPageQueryDTO implements Serializable {
    //活动名
    private String name;
    //活动描述
    private String description;
    //页码
    private int page;
    //每页显示数据条数
    private int pageSize;
    //社团id
    private Long communityId;


}
