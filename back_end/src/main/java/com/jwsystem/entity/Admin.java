package com.jwsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User{
    private String role; //= "admin"    角色 教师 学生 管理员
    private String number;
    private String password;
    private boolean curriculaVariable; // = false  //是否为选课时段
}
