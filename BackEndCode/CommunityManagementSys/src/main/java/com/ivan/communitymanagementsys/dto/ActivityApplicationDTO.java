package com.ivan.communitymanagementsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityApplicationDTO {
    private Long userId;
    private Long activityId;
}
