package com.jwsystem.dao;

import com.jwsystem.entity.user.Student;
<<<<<<< Updated upstream
import com.jwsystem.dto.User;
=======
import com.jwsystem.vo.UserVO;
>>>>>>> Stashed changes
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentDao {
    //新增学生
    int insertUser(UserVO userVO);     //这边是传入Student还是传入User? 前端给的是什么形式？
    //学生修改手机号、邮箱、登陆密码
    int updateStuInfoByUser(UserVO userVO);
    //管理员修改除学工号以外的信息
    int updateStuInfoByAdmin(UserVO userVO);
    //修改学生密码
    int updatePwdByNumber(String password,String number);
    //查看所有学生信息
    List<UserVO> getAllStu();
    //根据身份证号查找
    Student selectById(String id);
    //根据学号查找
    Student selectByNum(String number);
    //查看指定学生信息
    UserVO getUserByNumber(String number);
    //学生查看个人信息
    Student getStuInfo(String number);
    //删除学生
    int deleteStuByNumber(String number);
    //根据学院找到对应的学生个数
    int getStuByCollegeName(String collegeName);
    //根据专业找到对应的学生个数
    int getStuByMajor(String major);
}
