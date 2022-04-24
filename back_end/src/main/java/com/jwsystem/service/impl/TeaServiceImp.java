package com.jwsystem.service.impl;

import com.jwsystem.dao.CollegeDao;
import com.jwsystem.dao.MajorDao;
import com.jwsystem.dao.TeacherDao;
import com.jwsystem.entity.user.Teacher;
import com.jwsystem.vo.TeacherDataVO;
import com.jwsystem.dto.User;
import com.jwsystem.service.TeaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.jwsystem.controller.UserController.TEACHER_NUM_LENGTH;

@Service
public class TeaServiceImp implements TeaService {
    @Autowired
    TeacherDao teacherDao;

    @Autowired
    MajorDao majorDao;

    @Autowired
    CollegeDao collegeDao;

    @Override
    public Boolean insertUser(User user) {
        return teacherDao.insertUser(user) != 0;
    }

    @Override
    public User getUserByNumber(String number) {
        return teacherDao.getUserByNumber(number);
    }

    @Override
    public Teacher selectTeaById(String id) {
        return teacherDao.selectById(id);
    }

    @Override
    public Teacher selectTeaByNum(String number) {
        return teacherDao.selectByNum(number);
    }

    @Override
    public List<User> getAllUserInfos() {
        return teacherDao.getAllTea();
    }

    @Override
    public void updatePwdByNumber(String password, String number) {
        teacherDao.updatePwdByNumber(password, number);
    }

    @Override
    public Boolean updateTeaInfoByUser(User user) {
        return teacherDao.updateTeaInfoByUser(user) != 0;
    }

    @Override
    public void updateTeaInfoByAdmin(User user){
        teacherDao.updateTeaInfoByAdmin(user);
    }

    @Override
    public Boolean deleteTeaByNumber(String number) {
        return teacherDao.deleteTeaByNumber(number) != 0;
    }

    public Boolean legalNumber(String number){
        return number.length() == TEACHER_NUM_LENGTH && StringUtils.isNumeric(number);
    }

    @Override
    public boolean findUserMajor(String majorName) {
        return majorDao.findMajorByName(majorName) != null;
    }

    @Override
    public List<TeacherDataVO> getAllTeachersWithCollege(){
        //将所有的有老师的college拿出来遍历，根据college名字去找到所有的teacher
//        public class TeacherData {
//            private String collegeName;
//            private List<Teacher> teachers;
//        }
        List<TeacherDataVO> teacherDataVOList = new ArrayList<>();
        List<String> colleges;
        colleges = collegeDao.getAllCollegeName();
        for(String c:colleges){
            TeacherDataVO td = new TeacherDataVO(c,teacherDao.getTeacherByCollegeName(c));
            teacherDataVOList.add(td);
        }
        return teacherDataVOList;
    }


}