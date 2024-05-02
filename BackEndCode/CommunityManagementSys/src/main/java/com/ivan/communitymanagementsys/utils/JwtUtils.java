package com.ivan.communitymanagementsys.utils;

import com.ivan.communitymanagementsys.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    /**
     * 生成JWT令牌
     * @param claims
     * @return
     */
    public static String genJWT(String secretKey, long ttlMillis,Map<String,Object> claims){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        String jwt = Jwts.builder()
                .claims(claims)//自定义内容（载荷）
                .expiration(new Date(System.currentTimeMillis()+ttlMillis))
                .signWith(key, Jwts.SIG.HS256)//算法签名,(密钥，加密算法)
                .compact();//返回字符串类型的jwt
        return jwt;
    }

    public static Claims parseJWT(String secretKey,String jwt) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        Claims claims = Jwts.parser()
                .verifyWith(key)//传递密钥
                .build()
                .parseSignedClaims(jwt)//传递jwt令牌
                .getPayload();//获取第二部分：Payload(有效载荷），携带一些自定义信息、默认信息等
        return claims;
    }

}
