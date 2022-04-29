package com.jwsystem.service;

import com.jwsystem.entity.user.Teacher;
<<<<<<< Updated upstream
import com.jwsystem.dto.User;
=======
import com.jwsystem.vo.UserVO;
>>>>>>> Stashed changes
import com.jwsystem.vo.TeacherDataVO;

import java.util.List;

public interface TeaService {
    //插入老师
    Boolean insertUser(UserVO userVO);
    //查找是否存在该老师，返回User类型
    UserVO getUserByNumber(String number);
    //根据身份证号查找
    Teacher selectTeaById(String id);
    //根据工号查找
    Teacher selectTeaByNum(String number);
    //查找所有老师信息，返回list user
    List<UserVO> getAllUserInfos();
    //根据number修改用户密码
    void updatePwdByNumber(String password, String number);
    //用户修改个人信息
    Boolean updateTeaInfoByUser(UserVO userVO);
    //管理员修改个人信息
    void updateTeaInfoByAdmin(UserVO userVO);
    //删除教师
    Boolean deleteTeaByNumber(String number);

    boolean findUserMajor(String majorName);
    //返回教师信息 按照学院分类
    List<TeacherDataVO> getAllTeachersWithCollege();
}
