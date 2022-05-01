package com.jwsystem.vo;

import com.jwsystem.dao.TeacherDaoMP;
import com.jwsystem.entity.user.AdminPO;
import com.jwsystem.entity.user.StudentPO;
import com.jwsystem.entity.user.TeacherPO;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    public static String GRADUATED = "graduated";
    public static String QUIT = "quit";
    public static String STUDYING = "studying";
    public static String WORKING = "working";


    @CsvBindByName(column = "role")
    protected String role;//角色 教师 学生 管理员
    @CsvBindByName(column = "number")
    protected String number;
    @CsvBindByName(column = "id")
    protected String id;
    @CsvBindByName(column = "name")
    protected String name;
    @CsvBindByName(column = "password")
    protected String password;
    @CsvBindByName(column = "phone")
    protected String phone;
    @CsvBindByName(column = "email")
    protected String email;
    @CsvBindByName(column = "status")
    protected String status;
    @CsvBindByName(column = "major")
    protected String major;
    @CsvBindByName(column = "college")
    protected String college;

    public UserVO(AdminPO a) {
        this.role = a.getRole();
        this.number = a.getNumber();
        this.password = a.getPassword();
    }

    public UserVO(StudentPO s) {
        this.role = s.getRole();
        this.number = s.getNumber();
        this.id = s.getId();
        this.name = s.getName();
        this.password = s.getPassword();
        this.phone = s.getPhone();
        this.email = s.getEmail();
        this.status = s.getStatus();
    }

    public UserVO(TeacherPO teacherPO) {

    }


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
