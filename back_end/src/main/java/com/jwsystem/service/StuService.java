package com.jwsystem.service;

import com.jwsystem.entity.user.Student;
<<<<<<< Updated upstream
import com.jwsystem.dto.User;
=======
import com.jwsystem.vo.UserVO;
>>>>>>> Stashed changes

import java.util.List;

public interface StuService {
    //插入学生
    Boolean insertUser(UserVO userVO);
    //查找是否存在该学生，返回User类型
    UserVO getUserByNumber(String number);
    //根据身份证号查找
    Student selectStuById(String id);
    //根据学号查找
    Student selectStuByNum(String number);
    //查找所有学生信息，返回list user
    List<UserVO> getAllUserInfos();
    //根据number修改用户密码
    void updatePwdByNumber(String password, String number);
    //用户修改个人信息
    Boolean updateStuInfoByUser(UserVO userVO);
    //管理员修改个人信息
    void updateStuInfoByAdmin(UserVO userVO);
    //根据number删除学生
    Boolean deleteStuByNumber(String number);

    boolean findUserMajor(String majorName);
}
