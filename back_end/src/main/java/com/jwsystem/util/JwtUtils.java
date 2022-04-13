package com.jwsystem.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix = "group5.jwt")//前缀对应了在配置文件中的配置位置
public class JwtUtils {

    private long expire; //配置过期时间，可以在配置文件中修改
    private String secret;//配置密钥，可以在配置文件中修改
    private String header;//jwt的名称

    //生成JWT
    public String generateToken(String s){

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);

        return Jwts.builder()
                .setHeaderParam("typ","JWT")//设置头部参数
                .setSubject(s)//设置jwt的主体，即谁是这个jwt的所有者
                .setIssuedAt(nowDate)//生成时间
                .setExpiration(expireDate)//过期时间
                .signWith(SignatureAlgorithm.HS512,secret)//设置标签和算法
                .compact();//合成
    }

    //解析JWT
    public Claims getCliamByToken(String jwt){
        try {
            return Jwts.parser()
                    .setSigningKey(secret)//设置密钥
                    .parseClaimsJws(jwt)//解析jwt
                    .getBody();//返回Body部分
        }   catch (Exception e){
            return null;  //jwt解析产生异常，返回空
        }
    }

    //判断JWT是否过期
    public boolean isTokenExpired(Claims claims){
        return claims.getExpiration().before(new Date());//在过期时间之前，说明没有过期，否则过期
    }
}
