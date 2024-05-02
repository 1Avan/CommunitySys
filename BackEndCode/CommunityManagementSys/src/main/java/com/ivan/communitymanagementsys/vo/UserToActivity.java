package com.ivan.communitymanagementsys.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserToActivity implements Serializable {
    private String username;
    private String name;
    private String sex;
    private String phone;
}
