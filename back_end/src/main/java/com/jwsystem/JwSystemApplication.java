package com.jwsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.jwsystem.dao")//添加Mapper注解
public class JwSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(JwSystemApplication.class,args);
    }
}
