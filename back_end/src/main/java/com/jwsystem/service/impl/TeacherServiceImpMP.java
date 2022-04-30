package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dao.CollegeDaoMP;
import com.jwsystem.entity.college.CollegePO;
import com.jwsystem.entity.user.StudentPO;
import com.jwsystem.entity.user.TeacherPO;
import com.jwsystem.dao.TeacherDaoMP;
import com.jwsystem.service.TeacherServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.vo.TeacherDataVO;
import com.jwsystem.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.jwsystem.controller.UserController.TEACHER_NUM_LENGTH;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Service
public class TeacherServiceImpMP extends ServiceImpl<TeacherDaoMP, TeacherPO> implements TeacherServiceMP {
    @Autowired
    TeacherDaoMP teacherDaoMP;
    @Autowired
    CollegeDaoMP collegeDaoMP;
    @Override
    public Boolean insertUser(UserVO userVO) {
        teacherDaoMP.insert(teacherPO);
        return null;
    }

    @Override
    public UserVO selectUserByNumber(String number) {
        teacherDaoMP.selectById(number);
        return null;
    }

    @Override
    public TeacherPO selectTeaById(String id) {
        return teacherDaoMP.selectOne(Wrappers.lambdaQuery(TeacherPO.class)
                .eq(TeacherPO::getId,id));
    }

    @Override
    public List<UserVO> selectAllUserInfos() {
        return teacherDaoMP.selectAllUserInfos();
    }

    @Override
    public int updatePwdByNumber(String password, String number) {
         return teacherDaoMP.update(null,Wrappers.lambdaUpdate(TeacherPO.class)
                .set(TeacherPO::getPassword,password)
                .eq(TeacherPO::getId,number));
    }

    @Override
    public int updateTeaInfoByUser(UserVO userVO) {
        return teacherDaoMP.update(null,Wrappers.lambdaUpdate(TeacherPO.class)
                .set(TeacherPO::getPassword,userVO.getPassword())//密码
                .set(TeacherPO::getPhone,userVO.getPhone())//手机号
                .set(TeacherPO::getEmail,userVO.getEmail())//邮箱
                .eq(TeacherPO::getId,userVO.getNumber()));
    }

    @Override
    public int updateTeaInfoByAdmin(UserVO userVO) {
        TeacherPO user
        return teacherDaoMP.update(user);
    }

    @Override
    public List<TeacherDataVO> getAllTeachersWithCollege() {
        List<TeacherDataVO> teacherDataVOList = new ArrayList<>();
        List<CollegePO> colleges = collegeDaoMP.selectList(null);
        for(CollegePO c:colleges){
            TeacherDataVO td = new TeacherDataVO(c.getName(),teacherDaoMP.selectList(Wrappers.lambdaQuery(TeacherPO.class).eq(TeacherPO::getCollegeId,c.getCollegeId()))));
            teacherDataVOList.add(td);
        }
        return teacherDataVOList;
    }

    public Boolean legalNumber(String number){
        return number.length() == TEACHER_NUM_LENGTH && StringUtils.isNumeric(number);
    }
}
