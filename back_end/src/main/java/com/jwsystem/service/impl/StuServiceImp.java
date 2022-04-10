package com.jwsystem.service.impl;

import com.jwsystem.dao.MajorDao;
import com.jwsystem.dao.StudentDao;
import com.jwsystem.entity.Student;
import com.jwsystem.dto.User;
import com.jwsystem.service.StuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jwsystem.controller.UserController.STUDENT_NUM_LENGTH;

@Service
public class StuServiceImp implements StuService {
    @Autowired
    StudentDao studentDao;

    @Autowired
    MajorDao majorDao;

    @Override
    public Boolean insertUser(User user) {
        return studentDao.insertUser(user) != 0;
    }

    @Override
    public User getUserByNumber(String number) {
        return studentDao.getUserByNumber(number);
    }

    @Override
    public Student selectStuById(String id) {
        return studentDao.selectById(id);
    }

    @Override
    public Student selectStuByNum(String number) {
        return studentDao.selectByNum(number);
    }

    @Override
    public List<User> getAllUserInfos() {
        return studentDao.getAllStu();
    }

    @Override
    public void updatePwdByNumber(String password, String number) {
        studentDao.updatePwdByNumber(password, number);
    }
    @Override
    public Boolean updateStuInfoByUser(User user){
        return studentDao.updateStuInfoByUser(user) != 0;
    }

    @Override
    public void updateStuInfoByAdmin(User user) {
        studentDao.updateStuInfoByAdmin(user);
    }

    @Override
    public Boolean deleteStuByNumber(String number) {
        return studentDao.deleteStuByNumber(number) != 0;
    }

    public Boolean legalNumber(String number){
        return number.length() == STUDENT_NUM_LENGTH && StringUtils.isNumeric(number);
    }

    @Override
    public boolean findUserMajor(String majorName) {
        return majorDao.findMajorByName(majorName) != null;
    }
}
