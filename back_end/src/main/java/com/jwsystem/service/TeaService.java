package com.jwsystem.service;

import com.jwsystem.entity.Teacher;
import com.jwsystem.entity.User;

import java.util.List;

public interface TeaService {
    //插入老师
    void insertUser(User user);
    //查找是否存在该老师，返回User类型
    User getUserByNumber(String number);
    //根据身份证号查找
    Teacher selectTeaById(String id);
    //查找所有老师信息，返回list user
    List<User> getAllUserInfos();
    //根据number修改用户密码
    void updatePwdByNumber(String password, String number);
    //用户修改个人信息
    Boolean updateTeaInfoByUser(User user);
    //管理员修改个人信息
    void updateTeaInfoByAdmin(User user);
    //删除教师
    Boolean deleteTeaByNumber(String number);
}
