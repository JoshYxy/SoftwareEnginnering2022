package com.jwsystem.entity;

import com.jwsystem.dto.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User {
    private String role; //= "admin"    角色 教师 学生 管理员
    private String number; //1 默认值 能够以管理员身份登陆
    private String password;
    private Boolean curriculaVariable; // = false  //是否为选课时段
}