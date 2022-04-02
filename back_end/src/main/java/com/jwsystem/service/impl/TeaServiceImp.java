package com.jwsystem.service.impl;

import com.jwsystem.dao.TeacherDao;
import com.jwsystem.entity.Teacher;
import com.jwsystem.entity.User;
import com.jwsystem.service.TeaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jwsystem.controller.UserController.TEACHER_NUM_LENGTH;

@Service
public class TeaServiceImp implements TeaService {
    @Autowired
    TeacherDao teacherDao;

    @Override
    public Boolean insertUser(User user) { return teacherDao.insertUser(user) !=0;  }

    @Override
    public User getUserByNumber(String number) {
        return teacherDao.getUserByNumber(number);
    }

    @Override
    public Teacher selectTeaById(String id) {
        return teacherDao.selectById(id);
    }

    @Override
    public List<User> getAllUserInfos() {
        return teacherDao.getAllTea();
    }

    @Override
    public Boolean updatePwdByNumber(String password,String number) {
        return teacherDao.updatePwdByNumber(password,number) != 0;
    }

    @Override
    public Boolean updateTeaInfo(User user) {
        return teacherDao.updateTeaInfo(user) != 0;
    }

    @Override
    public Boolean deleteTeaByNumber(String number) {
        return teacherDao.deleteTeaByNumber(number) != 0;
    }

    public Boolean legalNumber(String number){
        return number.length() == TEACHER_NUM_LENGTH && StringUtils.isNumeric(number);
    }
}
