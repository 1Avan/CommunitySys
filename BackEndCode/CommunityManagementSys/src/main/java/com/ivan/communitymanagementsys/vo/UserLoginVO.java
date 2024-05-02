package com.ivan.communitymanagementsys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "用户登录返回的数据格式")
public class UserLoginVO  implements Serializable {
    @Schema(description = "用户id")
    private Long id;
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "角色")
    private Integer roleId;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "jwt令牌")
    private String token;
}
