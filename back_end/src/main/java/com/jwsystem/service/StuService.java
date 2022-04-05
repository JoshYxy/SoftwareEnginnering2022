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
    //根据学号查找
    Student selectStuByNum(String number);
    //查找所有学生信息，返回list user
    List<User> getAllUserInfos();
    //根据number修改用户密码
    void updatePwdByNumber(String password, String number);
    //用户修改个人信息
    Boolean updateStuInfoByUser(User user);
    //管理员修改个人信息
    void updateStuInfoByAdmin(User user);
    //根据number删除学生
    Boolean deleteStuByNumber(String number);

    boolean findUserMajor(String majorName);
}
