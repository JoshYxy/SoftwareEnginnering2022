package com.jwsystem.service;


import com.jwsystem.entity.user.StudentPO;
import com.jwsystem.entity.user.TeacherPO;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jwsystem.vo.TeacherDataVO;
import com.jwsystem.vo.UserVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
public interface TeacherServiceMP extends IService<TeacherPO> {
    //插入老师信息
    Boolean insertUser(UserVO userVO);
    //根据工号查找 返回UserVO
    UserVO selectUserByNumber(String number);
    //根据身份证查找
    TeacherPO selectTeaById(String id);
    //根据工号查找
    //TeacherPO selectById(String number);
    //得到所有老师用户信息
    List<UserVO> selectAllUserInfos();
    //根据主键修改密码
    void updatePwdByNumber(String password,String number);
    //用户更新信息
    int updateTeaInfoByUser(UserVO userVO);
    //管理员更新信息
    int updateTeaInfoByAdmin(UserVO userVO);
    //得到TeacherDataVo
    List<TeacherDataVO> getAllTeachersWithCollege();
}
