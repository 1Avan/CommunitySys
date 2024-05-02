package com.ivan.communitymanagementsys.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//从yaml配置文件中读取并自动赋值给类中变量
@ConfigurationProperties(prefix = "cms.jwt")
@Data
public class JwtProperties {

    /**
     * 用户端微信用户生成jwt令牌相关配置
     */
    //密钥
    private String userSecretKey;
    //过期时间
    private long userTtl;
    //前端传递过来的token名
    private String userTokenName;

}
