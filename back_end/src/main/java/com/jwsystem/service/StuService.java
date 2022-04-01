package com.jwsystem.service;

import com.jwsystem.entity.Student;
import com.jwsystem.entity.User;

import java.util.List;

public interface StuService {
    //插入学生
    Boolean insertUser(User user);
    //查找是否存在该学生，返回User类型
    User getUserByNumber(String number);
    //根据身份证号查找
    Student selectStuById(String id);
    //查找所有学生信息，返回list user
    List<User> getAllUserInfos();
    //根据number修改用户密码
    Boolean updatePwdByNumber(String password,String number);
    //修改个人信息
    Boolean updateStuInfo(User user);
    //根据number删除学生
    Boolean deleteStuByNumber(String number);
}
