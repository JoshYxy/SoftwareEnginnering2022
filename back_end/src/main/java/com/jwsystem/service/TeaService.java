package com.jwsystem.service;

import com.jwsystem.entity.Teacher;
import com.jwsystem.dto.User;
import com.jwsystem.vo.TeacherData;

import java.util.List;

public interface TeaService {
    //插入老师
    Boolean insertUser(User user);
    //查找是否存在该老师，返回User类型
    User getUserByNumber(String number);
    //根据身份证号查找
    Teacher selectTeaById(String id);
    //根据工号查找
    Teacher selectTeaByNum(String number);
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

    boolean findUserMajor(String majorName);
    //返回教师信息 按照学院分类
    List<TeacherData> getAllTeachersWithCollege();
}
