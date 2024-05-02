package com.ivan.communitymanagementsys.dto;

import lombok.Data;

@Data
public class NewsletterPageQueryDTO {
    private String name;
    private String content;
    private Integer page;
    private Integer pageSize;
}
