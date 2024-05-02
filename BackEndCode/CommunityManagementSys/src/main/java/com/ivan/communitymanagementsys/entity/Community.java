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
public class Community implements Serializable {
    private static final long seriaVersionUID = 1L;
    private Long id;
    private String communityName;
    private String description;
}
