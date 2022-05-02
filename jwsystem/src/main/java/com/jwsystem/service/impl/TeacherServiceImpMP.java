package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dao.CollegeDaoMP;
import com.jwsystem.entity.college.CollegePO;
import com.jwsystem.entity.user.StudentPO;
import com.jwsystem.entity.user.TeacherPO;
import com.jwsystem.dao.TeacherDaoMP;
import com.jwsystem.service.TeacherServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.util.TransUtil;
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
    @Autowired
    TransUtil transUtil;
    @Override
    public Boolean insertUser(UserVO userVO) {
        TeacherPO teacherPO = transUtil.UserVOtoTeacherPO(userVO);
        return teacherDaoMP.insert(teacherPO) != 0;
    }

    @Override
    public UserVO selectUserByNumber(String number) {
        TeacherPO teacherPO = teacherDaoMP.selectById(number);
        UserVO userVO = transUtil.TeacherPOtoUserVO(teacherPO);
        return userVO;
    }

    @Override
    public TeacherPO selectTeaById(String id) {
        return teacherDaoMP.selectOne(Wrappers.lambdaQuery(TeacherPO.class)
                .eq(TeacherPO::getId,id));
    }

    @Override
    public List<UserVO> selectAllUserInfos() {
        List<TeacherPO> teacherPOList = teacherDaoMP.selectList(Wrappers.emptyWrapper());
        List<UserVO> userVOList = new ArrayList<>();
        for (TeacherPO t:teacherPOList) {
            userVOList.add(transUtil.TeacherPOtoUserVO(t));
        }
        return userVOList;
    }

    @Override
    public void updatePwdByNumber(String password, String number) {
         TeacherPO teacherPO = teacherDaoMP.selectById(number);
         teacherPO.setPassword(password);
         teacherDaoMP.updateById(teacherPO);
    }

    @Override
    public int updateTeaInfoByUser(UserVO userVO) {
        TeacherPO teacherPO = transUtil.UserVOtoTeacherPO(userVO);
        return teacherDaoMP.updateById(teacherPO);
//        return teacherDaoMP.update(null,Wrappers.lambdaUpdate(TeacherPO.class)
//                .set(TeacherPO::getPassword,userVO.getPassword())//密码
//                .set(TeacherPO::getPhone,userVO.getPhone())//手机号
//                .set(TeacherPO::getEmail,userVO.getEmail())//邮箱
//                .eq(TeacherPO::getId,userVO.getNumber()));
    }

    @Override
    public int updateTeaInfoByAdmin(UserVO userVO) {
        TeacherPO teacherPO = transUtil.UserVOtoTeacherPO(userVO);
        return teacherDaoMP.updateById(teacherPO);
    }

    @Override
    public List<TeacherDataVO> getAllTeachersWithCollege() {
        List<TeacherDataVO> teacherDataVOList = new ArrayList<>();
        List<CollegePO> colleges = collegeDaoMP.selectList(null);
        for(CollegePO c:colleges){
            TeacherDataVO td = new TeacherDataVO();
            List<TeacherPO> teacherPOList = teacherDaoMP.selectList(Wrappers.lambdaQuery(TeacherPO.class).eq(TeacherPO::getCollegeId,c.getCollegeId()));
            List<UserVO> userVOList = new ArrayList<>();
            for(TeacherPO t :teacherPOList) {
                userVOList.add(transUtil.TeacherPOtoUserVO(t));
            }
            td.setCollegeName(c.getName());
            td.setTeachers(userVOList);
            teacherDataVOList.add(td);
        }
        return teacherDataVOList;
    }

    public Boolean legalNumber(String number){
        return number.length() == TEACHER_NUM_LENGTH && StringUtils.isNumeric(number);
    }
}
