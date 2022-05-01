//package com.jwsystem.dao;
//
//import com.jwsystem.entity.user.Teacher;
//<<<<<<< Updated upstream
//import com.jwsystem.dto.User;
//=======
//import com.jwsystem.vo.UserVO;
//>>>>>>> Stashed changes
//import org.apache.ibatis.annotations.Mapper;
//
//import java.util.List;
//
//@Mapper
//public interface TeacherDao {
//    //新增老师
//    int insertUser(UserVO userVO);
//    //老师修改手机号、邮箱、登陆密码
//    int updateTeaInfoByUser(UserVO userVO);
//    //管理员修改除学工号以外的信息
//    int updateTeaInfoByAdmin(UserVO userVO);
//    //根据number修改老师密码
//    int updatePwdByNumber(String password,String number);
//    //查看所有老师信息
//    List<UserVO> getAllTea();
//    //根据身份证号查找
//    Teacher selectById(String id);
//    //根据工号查找
//    Teacher selectByNum(String number);
//    //查看指定老师信息
//    UserVO getUserByNumber(String number);
//    //老师查看个人信息
//    Teacher getTeaInfo(String number);
//    //删除老师
//    int deleteTeaByNumber(String number);
//    //根据学院名得到老师信息
//    List<Teacher> getTeacherByCollegeName(String college);
//    //根据专业名得到老师数量
//    int getTeacherByMajor(String major);
//}
