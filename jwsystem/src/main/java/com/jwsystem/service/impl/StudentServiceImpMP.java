package com.jwsystem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.dao.StudentDaoMP;
import com.jwsystem.entity.user.StudentPO;
import com.jwsystem.service.StudentServiceMP;
import com.jwsystem.util.TransUtil;
import com.jwsystem.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.jwsystem.controller.UserController.STUDENT_NUM_LENGTH;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Service
public class StudentServiceImpMP extends ServiceImpl<StudentDaoMP, StudentPO> implements StudentServiceMP {

    @Autowired
    StudentDaoMP studentDaoMP;
    @Autowired
    TransUtil transUtil;

    @Override
    public Boolean insertUser(UserVO userVO) {
        StudentPO studentPO = transUtil.UserVOtoStudentPO(userVO);
        return studentDaoMP.insert(studentPO) != 0;
    }

    @Override
    public UserVO selectUserByNumber(String number) {
        UserVO userVO = transUtil.StudentPOtoUserVO(studentDaoMP.selectById(number));
        //UserVO userVO = transUtil.StudentPOtoUserVO(studentDaoMP.selectOne(Wrappers.lambdaQuery(StudentPO.class)
         //       .eq(StudentPO::getNumber,number)));
        return userVO;
    }

    @Override
    public StudentPO selectStuById(String id) {
        return studentDaoMP.selectOne(Wrappers.lambdaQuery(StudentPO.class)
                .eq(StudentPO::getId,id));
    }

    @Override
    public List<UserVO> selectAllUserInfos() {
        List<StudentPO> studentPOList = studentDaoMP.selectList(Wrappers.emptyWrapper());
        List<UserVO> userVOList = new ArrayList<>();
        for (StudentPO s:studentPOList) {
            userVOList.add(transUtil.StudentPOtoUserVO(s));
        }
        return userVOList;
    }

    @Override
    public void updatePwdByNumber(String password, String number) {
        StudentPO studentPO = studentDaoMP.selectById(number);
        studentPO.setPassword(password);
        studentDaoMP.updateById(studentPO);
    }

    @Override
    public int updateStuInfoByUser(UserVO userVO) {
        StudentPO studentPO = transUtil.UserVOtoStudentPO(userVO);
        return studentDaoMP.updateById(studentPO);
    }

    @Override
    public int updateStuInfoByAdmin(UserVO userVO) {
        StudentPO studentPO = transUtil.UserVOtoStudentPO(userVO);
        return studentDaoMP.updateById(studentPO);
    }

    @Override
    public int selectStuByCollege(int collegeId) {
        return Math.toIntExact(studentDaoMP.selectCount(Wrappers.lambdaQuery(StudentPO.class)
                .eq(StudentPO::getCollegeId, collegeId)));
    }

    @Override
    public int selectStuByMajor(int majorId) {
        return Math.toIntExact(studentDaoMP.selectCount(Wrappers.lambdaQuery(StudentPO.class)
                .eq(StudentPO::getMajorId, majorId)));
    }

    public Boolean legalNumber(String number){
        return number.length() == STUDENT_NUM_LENGTH && StringUtils.isNumeric(number);
    }
}
