package com.jwsystem.service.impl;

import com.jwsystem.dao.TeacherDao;
import com.jwsystem.entity.Teacher;
import com.jwsystem.entity.User;
import com.jwsystem.service.TeaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeaServiceImp implements TeaService {
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
}
