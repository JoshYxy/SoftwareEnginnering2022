package com.jwsystem.service;

import com.jwsystem.entity.user.StudentPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwsystem.vo.UserVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
public interface StudentServiceMP extends IService<StudentPO> {
    //插入学生信息
    Boolean insertUser(UserVO userVO);
    //根据学工号查找 返回UserVO
    UserVO selectUserByNumber(String number);
    //根据身份证查找
    StudentPO selectStuById(String id);
    //根据学号查找
    //StudentPO selectById(String number);
    //得到所有学生用户信息
    List<UserVO> selectAllUserInfos();
    //根据主键修改密码
    void updatePwdByNumber(String password,String number);
    //用户更新信息
    int updateStuInfoByUser(UserVO userVO);
    //管理员更新信息
    int updateStuInfoByAdmin(UserVO userVO);
    //根据college查找学生
    int selectStuByCollege(int collegeId);
    //根据major查找学生
    int selectStuByMajor(int majorId);
}
