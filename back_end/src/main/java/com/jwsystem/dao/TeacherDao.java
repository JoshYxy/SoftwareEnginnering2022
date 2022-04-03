package com.jwsystem.dao;

import com.jwsystem.entity.Student;
import com.jwsystem.entity.Teacher;
import com.jwsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherDao {
    //新增老师
    int insertUser(User user);
    //老师修改手机号、邮箱、登陆密码
    int updateTeaInfoByUser(User user);
    //管理员修改除学工号以外的信息
    int updateTeaInfoByAdmin(User user);
    //根据number修改老师密码
    int updatePwdByNumber(String password,String number);
    //查看所有老师信息
    List<User> getAllTea();
    //根据身份证号查找
    Teacher selectById(String id);
    //查看指定老师信息
    User getUserByNumber(String number);
    //老师查看个人信息
    Teacher getTeaInfo(String number);
    //删除老师
    int deleteTeaByNumber(String number);
}
