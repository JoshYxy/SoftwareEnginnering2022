package com.jwsystem.entity.user;

import com.jwsystem.dto.User;

public class Teacher extends User {
    private String role;//角色 教师 学生 管理员
    private String number;
    private String id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private String status;
    private String major;
    private String college;
    /*
            用户角色:role
            学号/工号:number
            姓名:name
            身份证号:id
            手机号（选填）:phone
            邮箱（选填）:email
            密码:password
     */
}
