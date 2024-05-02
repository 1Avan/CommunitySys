package com.ivan.communitymanagementsys.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ApplicationDTO implements Serializable {
    private Long id;
    private Long userId;
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime auditTime;
    //    0待审核、1已批准、2已拒绝
    private Integer status;
}
