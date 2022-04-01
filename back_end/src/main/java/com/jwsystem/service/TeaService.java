package com.jwsystem.service;

import com.jwsystem.entity.Teacher;
import com.jwsystem.entity.User;

import java.util.List;

public interface TeaService {
    //插入老师
    Boolean insertUser(User user);
    //查找是否存在该老师，返回User类型
    User getUserByNumber(String number);
    //根据身份证号查找
    Teacher selectTeaById(String id);
    //查找所有老师信息，返回list user
    List<User> getAllUserInfos();
    //根据number修改用户密码
    Boolean updatePwdByNumber(String password,String number);
    //修改个人信息
    Boolean updateTeaInfo(User user);
    //删除教师
    Boolean deleteTeaByNumber(String number);
}
